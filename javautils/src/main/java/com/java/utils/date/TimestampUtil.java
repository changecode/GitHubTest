package com.java.utils.date;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 时间戳工具类
 * @author Tim
 *
 */
public class TimestampUtil {

	/**
	 * 字符串转换成时间戳
	 * @param value
	 * @return
	 */
	public static Timestamp stringToTimestamp(String paramsStr){
        if(paramsStr != null && !"".equals(paramsStr.trim())){
            return null;
        }
    	Timestamp ts = new Timestamp(System.currentTimeMillis());
    	ts = Timestamp.valueOf(paramsStr);
    	return ts;
    }
	
	
	/**
	 * 日期转换成时间戳
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date){
        if(date == null){
            return null;
        }
        return new Timestamp(date.getTime());
    }
	
	/**
	 * 时间戳转换成日期
	 * @param time
	 * @return
	 */
	public static Date timestampToDate(Timestamp time){
        return time == null ? null : time;
    }
}
