package com.ecms.utils;

import com.ecms.exceptions.CommonBusinessException;

public class NumberUtils {

    /**
     * 获取编号，去除前面的0字符
     * 0011 -> 11
     * @param numberDisplay
     * @return
     */
    public static int getNumberFromDisplay(String numberDisplay) {
        int index = 0;
        for (char ch : numberDisplay.toCharArray()) {
            if ('0' == (ch)) {
                index ++;
            } else {
                break;
            }
        }
        if (index < numberDisplay.length()) {
            String value = numberDisplay.substring(index);
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

    /**
     * 获取编号描述，4位表示
     * 11 -> 0011
     * @param num
     * @return
     */
    public static String getNumberDisplay4(int num) {
        if (num > 9999) {
            throw new CommonBusinessException("编号超过9999");
        }
        int number = 10000 + num;
        String value = String.valueOf(number);
        return value.substring(1);
    }

    public static void main(String[] args) {
        System.out.println(getNumberFromDisplay("0000"));
        System.out.println(getNumberFromDisplay("0011"));
        System.out.println(getNumberFromDisplay("00101"));
        System.out.println(getNumberFromDisplay("001010"));
        System.out.println(getNumberDisplay4(1));
        System.out.println(getNumberDisplay4(11));
        System.out.println(getNumberDisplay4(111));
        System.out.println(getNumberDisplay4(1110));
    }
}
