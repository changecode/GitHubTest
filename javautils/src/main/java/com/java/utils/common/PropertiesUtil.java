package com.java.utils.common;

import java.io.*;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 读取properties属性文件工具类
 * @author Tim
 *
 */
public class PropertiesUtil {

	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);

	/***
	 * 根据配置名获取配置信息
	 *
	 * @return
	 */
	public static String getPropertiesByName(String name) {
		String value = null; // 配置文件的值
		Properties pro = new Properties();
		try {
			InputStream in = new FileInputStream("ddcash.properties");
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			pro.load(bf);
			in.close();
			value = pro.getProperty(name);
		} catch (Exception e) {
			logger.error("获取配置文件失败：" + e.getMessage());
		}
		logger.debug("配置信息 ：" + name + ":" + value);
		return value;
	}

	/***
	 * 根据配置名获取配置信息
	 *
	 * @return
	 */
	public static String getPropertiesByName(String name, String fileName) {
		String value = null; // 配置文件的值
		Properties pro = new Properties();
		try {
			InputStream in = new FileInputStream(fileName);
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			pro.load(bf);
			in.close();
			value = pro.getProperty(name);
		} catch (Exception e) {
			logger.error("获取配置文件失败：" + e.getMessage() + "，文件名：" + fileName);
		}
		logger.debug("配置信息 ：" + name + ":" + value);
		return value;
	}

	/**
	 * 保存内容到配置文件
	 */
	public static void saveProperty(String propKey, String propVal, String fileName) {
		if (StringUtils.isAnyBlank(propKey, propVal, fileName)) {
			throw new RuntimeException("参数不能为空");
		}
		BufferedReader bf = null;
		BufferedWriter bw = null;
		try {
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			Properties pro = new Properties();
			pro.load(bf);
			pro.setProperty(propKey, propVal);
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			pro.store(bw, "#最后一次处理到的客户ID");
		} catch (Exception e) {
			logger.error("保存Key【" + propKey + "】，Value【" + propVal + "】到配置文件【" + fileName + "】发生异常", e);
		} finally {
			try {
				if (null != bf) {
					bf.close();
				}
				if (null != bw) {
					bw.close();
				}
			} catch (IOException e) {
				logger.error("关闭IO流发生异常", e);
			}
		}
	}
}
