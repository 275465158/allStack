package com.ecms.utils;

public class StringUtils {
    /**
     * 自动补零
     * @param sourceDate 参数
     * @param formatLength 长度
     * @return
     */
    public static String frontCompWithZore(int sourceDate, int formatLength) {
        /**
         * 0 指前面补充零
         * formatLength 字符总长度为 formatLength
         * d 代表为正数。
         */
        String newString = String.format("%0" + formatLength + "d", sourceDate);
        return newString;
    }
}
