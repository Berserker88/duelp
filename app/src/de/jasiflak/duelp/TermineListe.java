package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class TermineListe extends Activity {
	
	
	private Intent mIntent;
	private TermineListeAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.termine_liste_layout);
		
		mIntent = getIntent();
		mAdapter = new TermineListeAdapter(this);
		
		ListView lv_dateItems = (ListView) findViewById(R.id.lv_calendar_entries);
		lv_dateItems.setAdapter(mAdapter);
		lv_dateItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {
				List<GregorianCalendar> keys = new ArrayList<GregorianCalendar>();
				keys.addAll(TermineKalendarAdapter.mDateItems.keySet());
				Collections.sort(keys);
				int month = keys.get(position).get(Calendar.MONTH);
				int year = keys.get(position).get(Calendar.YEAR);
				mIntent.putExtra("month", month);
				mIntent.putExtra("year", year);
				setResult(RESULT_OK, mIntent);
				finish();
			}
		});
		
		TextView tv_back = (TextView) findViewById(R.id.tv_list_back);
		tv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
