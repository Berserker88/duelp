package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TermineListeAdapter extends BaseAdapter {

	
	private Context mContext;
	private List<GregorianCalendar> mDateKeys;
	private GregorianCalendar dateToDel;
	
	
	public TermineListeAdapter(Context c) {
		List<GregorianCalendar> keys = new ArrayList<GregorianCalendar>();
		keys.addAll(TermineKalendarAdapter.mDateItems.keySet());
		Collections.sort(keys);
		mDateKeys = keys;
		mContext = c;
	}
	
	public void updateKeySet() {
		mDateKeys.clear();
		List<GregorianCalendar> keys = new ArrayList<GregorianCalendar>();
		keys.addAll(TermineKalendarAdapter.mDateItems.keySet());
		Collections.sort(keys);
		mDateKeys = keys;
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
	
	
//	private void httpRequest(GregorianCalendar date) {
//		// Create a new HttpClient and Post Header
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpPost httppost = new HttpPost("http://" + Duelp.URL + "/duelp-backend/rest/termine/delete");
//		String key = date.get(Calendar.YEAR) +"-"+ (date.get(Calendar.MONTH)+1) +"-"+ date.get(Calendar.DAY_OF_MONTH);
//		try {
//		    // Add your data
//		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		    nameValuePairs.add(new BasicNameValuePair("json", key));
//		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//		    // Execute HTTP Post Request
//		    httpclient.execute(httppost);
//		} catch (Exception e) {
//		    System.out.println("Error in posting: " + e.getMessage());
//		}
//    }
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listViewItem = convertView;
		
		// initialisieren des Tag-Views, wenn er noch nicht existiert
		if (listViewItem == null) {
			LayoutInflater inflated = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listViewItem = inflated.inflate(R.layout.termine_liste_item, null);
		}
		
		GregorianCalendar date = mDateKeys.get(position);
		Log.i("debug", date.get(Calendar.DAY_OF_MONTH) + ". " + date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMANY) + " " + date.get(Calendar.YEAR));
		
		TextView tv_date = (TextView) listViewItem.findViewById(R.id.tv_list_item_date);
		tv_date.setText(date.get(Calendar.DAY_OF_MONTH) + ". " + date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMANY) + " " + date.get(Calendar.YEAR));
		
				
		ImageView icon1 = (ImageView) listViewItem.findViewById(R.id.iv_list_item_busy);
		ImageView icon2 = (ImageView) listViewItem.findViewById(R.id.iv_list_item_home);
		
		switch(TermineKalendarAdapter.mDateItems.get(date)) {
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
		
		
		ImageView deleteItem = (ImageView) listViewItem.findViewById(R.id.iv_list_item_delete);
		deleteItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("info", "Trash has been clicked !!!");
				TextView tv_date = (TextView)((View) v.getParent()).findViewById(R.id.tv_list_item_date);
				String strDate = tv_date.getText().toString();
				String strToCompare;
				for(Map.Entry<GregorianCalendar, Integer> entry : TermineKalendarAdapter.mDateItems.entrySet()) {
					strToCompare = entry.getKey().get(Calendar.DAY_OF_MONTH) + ". " + entry.getKey().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMANY) + " " + entry.getKey().get(Calendar.YEAR);
					if (strDate.equals(strToCompare)) {
						dateToDel = entry.getKey();
						String key = dateToDel.get(Calendar.YEAR) +"-"+ (dateToDel.get(Calendar.MONTH)+1) +"-"+ dateToDel.get(Calendar.DAY_OF_MONTH);
						
						HttpAction httpAction = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/termine/delete", true, key);
						httpAction.execute();
						
						if(httpAction.waitForAnswer().equals("timeout"))
							Toast.makeText(mContext, "DUELP-Server nicht erreichbar", Toast.LENGTH_SHORT).show();
						else
							TermineKalendarAdapter.mDateItems.remove(entry.getKey());
						break;
					}
				}
				updateKeySet();
				notifyDataSetChanged();
			}
		});
		
		return listViewItem;
	}

}
