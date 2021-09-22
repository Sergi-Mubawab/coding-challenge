package com.n26.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date secondsAgo(int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.SECOND, (calendar.get(Calendar.SECOND) - seconds));

		return calendar.getTime();
	}

	public static Date today() {
		return new Date();
	}

}