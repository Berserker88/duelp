package de.jasiflak.duelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class FaecherDetail extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faecher_detail_layout);
		
		Intent intent = getIntent();
		((TextView)(findViewById(R.id.textView1))).setText("Es wurde "+intent.getStringExtra("selected")+ " gewählt!");
		
		
	}
}
