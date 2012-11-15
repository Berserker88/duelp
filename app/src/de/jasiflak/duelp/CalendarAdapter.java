package de.jasiflak.duelp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.opengl.Visibility;
import android.sax.TextElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {
	
	public static final int KANN_NICHT = 0x01;
	public static final int BEI_MIR = 0x10;
	public static final int KANN_NICHT_UND_BEI_MIR = 0x11;

	private Calendar mCalendar;
	private Calendar mActualDate;
	private ArrayList<String> mDaysOfMonth;
	private HashMap<Date, Integer> mDateItem;
	private Context mContext;

	public CalendarAdapter(Context c, Calendar calendar) {
		mCalendar = calendar;
		mActualDate = Calendar.getInstance();
		mContext = c;
		mDaysOfMonth = new ArrayList<String>();
		mDateItem = new HashMap<Date, Integer>();
		refreshDaysOfMonth();
	}

	public Calendar getCalendar() {
		return mCalendar;
	}
	
	private void changeItem(Date date, int state) {
		if(mDateItem.get(date) == null) {
			mDateItem.put(date, state);
			Log.i("info", "An Datum " + date.toString() + "neuer Status = " + state);
		}
		else {
			 int newState = mDateItem.get(date) ^ state;
			 mDateItem.remove(date);
			 if(newState != 0x00)
				 mDateItem.put(date, newState);
			 Log.i("info", "An Datum " + date.toString() + "neuer Status = " + newState);
		}
		Log.i("info", "HashMap = " + mDateItem.toString());
	}
	
	
	/**
	 * calculates the number of empty days in the first week of the actual month, started at monday
	 * @return number of empty days of the first week of the actual month
	 */
	private int calculateNumberOfEmptyDays() {
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		int firstDayOfMonth = mCalendar.get(Calendar.DAY_OF_WEEK);
		/**
		 * Calendar.DAY_OF_WEEK
		 * 1 = Sonntag
		 * 2 = Montag
		 * 3 = Dienstag
		 * 4 = Mittwoch
		 * 5 = Donnerstag
		 * 6 = Freitag
		 * 7 = Samstag
		 */
		if(firstDayOfMonth == 1) {
			return 6;
		}
		else {
			return (firstDayOfMonth - 2);
		}
	}
	
	
	private void setIconVisibility(ImageView iv1, ImageView iv2, Date d) {
		if(mDateItem.get(d) != null) {
			switch(mDateItem.get(d)) {
			case 0x01:
				iv1.setVisibility(View.VISIBLE);
				iv2.setVisibility(View.INVISIBLE);
				break;
			case 0x10:
				iv1.setVisibility(View.INVISIBLE);
				iv2.setVisibility(View.VISIBLE);
				break;
			case 0x11:
				iv1.setVisibility(View.VISIBLE);
				iv2.setVisibility(View.VISIBLE);
				break;
			default:
				iv1.setVisibility(View.INVISIBLE);
				iv2.setVisibility(View.INVISIBLE);
				break;
			}
		}
		else {
			iv1.setVisibility(View.INVISIBLE);
			iv2.setVisibility(View.INVISIBLE);
		}
	}
	
	
	public void refreshDaysOfMonth() {
		mDaysOfMonth.clear();
		
		int maxNumberOfDays = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int numberOfEmptyDays = calculateNumberOfEmptyDays();
		
		
		// Fülle mDaysOfMonth entsprechend des aktuell gewählten monats
		for(int i = numberOfEmptyDays; i > 0; i--)
			mDaysOfMonth.add("");
		for(int i = 1; i <= maxNumberOfDays; i++)
			mDaysOfMonth.add(""+i);	
	}
	
	
	@Override
	public int getCount() {
		return mDaysOfMonth.size();
	}

	@Override
	public Object getItem(int position) {
		return mDaysOfMonth.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private boolean compareDates(Calendar one, String day, Calendar two) {
		if(one.get(Calendar.YEAR)==two.get(Calendar.YEAR) && one.get(Calendar.MONTH)==two.get(Calendar.MONTH)
				&& day.equals("" + two.get(Calendar.DAY_OF_MONTH)))
			return true;
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View singleDayView = convertView;
		TextView dayNumberView;
		
		if (singleDayView == null) {  // if it's not recycled, initialize some attributes
			LayoutInflater inflated = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleDayView = inflated.inflate(R.layout.calendar_item, null);
        }
		
		// hole den TextView für den Tag des Monats
		dayNumberView = (TextView)singleDayView.findViewById(R.id.day_of_month);
		
		
		// deaktiviere die Leerdaten am Anfang eines Monats
		if(mDaysOfMonth.get(position).equals("")) {
			singleDayView.setEnabled(false);
			singleDayView.setBackgroundResource(R.drawable.item_background);			
		}
		else {
			singleDayView.setEnabled(true);
			
			if(compareDates(mCalendar, mDaysOfMonth.get(position), mActualDate))
				singleDayView.setBackgroundResource(R.drawable.item_background_focused);
			else
				singleDayView.setBackgroundResource(R.drawable.item_background);
			
			singleDayView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView tv = (TextView) v.findViewById(R.id.day_of_month);
					Calendar calendar = getCalendar();
					Date date = new Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH), Integer.parseInt(tv.getText().toString()));
					changeItem(date, KANN_NICHT);
					refreshDaysOfMonth();
					notifyDataSetChanged();
				}
			});
			singleDayView.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					TextView tv = (TextView) v.findViewById(R.id.day_of_month);
					Calendar calendar = getCalendar();
					Date date = new Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH), Integer.parseInt(tv.getText().toString()));
					changeItem(date, BEI_MIR);
					refreshDaysOfMonth();
					notifyDataSetChanged();
					return false;
				}
			});
		}
		
		dayNumberView.setText(mDaysOfMonth.get(position));
		
		// wenn es ein existierender Tag ist, gucke ob hier ein Icon hinmuss
		ImageView iconView1 = (ImageView)singleDayView.findViewById(R.id.date_icon);
		ImageView iconView2 = (ImageView)singleDayView.findViewById(R.id.date_icon2);
		if(mDaysOfMonth.get(position).equals("")) {
			iconView1.setVisibility(View.INVISIBLE);
			iconView2.setVisibility(View.INVISIBLE);
		}
		else {
			Date d = new Date(mCalendar.get(Calendar.YEAR) - 1900, mCalendar.get(Calendar.MONTH), Integer.parseInt(mDaysOfMonth.get(position)));
			setIconVisibility(iconView1, iconView2, d);
		}
		return singleDayView;
	}

	
}
