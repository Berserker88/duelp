package de.jasiflak.duelp;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.*;


public class FaecherDetail extends Activity
{
	
	private int year;
	private int month;
	private int day;
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faecher_detail_layout);
		
		Intent intent = getIntent();
		//((TextView)(findViewById(R.id.textView1))).setText("Es wurde "+intent.getStringExtra("selected")+ " gewählt!");
		
		//Set current date to label
		setCurrentDateOnView();
		
		//DateEditText...
		EditText dateEditText = (EditText) findViewById(R.id.txtDatum);
		dateEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
					Log.i("Debug","Date textField clicked");	  	 
            }
        });
		
		
		
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
	
	public void setCurrentDateOnView() {

		EditText dateEditText = (EditText) findViewById(R.id.txtDatum);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		dateEditText.setText(new StringBuilder()
				// Month is 0 based, just add 1
		.append(day).append(".").append(month + 1).append(".").append(year)
				.append(" "));

		// set current date into datepicker
		//dpResult.init(year, month, day, null);

	}

	
	
}
