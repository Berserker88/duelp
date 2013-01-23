package de.jasiflak.duelp;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Lernplan extends Activity
{
	  public void initialisiere() 
	  {
		  Log.i("debug", "initialisiere");
		  
		  //#############################
		  Lernplan_DatabaseHandler db = new Lernplan_DatabaseHandler(this); //Datenbank instanziiert
		  List<LearnEntry> lernplan_strings = new ArrayList<LearnEntry>();
		  
		  if(Duelp.mOfflineMode)
		  {
			  readDatabase();  
		  }
		  else
		  {
			  try
			  {
				HttpAction httpRequest = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/lernplan", false, null);
				httpRequest.execute();
				httpRequest.waitForAnswer();
				Log.i("debug", "httpRequest");
				db.deleteAllRows();		//löscht den inhalt aller tabellen
				lernplan_strings = parseJson(httpRequest.waitForAnswer());
				for(LearnEntry temp : lernplan_strings)
				{
					db.addLearnEntry(temp);
				}
				
				List<String> valueList = new ArrayList<String>();
				List<LearnEntry> ety = db.getAllLearnEntrys();
				  for(LearnEntry le : ety)
				  {
					  valueList.add(le.getDate()  + " " +le.getFach() + "\n"+ le.getStart() + " - " +le.getEnde() + "\n"+ le.getOrt() +"\n"+ "Frühstück: " + le.getFruehstueck()+"\n");
				  }
				  
				  ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
				  final ListView lv = (ListView)findViewById(R.id.listView1);
				  lv.setAdapter(null);//inhalt vom ListAdapter löschen
				  lv.setAdapter(adapter);
				  
				  //Toast.makeText(getBaseContext(), "Datenbank synchronisiert!", Toast.LENGTH_LONG).show();
			  }
			  catch(Exception e)
			  {
	
				Log.i("debug", "KEINE SERVERVERBINDUNG");
				readDatabase();
				
				//Toast.makeText(getBaseContext(), "Keine Verbindung zum Server!", Toast.LENGTH_LONG).show();
			  }
		  }
		  
		  //#############################
		  
		 /* 
		  // anlegen von testdaten in der DB
		  db.addLearnEntry(new LearnEntry("12.12.2012", "MoIT", "14:00", "21:30", "Flo", "ja"));
		  db.addLearnEntry(new LearnEntry("15.12.2012", "EZS", "11:00", "20:30", "Simon", "ja"));
		  db.addLearnEntry(new LearnEntry("20.12.2012", "ITS", "13:30", "19:30", "Jannis", "ja"));
		  db.addLearnEntry(new LearnEntry("25.12.2012", "SWE", "10:00", "17:30", "Aki", "nein"));
		  */
		 //HttpAction httpRequest = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/lernplan", false, null);
		  //httpRequest.execute();
			//parseJson(httpRequest.waitForAnswer());
			
			//##################################################################
			/*
			List<LearnEntry> lernplan_strings = new ArrayList<LearnEntry>();
			//List<LearnEntry> temp = new ArrayList<LearnEntry>();
			HttpAction httpRequest2 = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/lernplan", false, null);
			
			  httpRequest2.execute();
			  	lernplan_strings = parseJson(httpRequest2.waitForAnswer());
			  	
			  	Log.i("debug", "Lernplan: "+lernplan_strings.toString());
			  	
		
				for(LearnEntry temp : lernplan_strings)
				{
					db.addLearnEntry(temp);
					Log.i("debug", "temp: "+ temp);
				}
			
			
			*/
			//#################################################################
			
					  
	  }
	  
	  public void refresh()
	  {
		  initialisiere();
	  }
	   
	  public void readDatabase()
	  {
		  Log.i("debug", "refresh aufgerufen");
		  
		  Lernplan_DatabaseHandler db = new Lernplan_DatabaseHandler(this);
		  List<String> valueList = new ArrayList<String>();
		  List<LearnEntry> ety = db.getAllLearnEntrys();
		  
		  for(LearnEntry le : ety)
	      {
			  valueList.add(le.getDate()  + " " +le.getFach() + "\n"+ le.getStart() + " - " +le.getEnde() + "\n"+ le.getOrt() +"\n"+ "Frühstück: " + le.getFruehstueck()+"\n");
	  	  }
		  
		  //generieren eines ListAdapter für die ListView
		  ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		  final ListView lv = (ListView)findViewById(R.id.listView1);
		  lv.setAdapter(null); //inhalt vom ListAdapter lschen
		  Log.i("debug", "refresh");
		  lv.setAdapter(adapter);
		  //Toast.makeText(getBaseContext(), "Liste aktualisiert!", Toast.LENGTH_LONG).show();
	  }
	     
	  public void syncDB()
	  {}
	  
	  public List<LearnEntry> parseJson(String json)
	  {
		  List<String> lernplan_strings = new ArrayList<String>();
		  List<LearnEntry> lernplan = new ArrayList<LearnEntry>();
		  Gson gson = new Gson();
		  
		  lernplan_strings = (List<String>) gson.fromJson(json, lernplan_strings.getClass());
		  
		  for(String string : lernplan_strings)
		  {
			  lernplan.add(LearnEntry.fromString(string));
			  Log.i("debug", "fromString: " +LearnEntry.fromString(string));
		  }
		  Log.i("debug", "fromString Lernplan: "+ lernplan);
		  return lernplan;
		  
	  }
	
	  public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.lernplan_layout);

		  initialisiere();
		
		  final Button button = (Button) findViewById(R.id.btnRefresh);
		  button.setOnClickListener(new View.OnClickListener() 
		  {
			  public void onClick(View v) 
			  {
				  refresh();
				  Log.i("debug", "refresh button clicked!");
			  }
		  });
		  if(Duelp.mOfflineMode)
			  button.setEnabled(false);
	}
	
}
