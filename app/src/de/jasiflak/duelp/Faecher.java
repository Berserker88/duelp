package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class Faecher extends ListActivity 
{

	
	public void onCreate(Bundle savedInstanceState)
	{
		
		Log.i("Debug","Create...");

		super.onCreate(savedInstanceState);
		
		//NEW FACH DUMMY
		Fach newFach = new Fach("+","01.01.2013",-1,false);
		
		
		Fach ezs = new Fach("EZS","09.02.2013",3,false);
		Fach its = new Fach("ITS","13.02.2013",4,false);
		Fach rga = new Fach("RGA","01.02.2013",1,false);
		Fach lopro = new Fach("LoPro","04.02.2013",1,false);
	
		List<Fach> faecher = new ArrayList<Fach>();
		
		faecher.add(ezs);
		faecher.add(its);
		faecher.add(rga);
		faecher.add(lopro);

		try {
			
			setListAdapter(new Faecher_Apdapter(this,faecher));
		} catch (Exception e) {
			Log.i("Debug",e.getLocalizedMessage());
		}
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Log.i("Debug","item select....");
		Intent intent = new Intent();
		intent.setClassName(getPackageName(), getPackageName()+".FaecherDetail");
		//intent.putExtra("selected",MOBILE_OS[position]);
		startActivity(intent);
	}
	

  
	    
}
