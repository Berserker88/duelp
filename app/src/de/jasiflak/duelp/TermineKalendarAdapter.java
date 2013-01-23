package de.jasiflak.duelp;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import org.json.JSONObject;
import com.google.gson.Gson;

import de.jasiflak.duelp.HttpAction.HttpActionException;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TermineKalendarAdapter extends BaseAdapter {

	public static HashMap<GregorianCalendar, Integer> mDateItems = new HashMap<GregorianCalendar, Integer>();
	
	public static final int NOTHING = 0x00;
	public static final int BUSY = 0x01;
	public static final int HOME = 0x10;
	public static final int BUSY_HOME = 0x11;

	private Calendar mCalendar;
	private Calendar mActualDate;
	private ArrayList<String> mDaysOfMonth;
	private Context mContext;
	private GregorianCalendar chosenDate;
	private boolean mTimeout;
	
	public TermineKalendarAdapter(Context c, Calendar calendar) {
		mTimeout = false;
		mCalendar = calendar;
		mActualDate = GregorianCalendar.getInstance();
		mContext = c;
		mDaysOfMonth = new ArrayList<String>();
		refreshDaysOfMonth();

	}
	
	
	public void synchronizeData() {
		HttpAction httpRequest;
		try {
			httpRequest = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/termine/" + Duelp.mUser, false, null);
			httpRequest.execute();
			String answer = httpRequest.waitForAnswer();
			parseJSON(answer);
		} catch (HttpActionException e) {
			Toast.makeText(mContext, "DUELP-Server nicht erreichbar", Toast.LENGTH_SHORT).show();
		}		
	}

	
	public void parseJSON(String json) {
		HashMap<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		Log.i("debug", "Hallo hier bin ich!!!!");
		map = (HashMap<String, String>) gson.fromJson(json, map.getClass());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mDateItems.clear();
		for(String date : map.keySet()) {
			Date parsed = null;
			try {
				parsed = df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			GregorianCalendar datum = new GregorianCalendar();
			datum.setTime(parsed);
			mDateItems.put(datum, Integer.parseInt(map.get(date)));
		}
		Log.i("debug", "all ok: " + map.toString());
	}
	
	
	public HashMap<GregorianCalendar, Integer> getDateItems() {
		return mDateItems;
	}
	
	
	private void httpRequest(GregorianCalendar date, String mode) {
		// Create a new HttpClient and Post Header
		
		Log.i("debug", "http-request");
		String key = date.get(Calendar.YEAR) +"-"+ (date.get(Calendar.MONTH)+1) +"-"+ date.get(Calendar.DAY_OF_MONTH);
		String param = "";
		
		ArrayList<String> paramList = new ArrayList<String>();
		paramList.add(Duelp.mUser);
		paramList.add(key);
		if (!mode.equals("delete")) {
			String value = "" + mDateItems.get(date);
			paramList.add(value);
			Gson gson = new Gson();
			param = gson.toJson(paramList);
		}
			
		
		HttpAction httpAction;
		try {
			httpAction = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/termine/" + mode, true, param);
			httpAction.execute();
			if(httpAction.waitForAnswer().equals("timeout"))
				mTimeout = true;
		} catch (HttpActionException e) {
			e.printStackTrace();
		}
    }
	
	
	
	/**
	 * changes the itemstate in the mDateItems-HashMap
	 * @param position the position in mDaysOfMonth to be updated
	 * @param state the requested state to change
	 */
	public void changeItemState(int position, int state) {
				
		if(mDaysOfMonth.get(position).equals(""))
			return;
		
		GregorianCalendar date = new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), Integer.parseInt(mDaysOfMonth.get(position)));
		if (mDateItems.get(date) == null) {
			chosenDate = date;
			mDateItems.put(date, state);
			httpRequest(chosenDate, "new");
			if(mTimeout) {
				mDateItems.remove(date);
				Toast.makeText(mContext, "DUELP-Server nicht erreichbar", Toast.LENGTH_SHORT).show();
				mTimeout = false;
			}
		}
		else {
			int newState = mDateItems.get(date) ^ state;
			if (newState == NOTHING) {
				chosenDate = date;
				httpRequest(chosenDate, "delete");
				if(!mTimeout)
					mDateItems.remove(date);
				else {
					Toast.makeText(mContext, "DUELP-Server nicht erreichbar", Toast.LENGTH_SHORT).show();
					mTimeout = false;
				}
			}
			else {
				
				Log.i("debug", "newState: " + newState);
				
				if(newState == BUSY_HOME && state == BUSY)
					newState = BUSY;
				else
					newState = HOME;
				
				Log.i("debug", "newState: " + newState);
				
				
				mDateItems.put(date, newState);
				chosenDate = date;
				httpRequest(chosenDate, "edit");
				if(mTimeout) {
					Toast.makeText(mContext, "DUELP-Server nicht erreichbar", Toast.LENGTH_SHORT).show();
					mDateItems.remove(date);
					mDateItems.put(date, state);
					mTimeout = false;
				}
			}
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

		// Markiere aktuelles Datumsimpledateformat
		if (!mDaysOfMonth.get(position).equals("") && compareDates(mCalendar, mDaysOfMonth.get(position), mActualDate))
			singleDayView.setBackgroundResource(R.drawable.termine_background_actual);
		else if(!mDaysOfMonth.get(position).equals(""))
			singleDayView.setBackgroundResource(R.drawable.termine_kalendar_dynamicbackground);
		else
			singleDayView.setBackgroundResource(R.drawable.termine_background);

		// hole den TextView fär den Tag des Monats
		dayNumberView = (TextView) singleDayView.findViewById(R.id.tv_tagNummer);
		dayNumberView.setText(mDaysOfMonth.get(position));

		// wenn es ein existierender Tag ist, gucke ob hier ein Icon hinmuss
		ImageView iconView1 = (ImageView) singleDayView.findViewById(R.id.iv_icon1);
		ImageView iconView2 = (ImageView) singleDayView.findViewById(R.id.iv_icon2);
		
		if (mDaysOfMonth.get(position).equals(""))
			setIconVisibility(iconView1, iconView2, NOTHING);
		else {
			GregorianCalendar d = new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), Integer.parseInt(mDaysOfMonth.get(position)));
			if(mDateItems.get(d) != null)
				setIconVisibility(iconView1, iconView2, mDateItems.get(d));
			else
				setIconVisibility(iconView1, iconView2, NOTHING);
		}
		return singleDayView;
	}

}
