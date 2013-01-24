package de.jasiflak.duelp;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
				HttpAction httpRequest = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/lernplan/"+Duelp.mUser, false, null);
				httpRequest.execute();
				httpRequest.waitForAnswer();
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
					  valueList.add(le.getDate()  + "\nFach: " +le.getFach() + "\n"+ le.getStart() + " - " +le.getEnde() + " Uhr\nOrt: "+ le.getOrt());
				  }
				  
				  ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
				  final ListView lv = (ListView)findViewById(R.id.listView1);

				  lv.setAdapter(null);//inhalt vom ListAdapter löschen
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
			  valueList.add(le.getDate()  + "\nFach: " +le.getFach() + "\n"+ le.getStart() + " - " +le.getEnde() + " Uhr\nOrt: "+ le.getOrt());
	  	  }
		  

		  //generieren eines ListAdapter für die ListView
		  ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		  final ListView lv = (ListView)findViewById(R.id.listView1);
		  lv.setAdapter(null); //inhalt vom ListAdapter löschen

		  Log.i("debug", "refresh");
		  lv.setAdapter(adapter);
		  //Toast.makeText(getBaseContext(), "Liste aktualisiert!", Toast.LENGTH_LONG).show();
	  }
	     
	  
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
	  
		@Override
		protected void onResume() {
		    super.onResume();
		    Log.i("debug", "onResume called");
		    refresh();
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
