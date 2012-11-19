package de.jasiflak.duelp;

import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TermineListe extends Activity {
	
	Intent mIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.termine_liste_layout);
		Gson gsonParser = new Gson();
		
		mIntent = getIntent();
		HashMap<Date, Integer> map = gsonParser.fromJson(mIntent.getStringExtra("listItems"), HashMap.class);
		Log.i("info", "received Object: " + map.toString());
		
		TextView tv_back = (TextView) findViewById(R.id.tv_list_back);
		tv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public void onDestroy() {
		Log.i("info", "destroy!!!!");
		super.onDestroy();
	}
}
