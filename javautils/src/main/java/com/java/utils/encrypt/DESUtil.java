package com.java.utils.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES 加密解密
 * 
 * @author Tim
 *
 */
public class DESUtil {

	protected final static String KEY = "ScAKC0XhadTHT3Al0QIDAQAB";

	/**
	 * DES加密
	 * @param data
	 * @param key
	 * @return
	 */
	static String encrypt(String data, String key) {
		String encryptedData = null;
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(deskey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
			encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("DES加密错误，错误信息：", e);
		}
		return encryptedData;
	}

	/**
	 * DES解密
	 * @param cryptData
	 * @param key
	 * @return
	 */
	static String decrypt(String cryptData, String key) {
		String decryptedData = null;
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(deskey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
			decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
		} catch (Exception e) {
			throw new RuntimeException("DES解密错误，错误信息：", e);
		}
		return decryptedData;
	}
}
