package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TermineListeAdapter extends BaseAdapter {

	
	private HashMap<GregorianCalendar, Integer> mDateItems;
	private Context mContext;
	private List<GregorianCalendar> mDateKeys;
	
	public TermineListeAdapter(Context c, HashMap<GregorianCalendar, Integer> map) {
		mDateItems = map;
		List<GregorianCalendar> keys = new ArrayList<GregorianCalendar>();
		keys.addAll(mDateItems.keySet());
		mDateKeys = keys;
		Collections.sort(keys);
		mContext = c;
	}
	
	@Override
	public int getCount() {
		return mDateKeys.size();
	}

	@Override
	public Object getItem(int position) {
		return mDateKeys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listViewItem = convertView;
		
		// initialisieren des Tag-Views, wenn er noch nicht existiert
		if (listViewItem == null) {
			LayoutInflater inflated = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listViewItem = inflated.inflate(R.layout.termine_liste_item, null);
		}
		
		GregorianCalendar date = mDateKeys.get(position);
		
		TextView tv_date = (TextView) listViewItem.findViewById(R.id.tv_list_item_date);
		tv_date.setText(date.get(Calendar.DAY_OF_MONTH) + ". " + date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMANY) + " " + date.get(Calendar.YEAR));
		
				
		ImageView icon1 = (ImageView) listViewItem.findViewById(R.id.iv_list_item_busy);
		ImageView icon2 = (ImageView) listViewItem.findViewById(R.id.iv_list_item_home);
		
		switch(mDateItems.get(date)) {
			case TermineKalendarAdapter.BUSY:
				icon1.setVisibility(View.VISIBLE);
				icon2.setVisibility(View.INVISIBLE);
				break;
			case TermineKalendarAdapter.HOME:
				icon1.setVisibility(View.INVISIBLE);
				icon2.setVisibility(View.VISIBLE);
				break;
			case TermineKalendarAdapter.BUSY_HOME:
				icon1.setVisibility(View.VISIBLE);
				icon2.setVisibility(View.VISIBLE);
				break;
			default:
				icon1.setVisibility(View.INVISIBLE);
				icon2.setVisibility(View.INVISIBLE);
				break;
		}
		
		return listViewItem;
	}

}
