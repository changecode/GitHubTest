package com.lean.base64;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Tim on 2017/12/9.
 */
public class UrlBase64 {

    private static final String ENCODINGSTRING = "UTF-8";


    /**
     * urlbase encode
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode(String data) throws UnsupportedEncodingException {
        byte[]  encodeBytes = Base64.encodeBase64URLSafe(data.getBytes(ENCODINGSTRING));
        return new String(encodeBytes, ENCODINGSTRING);
    }

    /**
     * decode urlbase64
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decode(String data) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.decodeBase64(data.getBytes(ENCODINGSTRING));
        return new String(decodeBytes,ENCODINGSTRING);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String data = "阿修罗";
        System.out.println("原文-->"+data);
        String encodedStr = UrlBase64.encode(data);
        System.out.println("加密后-->"+encodedStr);
        String decodedStr = UrlBase64.decode(encodedStr);
        System.out.println("解密后-->"+decodedStr);
        System.out.println(data.equals(decodedStr));
    }
}
