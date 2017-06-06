package com.java.utils.validate;

/**
 * Created by Tim on 2017/5/23.
 * 正则表达式
 */
public class RegexConstant {

    final static String emailRegex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    final static String chineseRegex = "[\u0391-\uFFE5]+$";
    final static String doubleRegex = "^[-\\+]?\\d+\\.\\d+$";
    final static String integerRegex = "^[-\\+]?[\\d]+$";
    final static String idCardRegex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
    final static String numRegex = "^[0-9]*$";
}
