package com.java.utils.math;

import com.java.utils.isnull.IsNullUtil;

import java.math.BigDecimal;

/**
 * Created by Tim on 2017/5/22.
 * 加减乘除
 */
public class BigDecimaUtil {
    //精度
    private static int DEFAULT_SCALE =2;
    // 向上舍入
    private static int DEFAULT_ROUND = BigDecimal.ROUND_HALF_UP;


    public static String add(String s, String s1) {
        BigDecimal b1 = new BigDecimal(s);
        BigDecimal b2 = new BigDecimal(s1);
        return b1.add(b2).toString();
    }


    public static  String div(String s1, String s2,
                              int scale, int round) {
        if(scale < 0) {
            throw new IllegalArgumentException("the scale must be > 0");
        }

        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.divide(b2,scale, BigDecimal.ROUND_UP).toString();
    }


    public static int compare(String s1, String s2) {
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.compareTo(b2);
    }

    public static Long BigDecimalToLong(BigDecimal val) {
        return new Long(val.longValue());
    }
}
