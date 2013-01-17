package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.List;

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
	
	public void refreshData()
	{
		
		HttpAction httpAction = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/faecher", false,null);
		httpAction.execute();
		try {
			String response = httpAction.waitForAnswer();		
			Log.i("Debug","Response: " +  response);
			
		} catch (SecurityException ex) {
			Toast.makeText(null, "DUELP-Server nicht erreichbar", Toast.LENGTH_SHORT).show();
		}
			
	}


	public void onCreate(Bundle savedInstanceState)
	{
		
		
		Log.i("Debug","Create...");

		super.onCreate(savedInstanceState);
		refreshData();
		
		//NEW FACH DUMMY
		Fach newFach = new Fach("+","01.01.2013",-1,false);
		
		
		Fach ezs = new Fach("EZS","09.02.2013",3,false);
		Fach its = new Fach("ITS","13.02.2013",4,false);
		Fach rga = new Fach("RGA","01.02.2013",1,false);
		Fach lopro = new Fach("LoPro","04.02.2013",1,false);
	
		this.mFaecher = new ArrayList<Fach>();
		
		mFaecher.add(newFach);
		mFaecher.add(ezs);
		mFaecher.add(its);
		mFaecher.add(rga);
		mFaecher.add(lopro);

		try {
			
			setListAdapter(new Faecher_Apdapter(this,mFaecher));
		} catch (Exception e) {
			Log.i("Debug",e.getLocalizedMessage());
		}

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
