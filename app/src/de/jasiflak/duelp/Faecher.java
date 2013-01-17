package de.jasiflak.duelp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;

public class Faecher extends ListActivity 
{
	private List<Fach> mFaecher;
	//private Context mContext;
	



	public void onCreate(Bundle savedInstanceState)
	{
		
		Log.i("Debug","Create...");

		super.onCreate(savedInstanceState);
		this.mFaecher = new ArrayList<Fach>();
		
	
		refreshData();
		
		//NEW FACH DUMMY
		/*Fach newFach = new Fach("+","01.01.2013",-1,false);
		
		
		Fach ezs = new Fach("EZS","09.02.2013",3,false);
		Fach its = new Fach("ITS","13.02.2013",4,false);
		Fach rga = new Fach("RGA","01.02.2013",1,false);
		Fach lopro = new Fach("LoPro","04.02.2013",1,false);
	
		this.mFaecher = new ArrayList<Fach>();
		
		mFaecher.add(newFach);
		mFaecher.add(ezs);
		mFaecher.add(its);
		mFaecher.add(rga);
		mFaecher.add(lopro);*/

		try {
			
			setListAdapter(new Faecher_Apdapter(this,mFaecher));
		} catch (Exception e) {
			Log.i("Debug",e.getLocalizedMessage());
		}

	}
	
	
	public void refreshData()
	{
		
		HttpAction httpAction = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/faecher", false,null);
		httpAction.execute();
		try {
			String response = httpAction.waitForAnswer();		
			Log.i("Debug","Response: " +  response);	
			if(!response.equals("timeout"))
			{
				parseJSON(response);
			}

		} catch (SecurityException ex) {
			//INSERT CORRECT CONTEXT HIER
			Toast.makeText(null, "DUELP-Server nicht erreichbar", Toast.LENGTH_SHORT).show();
		}
			
	}
	
	public void parseJSON(String json) {
		Log.i("Debug","Parse");
		ArrayList <ArrayList<String>> faecherArray = new ArrayList <ArrayList<String>>();
		Gson gson = new Gson();
		
		faecherArray = (ArrayList <ArrayList<String>>) gson.fromJson(json, faecherArray.getClass());
		
		
		this.mFaecher.clear();
		Fach newFach = new Fach("+","01.01.2013",-1,false);
		mFaecher.add(newFach);
				
		// Fach (String name, String date, int rat, boolean checked)
		for (ArrayList<String> list : faecherArray)
		{
			//Log.i("Debug", "List(0):" + list.get(0) + "List(1): " + list.get(1) + "List(2): " + list.get(2));	
			Fach fach = new Fach(list.get(1),list.get(2),Integer.parseInt(list.get(3)),false);
			Log.i("Debug","BAUM:" + fach.toString());
			this.mFaecher.add(fach);	
		}
		
		Log.i("Debug","mFaecher: " +mFaecher.toString());
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Log.i("Debug","item select....");
		Intent intent = new Intent();

		intent.putExtra("name",mFaecher.get(position).getmName());
		intent.putExtra("date",mFaecher.get(position).getmDate());
		intent.putExtra("rating",mFaecher.get(position).getmRating());	
	
		intent.setClassName(getPackageName(), getPackageName()+".FaecherDetail");
		startActivity(intent);
	}
	

  
	    
}
