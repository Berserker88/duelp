package de.jasiflak.duelp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;

import com.google.gson.Gson;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


public class FaecherDetail extends Activity
{

	static final int DATE_DIALOG_ID = 777;

	private int year;
	private int month;
	private int day;
	private Intent mIntent;
	private Bundle mBundle;
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faecher_detail_layout);
		
		//Fachname
		 EditText fachEditText = (EditText) findViewById(R.id.txtFach);
		
			//DateEditText...
		 EditText dateEditText = (EditText) findViewById(R.id.txtDatum);
		 
		mIntent = getIntent();
		this.mBundle = mIntent.getExtras();
		
		
		if(!mBundle.getCharSequence("name").equals("+"))
		{
			fachEditText.setText(mBundle.getCharSequence("name"));
			try {
				setBundleDateOnView();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else
		{
			setCurrentDateOnView(); 
			
		}
		
			
		 dateEditText.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
         	 showDialog(DATE_DIALOG_ID);
         }
     	});
	

		 //Save Button
		 final Button button = (Button) findViewById(R.id.btnSave);
		 button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) 
             {
                 // Perform action on click
					Log.i("Debug","Save button clicked!");	
					
					//CREATE JSON REPRESENTATION OF CURRENT FACH
					
					 EditText fachEditText = (EditText) findViewById(R.id.txtFach);

					
					ArrayList<String> arrList = new ArrayList<String>();
					arrList.add((String) mBundle.get("id"));
					arrList.add((String) fachEditText.getText().toString());
					arrList.add((String) mBundle.get("date"));
					arrList.add((String) mBundle.get("rating").toString());
					arrList.add("false");

					//TO JSON
					Gson gson = new Gson();
					String postString = gson.toJson(arrList);
					
					Log.i("Debug","POSTJSON:" + postString);
					
					
					//HTTP POST
					// Create a new HttpClient and Post Header
				
					try
					{
						
						Log.i("Debug", "http-request");
						HttpAction httpAction = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/faecher/edit", true, postString);
						httpAction.execute();
						if(httpAction.waitForAnswer().equals("timeout"))
						Log.i("Debug","Timeout");
						//mTimeout = true;
						//Pop back view
						//finish();
						
					}
					
					catch (Exception e) 
					{
					}
					
 
             }
         });
		 
		 //Delete Button
		 final Button button2 = (Button) findViewById(R.id.btnDel);
		 button2.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
					Log.i("Debug","Delete button clicked!");	

             }
         });
	}
	
	
/*----------Date Picker Methods--------------------------*/	
	public void setCurrentDateOnView() {


		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		 EditText dateEditText = (EditText) findViewById(R.id.txtDatum); 

		dateEditText.setText(new StringBuilder()
				// Month is 0 based, just add 1
		.append(day).append(".").append(month + 1).append(".").append(year)
				.append(" "));

	}
	
	@SuppressWarnings("deprecation")
	public void setBundleDateOnView() throws ParseException {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
		Date date = dateFormatter.parse(mBundle.getString("date"));
		
		day = date.getDay();
		month = date.getMonth();
		year = date.getYear();
		
		// set current date into textview
		 EditText dateEditText = (EditText) findViewById(R.id.txtDatum); 

		dateEditText.setText(mBundle.getString("date"));

	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set current date into textview
			EditText dateEditText = (EditText) findViewById(R.id.txtDatum); 
			dateEditText.setText(new StringBuilder()
					// Month is 0 based, just add 1
			.append(day).append(".").append(month + 1).append(".").append(year)
					.append(" "));

			// set current date into datepicker

		}
	};

	
	
}
