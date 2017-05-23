package com.java.utils.math;

import java.util.Random;

/**
 * Created by Tim on 2017/5/22.
 *
 */
public class RandomUtil {

    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHAR = "0123456789";

    /**
     * 生产定长随机数 包含大小写 数字
     * @param length
     * @return
     */
    public static String randomNumAndString(int length) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            buffer.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return buffer.toString();
    }

    public static String randomStr(int length) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            buffer.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return buffer.toString();
    }

    public static String randomNum(int length) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            buffer.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
        }
        return buffer.toString();
    }
}
