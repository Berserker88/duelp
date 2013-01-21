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
	private FachMode mMode;
	
	private EditText mfachEditText;
	private EditText mdatumEditText;
	
	private Button mBtnSave;
	private Button mBtnDel;

	
	public enum FachMode 
	{
	    FachModeNew,FachModeEdit
	}

	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faecher_detail_layout);
		
		
		 this.mfachEditText = (EditText) findViewById(R.id.txtFach);
		 this.mdatumEditText = (EditText) findViewById(R.id.txtDatum);
		 
		 this.mBtnDel = (Button) findViewById(R.id.btnDel);
		 this.mBtnSave = (Button) findViewById(R.id.btnSave);

		 
		mIntent = getIntent();
		this.mBundle = mIntent.getExtras();
		
		/*-------CHANGE ENTRY------------*/

		if(!mBundle.getCharSequence("name").equals("+"))
		{
			mMode = FachMode.FachModeEdit;
			
			mfachEditText.setText(mBundle.getCharSequence("name"));
			try {
				setBundleDateOnView();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		/*-------NEW ENTRY!!!!------------*/
		else
		{	
			mMode = FachMode.FachModeNew;
			
			//Hide delete button
			mBtnDel.setVisibility(View.INVISIBLE);	
			setCurrentDateOnView(); 
		}
		
			
		 mdatumEditText.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
         	 showDialog(DATE_DIALOG_ID);
         }
     	});
	

		 //Save Button
		 mBtnSave.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) 
             {
                 // Perform action on click
					Log.i("Debug","Save button clicked!");	
					
					//CREATE JSON REPRESENTATION OF CURRENT FACH
	

					ArrayList<String> arrList = new ArrayList<String>();
					arrList.add((String) mBundle.get("id"));
					arrList.add((String) mfachEditText.getText().toString());
					arrList.add((String) mdatumEditText.getText().toString());
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
						HttpAction httpAction;
						
						switch(mMode)
						{
							case FachModeNew:
								Log.i("Debug","Connecting to /add...");
								httpAction = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/faecher/add", true, postString);
							break;
							
							case FachModeEdit:
								Log.i("Debug","Connecting to /edit...");
								httpAction = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/faecher/edit", true, postString);
							break;
							
							default:
								Log.i("Debug","Connecting to /edit...(default)");
								httpAction = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/faecher/edit", true, postString);
							break;
						
						}
											
						httpAction.execute();
						if(httpAction.waitForAnswer().equals("timeout"))
						Log.i("Debug","Timeout");
						//Pop back view
						finish();						
					}
					
					catch (Exception e) 
					{
					}
					
             }
         });
		 
		 //Delete Button
		 mBtnDel.setOnClickListener(new View.OnClickListener() {
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
		
		mdatumEditText.setText(new StringBuilder()
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

		mdatumEditText.setText(mBundle.getString("date"));

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
			mdatumEditText.setText(new StringBuilder()
					// Month is 0 based, just add 1
			.append(day).append(".").append(month + 1).append(".").append(year)
					.append(" "));

			// set current date into datepicker

		}
	};

	
	
}
