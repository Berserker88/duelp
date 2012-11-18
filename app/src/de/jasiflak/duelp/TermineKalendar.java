package de.jasiflak.duelp;


import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class TermineKalendar extends Activity {

	public static final int NEXT = 1;
	public static final int PREV = 2;
	
	private Calendar mCalendar;
	private TermineKalendarAdapter mAdapter;
	private Handler handler;
	private OnSwipeTouchListener mSwipeListener;
	private Intent mTermineListeIntent;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		// Konstruktor-Aufruf der Basisklasse
		super.onCreate(savedInstanceState);
		
		// Verknüpfung mit dem Layout
        setContentView(R.layout.termine_kalendar_layout);
        
        // mCalendar = aktueller Kalendar
        mCalendar = Calendar.getInstance();
        // Implementation der Funktionen für meinen OnSwipeListener
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
        
        // Erstellen des Adapters für den Kalendar
        mAdapter = new TermineKalendarAdapter(this, mCalendar);
        
        // Zuweisungen für das GridView des Kalendars
        GridView gv_calendar = (GridView) findViewById(R.id.gv_kalendar);
        gv_calendar.setAdapter(mAdapter);
        gv_calendar.setOnTouchListener(mSwipeListener);
        gv_calendar.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {
				mAdapter.changeItemState(position, TermineKalendarAdapter.BUSY);
				refreshCalendar();
			}
        });
        gv_calendar.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parentView, View clickedView, int position, long id) {
				mAdapter.changeItemState(position, TermineKalendarAdapter.HOME);
				refreshCalendar();
				return true;
			}
		});
        
        // Zuweisungen für das GridView der Namen der Wochentage
        GridView gv_weekdays = (GridView) findViewById(R.id.gv_kalendar_wochentage);
        String[] days = getResources().getStringArray(R.array.strArr_weekdays);
        gv_weekdays.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days));
        gv_weekdays.setOnTouchListener(mSwipeListener);
        
        // Registrierung des Handlers
        handler = new Handler();
	    handler.post(calendarUpdater);
        
	    // Initialisierung der TextViews
        TextView title = (TextView) findViewById(R.id.tv_kalendar_title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", mCalendar));
        title.setOnTouchListener(mSwipeListener);
        
        mTermineListeIntent = new Intent().setClass(this, TermineListe.class);
        
        ImageView iv_showAsList = (ImageView) findViewById(R.id.iv_kalendar_liste);
        iv_showAsList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("info", "start!!!!");
				startActivity(mTermineListeIntent);
			}
		});
        
        
//        TextView next = (TextView) findViewById(R.id.next);
//        next.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				updateMonth(NEXT);
//				refreshCalendar();
//			}
//		});
//        
//        TextView prev = (TextView) findViewById(R.id.previous);
//        prev.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				updateMonth(PREV);
//				refreshCalendar();
//			}
//		});
        
	}
	
	/**
	 * responsible for calling the Handler runnable
	 */
	public void refreshCalendar() {
		// aktualisiere den angezeigten Monat
		TextView title = (TextView) findViewById(R.id.tv_kalendar_title);
		
		mAdapter.refreshDaysOfMonth();			
		handler.post(calendarUpdater);				
		
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", mCalendar));
	}

	
	/**
	 * updates the Month in Calendar corresponding to direction
	 * @param direction either 1 for next month or 2 for previous month
	 */
	public void updateMonth(int direction) {
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
	
	/**
	 * the Handler that notifies the adapter to reload
	 */
	public Runnable calendarUpdater = new Runnable() {
		@Override
		public void run() {
			mAdapter.notifyDataSetChanged();
		}
	};

}
