package de.jasiflak.duelp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;

public class KalendarAdapter extends BaseAdapter {

	public static final int NOTHING = 0x00;
	public static final int BUSY = 0x01;
	public static final int HOME = 0x10;
	public static final int BUSY_HOME = 0x11;

	private Calendar mCalendar;
	private Calendar mActualDate;
	private ArrayList<String> mDaysOfMonth;
	private HashMap<Date, Integer> mDateItem;
	private Context mContext;

	public KalendarAdapter(Context c, Calendar calendar) {
		mCalendar = calendar;
		mActualDate = Calendar.getInstance();
		mContext = c;
		mDaysOfMonth = new ArrayList<String>();
		mDateItem = new HashMap<Date, Integer>();
		refreshDaysOfMonth();
	}

	
	/**
	 * changes the itemstate in the mDateItem-HashMap
	 * @param position the position in mDaysOfMonth to be updated
	 * @param state the requested state to change
	 */
	public void changeItemState(int position, int state) {
				
		if(mDaysOfMonth.get(position).equals(""))
			return;
		
		Date date = new Date(mCalendar.get(Calendar.YEAR) - 1900, mCalendar.get(Calendar.MONTH), Integer.parseInt(mDaysOfMonth.get(position)));
		if (mDateItem.get(date) == null)
			mDateItem.put(date, state);
		else {
			int newState = mDateItem.get(date) ^ state;
			mDateItem.remove(date);
			if (newState != NOTHING)
				mDateItem.put(date, newState);
		}
	}

	/**
	 * calculates the number of empty days in the first week of the actual
	 * month, started at monday
	 * 
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
		if (firstDayOfMonth == 1) {
			return 6;
		} else {
			return (firstDayOfMonth - 2);
		}
	}

	/**
	 * changes visibility of both icons
	 * @param iv1 an ImageView that represents the first Icon
	 * @param iv2 an ImageView that represents the first Icon
	 * @param state an integer-value that represents the state
	 */
	private void setIconVisibility(ImageView iv1, ImageView iv2, int state) {
		switch (state) {
		case BUSY:
			iv1.setVisibility(View.VISIBLE);
			iv2.setVisibility(View.INVISIBLE);
			break;
		case HOME:
			iv1.setVisibility(View.INVISIBLE);
			iv2.setVisibility(View.VISIBLE);
			break;
		case BUSY_HOME:
			iv1.setVisibility(View.VISIBLE);
			iv2.setVisibility(View.VISIBLE);
			break;
		default:
			iv1.setVisibility(View.INVISIBLE);
			iv2.setVisibility(View.INVISIBLE);
			break;
		}
	}

	
	/**
	 * fill mDaysOfMonth corresponding to the actual selected
	 */
	public void refreshDaysOfMonth() {
		mDaysOfMonth.clear();

		int maxNumberOfDays = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int numberOfEmptyDays = calculateNumberOfEmptyDays();

		// Fülle mDaysOfMonth entsprechend des aktuell gewählten monats
		for (int i = numberOfEmptyDays; i > 0; i--)
			mDaysOfMonth.add("");
		for (int i = 1; i <= maxNumberOfDays; i++)
			mDaysOfMonth.add("" + i);
	}
	
	
	/**
	 * compares two Calendars
	 * @param one the first Calendar
	 * @param day the Day of the second Calendar
	 * @param two the Second Calendar
	 * @return true if the Both Dates matches, else false
	 */
	private boolean compareDates(Calendar one, String day, Calendar two) {
		if (one.get(Calendar.YEAR) == two.get(Calendar.YEAR) && one.get(Calendar.MONTH) == two.get(Calendar.MONTH) && day.equals("" + two.get(Calendar.DAY_OF_MONTH)))
			return true;
		return false;
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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View singleDayView = convertView;
		TextView dayNumberView;

		// initialisieren des Tag-Views, wenn er noch nicht existiert
		if (singleDayView == null) {
			LayoutInflater inflated = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			singleDayView = inflated.inflate(R.layout.termine_kalendar_item, null);
		}


		// die views deaktivieren, um die Swipefunktionalität beizubehalten (bessere Swipe-Performance)
		singleDayView.setEnabled(false);

		// Markiere aktuelles Datum
		if (!mDaysOfMonth.get(position).equals("") && compareDates(mCalendar, mDaysOfMonth.get(position), mActualDate))
			singleDayView.setBackgroundResource(R.drawable.termine_background_actual);
		else if(!mDaysOfMonth.get(position).equals(""))
			singleDayView.setBackgroundResource(R.drawable.termine_kalendar_dynamicbackground);
		else
			singleDayView.setBackgroundResource(R.drawable.termine_background);

		// hole den TextView fär den Tag des Monats
		dayNumberView = (TextView) singleDayView.findViewById(R.id.day_of_month);
		dayNumberView.setText(mDaysOfMonth.get(position));

		// wenn es ein existierender Tag ist, gucke ob hier ein Icon hinmuss
		ImageView iconView1 = (ImageView) singleDayView.findViewById(R.id.date_icon1);
		ImageView iconView2 = (ImageView) singleDayView.findViewById(R.id.date_icon2);
		
		if (mDaysOfMonth.get(position).equals(""))
			setIconVisibility(iconView1, iconView2, NOTHING);
		else {
			Date d = new Date(mCalendar.get(Calendar.YEAR) - 1900, mCalendar.get(Calendar.MONTH), Integer.parseInt(mDaysOfMonth.get(position)));
			if(mDateItem.get(d) != null)
				setIconVisibility(iconView1, iconView2, mDateItem.get(d));
			else
				setIconVisibility(iconView1, iconView2, NOTHING);
		}
		return singleDayView;
	}

}
