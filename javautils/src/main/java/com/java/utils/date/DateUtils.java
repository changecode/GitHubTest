package com.java.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式化工具类
 * 
 * @author Tim
 *
 */
public class DateUtils {

	static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取当前时间，格式为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime() {
		return getCurrentTime(DATE_FORMAT);
	}
	
	/**
	 * 获取当前时间
	 * @param format
	 * @return
	 */
	public static String getCurrentTime(String format) {
		SimpleDateFormat sdf = getFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 将日期格式转换成String
	 * @param value
	 * @param format
	 * @return
	 */
	public static String date2String(Date value, String format) {
		if (value == null) {
			return null;
		}

		SimpleDateFormat sdf = getFormat(format);
		return sdf.format(value);
	}

	/**
	 * 日期转换为字符串
	 * @param value
	 * @return
	 */
	public static String date2String(Date value) {
		if (value == null) {
			return null;
		}

		SimpleDateFormat sdf = getFormat(DATE_FORMAT);
		return sdf.format(value);
	}




	/**
	 * 比较两个时间相差多少小时(分钟、秒)
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	public static int compareTime(String startTime, String endTime, int type) {
		// endTime是否为空，为空默认当前时间
		if (endTime == null || "".equals(endTime)) {
			endTime = getCurrentTime();
		}

		SimpleDateFormat sdf = getFormat("");
		int value = 0;
		try {
			Date begin = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			long between = (end.getTime() - begin.getTime()) / 1000; // 除以1000转换成豪秒
			if (type == 1) { // 小时
				value = (int) (between % (24 * 36000) / 3600);
			} else if (type == 2) {
				value = (int) (between % 3600 / 60);
			} else if (type == 3) {
				value = (int) (between % 60 / 60);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 比较两个日期的大小
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static int compare(String date1, String date2, String format) {
		DateFormat df = getFormat(format);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取所在星期的第一天
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getWeekFirstDate(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		now.set(now.DATE, first_day_of_week);
		return now.getTime();
	}

	/**
	 * 获取所在星期的最后一天
	 */
	@SuppressWarnings("static-access")
	public static Date geWeektLastDate(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		int last_day_of_week = first_day_of_week + 6; // 星期日
		now.set(now.DATE, last_day_of_week);
		return now.getTime();
	}
	
	/**
	 * 获取格式化字符串
	 * @param format
	 * @return
	 */
	private static SimpleDateFormat getFormat(String format){
		if(format == null || "".equals(format)){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(format);
	}
}
