package com.bjxg.tools.codegenerator.projectitem.tools.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	/****
	 * yyyy-MM-dd HH:mm:ss
	 */
	private static String DATEFORMATE1 = "yyyy-MM-dd HH:mm:ss";

	public static String getNowDateTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMATE1);
		return sdf.format(d);
	}

	public static String getNowDateTime(String dateFormat) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(d);
	}
}
