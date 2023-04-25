package com.devjp.basic.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class HomepageUtil {
	public static String parseUnixTimestamp(long timestamp) {
		long time = Long.valueOf(timestamp);
		
		Date date = new Date(time*1000L);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT+9"));
		
		return df.format(date);
	}
}
