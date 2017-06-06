package com.java.utils.encrypt;

/**
 * 常用加解密工具类
 * 
 * @author Tim
 *
 */
public class EncryptAndDecryptUtil {

	
	public static String md5Encrypt(String value) {
		String result = null;
		if (value != null && !"".equals(value.trim())) {
			result = MD5Util.encrypt(value, MD5Util.MD5_KEY);
		}
		return result;
	}

	/**
	 * SHA加密
	 * 
	 * @param value
	 * @return
	 */
	public static String shaEncrypt(String value) {
		String result = null;
		if (value != null && !"".equals(value.trim())) {
			result = MD5Util.encrypt(value, MD5Util.SHA_KEY);
		}
		return result;
	}

	/**
	 * BASE64 加密
	 * 
	 * @param value
	 * @return
	 */
	public static String base64Encrypt(String value) {
		String result = null;
		if (value != null && !"".equals(value.trim())) {
			result = Base64Util.encrypt(value.getBytes());
		}
		return result;

	}

	/**
	 * BASE64 解密
	 * 
	 * @param value
	 * @return
	 */
	public static String base64Decrypt(String value) {
		String result = null;
		try {
			if (value != null && !"".equals(value.trim())) {
				byte[] bytes = Base64Util.decrypt(value);
				result = new String(bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * DES加密
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	public static String desEncrypt(String value, String key) {
		key = key == null ? DESUtil.KEY : key;
		String result = null;

		try {
			if (value != null && !"".equals(value.trim())) {
				result = DESUtil.encrypt(value, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * DES解密
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	public static String desDecrypt(String value, String key) {
		key = key == null ? DESUtil.KEY : key;
		String result = null;

		try {
			if (value != null && !"".equals(value.trim())) {
				result = DESUtil.decrypt(value, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * AES加密
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	public static String aesEncrypt(String value, String key) {
		key = key == null ? AESUtil.KEY : key;
		String result = null;
		try {
			if (value != null && !"".equals(value.trim())) {

				result = AESUtil.encrypt(value, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * AES解密
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	public static String aesDecrypt(String value, String key) {
		key = key == null ? AESUtil.KEY : key;
		String result = null;
		try {
			if (value != null && !"".equals(value.trim())) {

				result = AESUtil.decrypt(value, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
