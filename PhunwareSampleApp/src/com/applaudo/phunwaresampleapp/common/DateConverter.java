package com.applaudo.phunwaresampleapp.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConverter {
	
	private static final int TWELVE = 12;
	private static final int ZERO = 0;
	/**
	 * Method that returns the custom formatted Schedule
	 */
	public static String getScheduleDateFormat(Date stratDate, Date endDate){
		
		String formatedSchedule = "";
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(stratDate);
		
		// Get and append day of the weak
		formatedSchedule +=  getDayOfTheWeek(startCalendar.get(Calendar.DAY_OF_WEEK));

		// Get and append month, +1 because month enum it's 0 based
		formatedSchedule += " " + startCalendar.get(Calendar.MONTH) + 1;
		
		// Get and append day of the month
		formatedSchedule += "/" + startCalendar.get(Calendar.DAY_OF_MONTH);
		
		// Get and append start hour in a 12:am/pm format
		formatedSchedule += " " + getMeridiemFormat(startCalendar.get(Calendar.HOUR_OF_DAY));
		
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(endDate);
		
		// Get and append end hour in a 12:am/pm format
		formatedSchedule += " to " + getMeridiemFormat(endCalendar.get(Calendar.HOUR_OF_DAY));

		return formatedSchedule;
	}
	
	/**
	 * Method returns the custom formatted hour
	 */
	private static String getMeridiemFormat(int rawHour){
		String formatedHour = "";
		
		if (rawHour == ZERO) 
			formatedHour =  "12:00 am";  
		else if (rawHour == TWELVE) 
			formatedHour = "12:00 pm";
			else if (rawHour < TWELVE) 
				formatedHour = rawHour + " am";
				else
					// convert to 12 format
					formatedHour = rawHour-TWELVE +" pm";

		return formatedHour;
	}
	
	/**
	 * Method returns the names of the days of the week
	 */
	private static String getDayOfTheWeek(int dayOfWeek){
		
		String formatedDayofTheWeak = "";
		
		switch (dayOfWeek)
		{
			case Calendar.SUNDAY:
				formatedDayofTheWeak = "Sunday";
				break;
			case Calendar.MONDAY:
				formatedDayofTheWeak = "Monday";
				break;
			case Calendar.TUESDAY:
				formatedDayofTheWeak = "Tuesday";
				break;
			case Calendar.WEDNESDAY:
				formatedDayofTheWeak = "Wednesday";
				break;
			case Calendar.THURSDAY:
				formatedDayofTheWeak = "Thursday";
				break;
			case Calendar.FRIDAY:
				formatedDayofTheWeak = "Friday";
				break;
			case Calendar.SATURDAY:
				formatedDayofTheWeak = "Saturday";
				break;
			default:
				formatedDayofTheWeak = "";
				break;
		}
		
		return formatedDayofTheWeak;
		
	}


}
