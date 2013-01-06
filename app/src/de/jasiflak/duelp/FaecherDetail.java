package de.jasiflak.duelp;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faecher_detail_layout);

	
		//DateEditText...
		 EditText dateEditText = (EditText) findViewById(R.id.txtDatum);
		
		dateEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	 showDialog(DATE_DIALOG_ID);
            }
        });

		
		//Set current date to label
		setCurrentDateOnView();
		
		 //Save Button
		 final Button button = (Button) findViewById(R.id.btnSave);
		 button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
					Log.i("Debug","Save button clicked!");	

            	 
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
