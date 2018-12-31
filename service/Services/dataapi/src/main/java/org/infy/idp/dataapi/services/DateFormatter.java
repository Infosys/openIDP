package org.infy.idp.dataapi.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class DateFormatter {

	public DateFormatter() {
		
	}
	public String DateFormater() {

		String today_date = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		today_date = dateFormat.format(date);
		return today_date;
	}
}
