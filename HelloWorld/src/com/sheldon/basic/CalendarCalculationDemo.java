package com.sheldon.basic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("all")
public class CalendarCalculationDemo {
	
	public static final String DATETIME_FORMATE = "yyyy-MM-dd";
	
	public static void main(String[] args) throws ParseException {
		String strDateBegin = "2016-07-01";
		String strDateEnd = "2018-10-01";
		SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMATE);
		
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(format.parse(strDateBegin));
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(format.parse(strDateEnd));
		
		
		int yearDistance = calEnd.getWeekYear() - calBegin.getWeekYear();
		int monthDistance = calEnd.get(Calendar.MONTH) - calBegin.get(Calendar.MONTH);
		
		BigDecimal bdYear = new BigDecimal(yearDistance);
		BigDecimal bdMonth = new BigDecimal(monthDistance);
		
		System.out.println(bdMonth);
		bdMonth = bdMonth.multiply(new BigDecimal(5.0/6.0));
		System.out.println(bdMonth);
		
		BigDecimal bd = new BigDecimal(0);
		
	}
	
}
