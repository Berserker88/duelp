package de.jasiflak.duelp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class TermineListe extends Activity {
	
	
	private Intent mIntent;
	private TermineListeAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.termine_liste_layout);
		
//		mIntent = getIntent();
		
//		Gson gson = new Gson();
//		Log.i("info", "intent getStringExtra: " + mIntent.getStringExtra("listItems"));
//		HashMap<String, Double> gsonMap = new HashMap<String, Double>();
//		gsonMap = gson.fromJson(mIntent.getStringExtra("listItems"), gsonMap.getClass());
//		Log.i("info", "gson hashmap: " + gsonMap);
//		HashMap<GregorianCalendar, Integer> map = new HashMap<GregorianCalendar, Integer>();
//		
//		Log.i("info", "" + gsonMap);
//		
//		for(Map.Entry<String, Double> entry : gsonMap.entrySet()) {
//			GregorianCalendar c = new GregorianCalendar();
//			c.setTimeInMillis(Long.valueOf(entry.getKey()));
//			map.put(c, entry.getValue().intValue());
//		}
		
		
		
		mAdapter = new TermineListeAdapter(this);
		
		ListView lv_dateItems = (ListView) findViewById(R.id.lv_calendar_entries);
		lv_dateItems.setAdapter(mAdapter);
		
		TextView tv_back = (TextView) findViewById(R.id.tv_list_back);
		tv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
