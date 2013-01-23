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
				db.deleteAllRows();		//l�scht den inhalt aller tabellen
				lernplan_strings = parseJson(httpRequest.waitForAnswer());
				for(LearnEntry temp : lernplan_strings)
				{
					db.addLearnEntry(temp);
				}
				
				List<String> valueList = new ArrayList<String>();
				List<LearnEntry> ety = db.getAllLearnEntrys();
				  for(LearnEntry le : ety)
				  {
					  valueList.add(le.getDate()  + " " +le.getFach() + "\n"+ le.getStart() + " - " +le.getEnde() + "\n"+ le.getOrt() +"\n"+ "Fr�hst�ck: " + le.getFruehstueck()+"\n");
				  }
				  
				  ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
				  final ListView lv = (ListView)findViewById(R.id.listView1);
				  lv.setAdapter(null);//inhalt vom ListAdapter l�schen
				  lv.setAdapter(adapter);
				  
				  //Toast.makeText(getBaseContext(), "Datenbank synchronisiert!", Toast.LENGTH_LONG).show();
			  	}
			 catch(Exception e)
			  {
	
				readDatabase();
				
				//Toast.makeText(getBaseContext(), "Keine Verbindung zum Server!", Toast.LENGTH_LONG).show();
			  }
		  }
		  
		 
					  
	  }
	  
	  public void refresh()
	  {
		  initialisiere();
	  }
	   
	  public void readDatabase()
	  {		  
		  Lernplan_DatabaseHandler db = new Lernplan_DatabaseHandler(this);
		  List<String> valueList = new ArrayList<String>();
		  List<LearnEntry> ety = db.getAllLearnEntrys();
		  
		  for(LearnEntry le : ety)
	      {
			  valueList.add(le.getDate()  + " " +le.getFach() + "\n"+ le.getStart() + " - " +le.getEnde() + "\n"+ le.getOrt() +"\n"+ "Fr�hst�ck: " + le.getFruehstueck()+"\n");
	  	  }
		  
		  //generieren eines �ListAdapter� f�r die �ListView�
		  ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		  final ListView lv = (ListView)findViewById(R.id.listView1);
		  lv.setAdapter(null); //inhalt vom ListAdapter l�schen
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
		  }
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
			  }
		  });
		  if(Duelp.mOfflineMode)
			  button.setEnabled(false);
	}
	
}
