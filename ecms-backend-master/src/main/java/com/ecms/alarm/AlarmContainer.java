package com.ecms.alarm;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class AlarmContainer {

    private static final AlarmContainer instance = new AlarmContainer();
    private final int bufferSize = 1024;
    private final int indexMask = bufferSize - 1;
    private final int alarmIntervalTime = 30 * 60 * 1000;
    private final long alarmScheduleDelayTime = 60 * 1000;
    private final long consumerScheduleDelayTime = 500;
    private final AlarmContent[] alarmContents = new AlarmContent[bufferSize];
    private final AtomicLong producerSequence = new AtomicLong(-1);
    private long consumerIndex = 0;
    private final ConcurrentHashMap<String, AlarmContent> alarmContentMap = new ConcurrentHashMap<String, AlarmContent>();
    private final ConcurrentHashMap<String, Long> alarmTimeMap = new ConcurrentHashMap<String, Long>();

    private AlarmContainer() {
        startConsumerThread();
        startAlarmThread();
    }

    private void startConsumerThread() {
        ScheduledExecutorService consumerExecutor = Executors.newScheduledThreadPool(1, new DaemonThreadFactory());
        consumerExecutor.scheduleWithFixedDelay(new ConsumerThread(), 0, consumerScheduleDelayTime, TimeUnit.MILLISECONDS);
    }

    private void startAlarmThread() {
        ScheduledExecutorService alarmExecutor = Executors.newScheduledThreadPool(1, new DaemonThreadFactory());
        alarmExecutor.scheduleWithFixedDelay(new AlarmThread(), 0, alarmScheduleDelayTime, TimeUnit.MILLISECONDS);
    }


    /**
     * 首次即时报警，其余则按间隔时间轮询
     * @param alarmContent
     */
    private void cacheOrSendAlarm(AlarmContent alarmContent) {
        String cacheKey = alarmContent.getCacheKey();
        Long alarmTime = alarmTimeMap.get(cacheKey);
        if (alarmTime == null) {
            sendAlarm(alarmContent);
        } else {
            if (alarmContentMap.containsKey(cacheKey)) {
                alarmContentMap.get(cacheKey).increaseCount();
            } else {
                alarmContentMap.put(cacheKey, alarmContent);
            }
        }
    }

    private void sendAlarm(AlarmContent alarmContent) {
        int count = alarmContent.getCount();
        String subject = alarmContent.getSubject() + wrapString(String.valueOf(count));
        subject = addMachineInfo(subject);
        DingdingAlarm.sendAlarm(subject);
        alarmTimeMap.put(alarmContent.getCacheKey(), System.currentTimeMillis());
    }

    public static AlarmContainer getInstance() {
        return instance;
    }

    public void saveAlarm(String receiver, String subject, String content) {
		if (needDiscard() || isInvalidReceiver(receiver) || isIgnoreSubject(subject)) {
			return;
		}
        alarmContents[getPutIndex()] = new AlarmContent(receiver, subject, content);
    }

	public void saveAlarm(String receiver, String subject, String content, Throwable e) {
		if (needDiscard() || isInvalidReceiver(receiver) || isIgnoreSubject(subject)) {
			return;
		}
		alarmContents[getPutIndex()] = new AlarmContent(receiver, subject, content, e);
	}

    /**
     * 如果短时间内的报警过多，为了控制内存大小，这里简单的做丢弃策略
     */
    private boolean needDiscard() {
		return producerSequence.get() - consumerIndex >= indexMask;
	}

    private boolean isInvalidReceiver(String receiver) {
        return false;
    }

	private boolean isIgnoreSubject(String subject) {
		return subject != null && subject.contains("Logback context not exist");
	}

    private int getPutIndex() {
        return (int) producerSequence.incrementAndGet() & indexMask;
    }

    private int getIndex(long sequence) {
        return (int) sequence & indexMask;
    }

    private String wrapString(String s) {
        return "[" + s + "]";
    }

    private String addMachineInfo(String subject) {
        return subject + wrapString(IpUtils.getLocalIp()) + wrapString(IpUtils.getHostName());
    }

    private class ConsumerThread implements Runnable {
        @Override
        public void run() {
            long producerIndex = producerSequence.get();
            if (consumerIndex <= producerIndex) {
                for (long sequence = consumerIndex; sequence <= producerIndex; sequence++) {
                    int index = getIndex(sequence);
                    AlarmContent alarmContent = alarmContents[index];
                    cacheOrSendAlarm(alarmContent);
                    alarmContents[index] = null;
                }
                consumerIndex = producerIndex + 1;
            }
        }
    }

    private class AlarmThread implements Runnable {
        @Override
        public void run() {
            Set<String> keySet = alarmTimeMap.keySet();
            for (String cacheKey : keySet) {
                long alarmTime = alarmTimeMap.get(cacheKey);
                if (System.currentTimeMillis() - alarmTime >= alarmIntervalTime) {
                    AlarmContent alarmContent = alarmContentMap.get(cacheKey);
                    if (alarmContent == null) {
                        alarmTimeMap.remove(cacheKey);
                    } else {
                        sendAlarm(alarmContent);
                        alarmContentMap.remove(cacheKey);
                    }
                }
            }
        }
    }

    static class AlarmContent {
        String receiver;
        String subject;
        String content;
        String cacheKey;
        int count = 1;

        AlarmContent(String receiver, String subject, String content) {
            this.receiver = receiver;
            this.subject = subject;
            this.content = content;
            this.cacheKey = subject;
        }

        AlarmContent(String receiver, String subject, String content, Throwable e) {
            String errorStackTrace = ExceptionUtils.getStackTrace(e);
            this.content = content + "\n" + errorStackTrace;
            this.receiver = receiver;
            this.subject = subject;
            this.cacheKey = subject;
        }

        public String getReceiver() {
            return receiver;
        }

        public String getSubject() {
            return subject;
        }

        public String getContent() {
            return content;
        }
        public String getCacheKey() {
            return cacheKey;
        }

        public int getCount() {
            return count;
        }

        public void increaseCount() {
            count ++;
        }
    }

}
