package com.ecms.alarm;

import com.alibaba.fastjson.JSONObject;
import com.ecms.utils.HttpUtils;

public class DingdingAlarm {

    private static final JSONObject alarmJson = new JSONObject();
    private static final JSONObject textJson = new JSONObject();
    private static String ALARM_URL = "https://oapi.dingtalk.com/robot/send?access_token=ddf44e11f83b3b1860f2a6c0e6f014c56543dd6fb9e2163ce87e5ee2bc9952fd";

    static {
        alarmJson.put("msgtype", "text");
        alarmJson.put("text", textJson);
    }

    public static void sendAlarm(String content) {
        textJson.put("content", content);
        HttpUtils.postJson(ALARM_URL, alarmJson.toJSONString());
    }

    public static void main(String[] args) {
        sendAlarm("测试");
    }

}
