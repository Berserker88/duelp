package de.jasiflak.duelp;


import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class Termine extends Activity {

	public static final boolean NEXT = true;
	public static final boolean PREV = false;
	
	
	private Calendar mCalendar;
	private CalendarAdapter mAdapter;
	private Handler handler;
	private OnSwipeTouchListener mSwipeListener;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Log.i("debug", "START Termine/onCreate");
		super.onCreate(savedInstanceState);
        setContentView(R.layout.termine_layout);
        
        mCalendar = Calendar.getInstance();
        mSwipeListener = new OnSwipeTouchListener() {
            public void onSwipeTop() {
            	Log.i("info", "hoch");
            }
            public void onSwipeRight() {
            	Log.i("info", "rechts");
            	updateMonth(PREV);
            	refreshCalendar();
            }
            public void onSwipeLeft() {
            	Log.i("info", "links");
            	updateMonth(NEXT);
            	refreshCalendar();
            }
            public void onSwipeBottom() {
            	Log.i("info", "runter");
            }
        };
        
        mAdapter = new CalendarAdapter(this, mCalendar);
        GridView gv_calendar = (GridView) findViewById(R.id.gv_singleDays);
        gv_calendar.setAdapter(mAdapter);
        gv_calendar.setOnTouchListener(mSwipeListener);
        
        GridView gv_weekdays = (GridView) findViewById(R.id.gv_weekdays);
        String[] days = getResources().getStringArray(R.array.strArr_weekdays);
        gv_weekdays.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days));
        gv_weekdays.setOnTouchListener(mSwipeListener);
        
        handler = new Handler();
	    handler.post(calendarUpdater);
        
        TextView title = (TextView) findViewById(R.id.calendar_title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", mCalendar));
        title.setOnTouchListener(mSwipeListener);
        
        TextView next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateMonth(NEXT);
				refreshCalendar();
				
			}
		});
        
        TextView prev = (TextView) findViewById(R.id.previous);
        prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateMonth(PREV);
				refreshCalendar();
			}
		});
       
        Log.i("debug", "ENDE Termine/onCreate");
	}
	
	
	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.calendar_title);
		
		mAdapter.refreshDaysOfMonth();			
		handler.post(calendarUpdater);				
		
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", mCalendar));
	}

	
	public void updateMonth(boolean direction) {
		if(direction == NEXT) {
			if(mCalendar.get(Calendar.MONTH) == mCalendar.getActualMaximum(Calendar.MONTH)) {
				mCalendar.set(Calendar.YEAR, mCalendar.get(Calendar.YEAR)+1);
				mCalendar.set(Calendar.MONTH, 0);
			}
			else
				mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH)+1);
		}
		else {
			if(mCalendar.get(Calendar.MONTH) == mCalendar.getActualMinimum(Calendar.MONTH)) {
				mCalendar.set(Calendar.YEAR, mCalendar.get(Calendar.YEAR)-1);
				mCalendar.set(Calendar.MONTH, 11);
			}
			else
				mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH)-1);
		}

	}
	
	
	public Runnable calendarUpdater = new Runnable() {
		
		@Override
		public void run() {
			Log.i("debug", "START Termine/runnable");
			mAdapter.notifyDataSetChanged();
			Log.i("debug", "ENDE Termine/runnable");
		}
	};

}
