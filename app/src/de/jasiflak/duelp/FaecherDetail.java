package de.jasiflak.duelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FaecherDetail extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faecher_detail_layout);
		
		Intent intent = getIntent();
		//((TextView)(findViewById(R.id.textView1))).setText("Es wurde "+intent.getStringExtra("selected")+ " gewählt!");
		
		
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
}
