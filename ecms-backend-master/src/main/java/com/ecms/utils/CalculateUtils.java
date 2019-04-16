package com.ecms.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalculateUtils {

    private static final String BLANK = "";

    /**
     * 根据生日计算年龄
     * @param birthday  格式: yyyy-MM-dd
     * @return
     */
    public static String getAgeByBirth(String birthday) {
        if (StringUtils.isBlank(birthday)) {
            return BLANK;
        }
        try {
            int age = 0;
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar birth = Calendar.getInstance();
            birth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
            if (birth.before(now)) {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
                    age -= 1;
                }
            }
            return String.valueOf(age);
        } catch (Exception e) {
            return BLANK;
        }
    }

    /**
     * 根据入职日期计算工龄
     * @param joinDate          格式: yyyy-MM-dd
     * @param dimissionDate     格式: yyyy-MM-dd
     * @return
     */
    public static String getJobAge(String joinDate, String dimissionDate) {
        if (StringUtils.isBlank(joinDate)) {
            return BLANK;
        }
        try {
            float age = 0;
            long latest = System.currentTimeMillis();

            if (StringUtils.isNotBlank(dimissionDate)) {
                Date dd = new SimpleDateFormat("yyyy-MM-dd").parse(dimissionDate);
                latest = dd.getTime();
            }
            Date jd = new SimpleDateFormat("yyyy-MM-dd").parse(joinDate);
            long join = jd.getTime();
            if (latest > join) {
                long time = latest - join;
                BigDecimal bd = new BigDecimal((float) time / 86400000 / 365);
                age = bd.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).floatValue();
                return String.valueOf(age);
            }
        } catch (Exception e) {
        }
        return BLANK;
    }

}
