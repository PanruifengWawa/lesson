package com.lesson.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date parse(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		Date date = null;
		try {
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return date;
	}

}
