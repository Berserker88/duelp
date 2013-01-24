package de.jasiflak.duelp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import de.jasiflak.duelp.HttpAction.HttpActionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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

		try {
			
			setListAdapter(new Faecher_Apdapter(this,mFaecher));
		} catch (Exception e) {
			Log.i("Debug",e.getLocalizedMessage());
		}
	}
	
	public void onResume(Bundle savedInstanceState)
	{
		
		Log.i("Debug","Resume....!");

	}
	
	public void onRestart()
	{
		super.onRestart();
		Log.i("Debug","RESTART!!!");
		
		refreshData();
		
		//Refresh List
		this.setListAdapter(null); //inhalt vom ListAdapter löschen
		this.setListAdapter(new Faecher_Apdapter(this,mFaecher));
		
	}
	
	
	public void refreshData()
	{
		
		try {
			HttpAction httpAction = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/faecher/get/"+Duelp.mUser, false,null);
			httpAction.execute();
			String response = httpAction.waitForAnswer();
			Log.i("Debug","Response: " +  response);
			parseJSON(response);

		} catch (Exception ex) {
			try {
				Toast.makeText(null, "DUELP-Server nicht erreichbar", Toast.LENGTH_SHORT).show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
			
	}
	
	public void parseJSON(String json) {
		Log.i("Debug","Parse");
		
		
		//Replace "null" with 0 in json...
		json = json.replace("null", "\"0\"");
		
		
		ArrayList <ArrayList<String>> faecherArray = new ArrayList <ArrayList<String>>();
		Gson gson = new Gson();
		
		faecherArray = (ArrayList <ArrayList<String>>) gson.fromJson(json, faecherArray.getClass());
		
		
		this.mFaecher.clear();
		Fach newFach = new Fach(-1,"+","01.01.2013",-1,0);
		mFaecher.add(newFach);
				
		// Fach (String name, String date, int rat, boolean checked)
		for (ArrayList<String> list : faecherArray)
		{
			//Log.i("Debug", "List(0):" + list.get(0) + "List(1): " + list.get(1) + "List(2): " + list.get(2));	
			
			Fach fach = new Fach(Integer.parseInt(list.get(0)),list.get(1),list.get(2),Integer.parseInt(list.get(3)) ,Integer.parseInt(list.get(4)));
			this.mFaecher.add(fach);	
		}
		
		Log.i("Debug","mFaecher: " +mFaecher.toString());
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Log.i("Debug","item select....");
		Log.i("Debug","Rating:"+mFaecher.get(position).getmRating());

		Intent intent = new Intent();

		intent.putExtra("id",""+mFaecher.get(position).getmId());
		intent.putExtra("name",mFaecher.get(position).getmName());
		intent.putExtra("date",mFaecher.get(position).getmDate());
		intent.putExtra("rating",mFaecher.get(position).getmRating());	
		intent.putExtra("checked",mFaecher.get(position).ismCheckedIn());	

		intent.setClassName(getPackageName(), getPackageName()+".FaecherDetail");
		startActivity(intent);
	}
	
	@Override
    public void onBackPressed() {
		new AlertDialog.Builder(this)
			.setTitle("Beenden?")
			.setMessage("Wollen Sie die App beenden?")
        	.setCancelable(false)
        	.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int id) {
        			finish();
        		}
        	})
        	.setNegativeButton("Abbrechen", null)
        	.show();
    }
	

  
	    
}
