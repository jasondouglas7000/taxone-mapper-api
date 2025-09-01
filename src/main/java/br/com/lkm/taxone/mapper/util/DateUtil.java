package br.com.lkm.taxone.mapper.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	public static Date parseDateyyyyMMdd(String value) throws Exception {
		DateFormat SDF_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
		return SDF_yyyy_MM_dd.parse(value);
	}

	public static String formatyyyyMMdd(Date value) {
		DateFormat SDF_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		return SDF_yyyyMMdd.format(value);
	}
	
	public static String formatyyyyMMdd(LocalDateTime value) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		return dtf.format(value);
	}

	public static String formatyyyyMMddhhmmss(LocalDateTime value) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dtf.format(value);
	}

	public static LocalDateTime parseDateyyyyMMddhhmmss(String value) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(value, dtf);
	}

	
}
