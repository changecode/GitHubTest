package com.java.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 加密解密
 * 
 * @author Tim
 *
 */
public class MD5Util {

	protected final static String MD5_KEY = "MD5";
	protected final static String SHA_KEY = "SHA1";
	
	/**
	 * md5 加密
	 * @param value
	 * @param key
	 * @return
	 */
	static String encrypt(String value, String key) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(key);
			byte[] inputByteArray = value.getBytes();
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 字节数组转换成16进制
	 * @param byteArray
	 * @return
	 */
	static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}
}
