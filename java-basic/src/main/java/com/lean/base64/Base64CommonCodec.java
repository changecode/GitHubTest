package com.lean.base64;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by Tim on 2017/12/9.
 */
public class Base64CommonCodec {

    private static final String ENCODING = "UTF-8";


    /**
     * common base64
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode(String data) throws UnsupportedEncodingException {
        byte[] encodeByte = Base64.encodeBase64(data.getBytes());
        return new String(encodeByte, ENCODING);
    }

    /**
     * safe base64
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeSafe(String data) throws UnsupportedEncodingException {
        byte[] encodeByte = Base64.encodeBase64(data.getBytes(ENCODING), true);
        return new String(encodeByte, ENCODING);

    }

    /**
     * decode base64
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decode(String data) throws UnsupportedEncodingException {
        byte[] decodeByte = Base64.decodeBase64(data.getBytes(ENCODING));
        return new String(decodeByte, ENCODING);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        /********************测试一般encode*********************/
        String data = "赚钱";
        System.out.println("原文-->"+data);
        String encodedStr = Base64CommonCodec.encode(data);
        System.out.println("加密后-->"+encodedStr);
        String decodedStr = Base64CommonCodec.decode(encodedStr);
        System.out.println("解密后-->"+decodedStr);
        System.out.println(data.equals(decodedStr));
        System.out.println("================================");
        /********************测试安全encode*********************/
        String data2 = "赚钱";
        System.out.println("原文-->"+data2);
        String encodedStr2 = Base64CommonCodec.encodeSafe(data2);
        System.out.println("加密后-->"+encodedStr2);
        String decodedStr2 = Base64CommonCodec.decode(encodedStr2);
        System.out.println("解密后-->"+decodedStr2);
        System.out.println(data2.equals(decodedStr2));
    }
}
