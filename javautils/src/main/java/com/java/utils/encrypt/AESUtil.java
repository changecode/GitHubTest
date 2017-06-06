package com.java.utils.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密
 * @author Tim
 *
 */
public class AESUtil {

	protected static final String KEY = "NOPO3nzMD3dndwS0MccuMeXCHgVlGOoYyFwLdS24Im2e7YyhB0wrUsyYf0";
	
	/**
	 * AES 加密
	 * @param content
	 * @param encryptKey
	 * @return
	 * @throws Exception
	 */
	static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));  
  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
          
        return cipher.doFinal(content.getBytes("utf-8"));  
    }
	
	/**
	 * AES 加密接口
	 * @param value
	 * @param key
	 * @return
	 * @throws Exception
	 */
	static String encrypt(String value, String key) throws Exception {  
        return base64Encode(aesEncryptToBytes(value, key));  
    }
	
	/**
	 * base64 加密
	 * @param bytes
	 * @return
	 */
	static String base64Encode(byte[] bytes){  
        return Base64Util.encrypt(bytes);  
    } 
	
	/**
	 * AES 解密接口
	 * @param encryptValue
	 * @param key
	 * @return
	 * @throws Exception
	 */
	static String decrypt(String encryptValue, String key) throws Exception {  
        return aesDecryptByBytes(base64Decode(encryptValue), key);  
    }
	
	/**
	 * AES 解密
	 * @param encryptBytes
	 * @param decryptKey
	 * @return
	 * @throws Exception
	 */
	static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));  
          
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
          
        return new String(decryptBytes);  
    }  
	
	/**
	 * base64 解密
	 * @param base64Code
	 * @return
	 * @throws Exception
	 */
	static byte[] base64Decode(String base64Code) throws Exception{  
        return (base64Code == null ? null : new Base64Util().decrypt(base64Code));  
    }
}
