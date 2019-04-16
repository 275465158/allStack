package com.ecms.alarm;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;

public class AlarmRollingFileAppender<E> extends RollingFileAppender<E> {

    private String receiver;
    private AlarmContainer alarmContainer = AlarmContainer.getInstance();

    @Override
    protected void subAppend(E event) {
        super.subAppend(event);
        if (event instanceof LoggingEvent) {
            LoggingEvent loggingEvent = (LoggingEvent)event;
            try {
                if (loggingEvent.getLevel() == Level.ERROR
                        && alarmContainer != null) {
                    String subject = loggingEvent.getFormattedMessage();
                    if (subject == null && loggingEvent.getThrowableProxy() != null) {
                        subject = loggingEvent.getThrowableProxy().getClassName();
                    }
                    alarmContainer.saveAlarm(getReceiver(), subject, subject);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
