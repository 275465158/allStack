package com.ecms.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

public class MoneyUtils {

    /**
     * 分转元
     * @param amount
     * @return
     */
    public static String changeF2Y(Integer amount) {
        if (amount == null) {
            return null;
        }
        return new DecimalFormat("0.##").format(0.01*amount);
    }

    public static Integer changeY2F(String amount) {
        if (StringUtils.isBlank(amount)) {
            return null;
        }
        double money = Double.parseDouble(amount) * 100;
        return (int) money;
    }

}
