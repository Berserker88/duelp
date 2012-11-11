package de.jasiflak.duelp;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

public class Termine extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.termine_layout);
		
		// get the CalendarView
		CalendarView calendar = (CalendarView) findViewById(R.id.calendarTermine);
		Date minDate = new Date(112, 0, 1);
		Date maxDate = new Date(113, 11, 31);
		// limit the range of the calendar
		calendar.setMinDate(minDate.getTime());
		calendar.setMaxDate(maxDate.getTime());
	}
}
