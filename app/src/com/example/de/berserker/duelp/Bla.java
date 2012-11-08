package com.example.de.berserker.duelp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Bla extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		TextView textview = new TextView(this);
		textview.setText("Ich bin ein Bla");
		setContentView(textview);
	}
	

}
