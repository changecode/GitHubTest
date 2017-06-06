package com.java.utils.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tim on 2017/5/23.
 * string常见操作
 */
public class StringUtil {

    /**
     * 将半角转换成全角
     * @param str
     * @return
     */
    public static String toFull(String str) {
        String source = "1234567890!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=+\\|[];:'\",<.>/?";
        String[] decode = { "１", "２", "３", "４", "５", "６", "７", "８", "９", "０",
                "！", "＠", "＃", "＄", "％", "︿", "＆", "＊", "（", "）", "ａ", "ｂ",
                "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ",
                "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ",
                "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ",
                "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ",
                "Ｙ", "Ｚ", "－", "＿", "＝", "＋", "＼", "｜", "【", "】", "；", "：",
                "'", "\"", "，", "〈", "。", "〉", "／", "？" };
        String result = "";
        for(int i = 0; i < str.length(); i++) {
            int position = source.indexOf(str.charAt(i));
            if(position != -1){
                result += decode[position];
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 进行string 判断是否为空
     * @param object
     * @param delval
     * @return
     */
    public static boolean strIsNull(Object object) {
        return object == null ? true : false;
    }

    /**
     * 反转字符串
     * @param str
     * @return
     */
    public static String reverseStr(String str) {
        if(str == null)
            return null;
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 截取字符串 支持中文
     * @param sourceStr
     * @param length
     * @return
     */
    public static String subStr(String sourceStr, int length) {
        String resultStr = "";
        if(sourceStr == null || "".equalsIgnoreCase(sourceStr) ||
                sourceStr.length() < length) {
            return resultStr;
        }
        char[] chars = resultStr.toCharArray();
        int strNum = 0;
        int strGBKnum = 0;
        boolean isHavaDot = false;
        for(int i = 0; i < resultStr.length(); i++) {
            if(chars[i] >= 0xa1) {
                strNum = strNum + 2;
                strGBKnum++;
            } else {
                strNum++;
            }
        }
        resultStr = sourceStr.substring(0, strNum - strGBKnum);
        return  resultStr;
    }

    /**
     * 过滤html
     * @param htmlStr
     * @return
     */
    public static String escapeHtml(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签

        return htmlStr.trim(); // 返回文本字符串
    }

}
