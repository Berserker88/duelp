package de.jasiflak.duelp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Lernplan extends Activity
{
	  public void refresh()
	  {
		  Lernplan_DatabaseHandler db = new Lernplan_DatabaseHandler(this);
		  List<String> valueList = new ArrayList<String>();
		  
		  db.addLearnEntry(new LearnEntry("12.12.2012", "MoIT", "14:00", "21:30", "Flo", "ja"));
		  db.addLearnEntry(new LearnEntry("15.12.2012", "EZS", "11:00", "20:30", "Simon", "ja"));
		  db.addLearnEntry(new LearnEntry("20.12.2012", "ITS", "13:30", "19:30", "Jannis", "ja"));
		  db.addLearnEntry(new LearnEntry("25.12.2012", "SWE", "10:00", "17:30", "Aki", "nein"));
		  
		  List<LearnEntry> ety = db.getAllLearnEntrys();
		  for(LearnEntry le : ety)
	      {
	  	    	valueList.add(" Datum: " +le.getDate()  + " " +le.getFach() + " "+ le.getStart() + " " +le.getEnde() + " " + le.getOrt() + " frühstuck: " + le.getFruehstueck());
	  	  }
		  
		  ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		  final ListView lv = (ListView)findViewById(R.id.listView1);
		  lv.setAdapter(adapter);
		  
		  
	  }
	    
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lernplan_layout);

		
		refresh();
		
/*###in refresh() verlagert anfang
		//Instanziieren einer Liste, erstellen einer Liste
		List<String> valueList = new ArrayList<String>();
		
		
		//Testdaten
//		Map<Integer, String[]> testdaten = new HashMap<Integer, String[]>();
//		String[] eintrag1 = {"15.11.12","MoIT","13:00","16:00","Flo","nein"};
//	    String[] eintrag2 = {"16.11.12","ITS","14:00","19:00","Simon","ja"};
//	    String[] eintrag3 = {"17.11.12","VSY","12:00","17:30","Jannis","nein"};
//	    String[] eintrag4 = {"19.11.12","SWE","10:30","15:30","Aki","ja"};
//	    String[] eintrag5 = {"23.11.12","EZS","11:00","20:30","Uni","nein"};
//	    testdaten.put(1, eintrag1);
//	    testdaten.put(2, eintrag2);
//	    testdaten.put(3, eintrag3);
//	    testdaten.put(4, eintrag4);
//	    testdaten.put(5, eintrag5);
		
	    
	  //############################test
		Lernplan_DatabaseHandler db = new Lernplan_DatabaseHandler(this);
		
		//TODO: löschen der Einträge
		//db.DropTable();
	    
	  //Einträge in der Datenbank anlegen
	    db.addLearnEntry(new LearnEntry("12.12.2012", "MoIT", "14:00", "21:30", "Flo", "ja"));
	    db.addLearnEntry(new LearnEntry("15.12.2012", "EZS", "11:00", "20:30", "Simon", "ja"));
	    db.addLearnEntry(new LearnEntry("20.12.2012", "ITS", "13:30", "19:30", "Jannis", "ja"));
	    db.addLearnEntry(new LearnEntry("25.12.2012", "SWE", "10:00", "17:30", "Aki", "nein"));
	    
	    //db.DropTable();
	  
	    
	    List<LearnEntry> ety = db.getAllLearnEntrys();
	    for(LearnEntry le : ety)
	    {
	    	valueList.add(" Datum: " +le.getDate()  + " " +le.getFach() + " "+ le.getStart() + " " +le.getEnde() + " " + le.getOrt() + " frühstuck: " + le.getFruehstueck());
	    }
	    
	  
	    //############################test
	    
	//	//befüllen der Liste
	//	for (Map.Entry<Integer, String[]> entry: testdaten.entrySet())
	//	{
	//		String[] temp = entry.getValue();//temp-Array
	//		valueList.add(temp[0]+"	"+temp[1]+" "+temp[2]+" -"+temp[3]+"\nOrt: "+temp[4]+" Frühstück: "+temp[5]);
	//	}
	//
	//	
	    
	    
	    
		//generieren eines „ListAdapter“ für die „ListView“
		ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		
		//ListView aus layout holen und ihr den Adapter übergeben
	    final ListView lv = (ListView)findViewById(R.id.listView1);
	    lv.setAdapter(adapter);
	    
*/ //###in refresh() verlagert ende
	    
	    
	    
	    
	    //neue instanz erstellen
	    Lernplan_DatabaseHandler handler = new Lernplan_DatabaseHandler(this);
	    
	    //Einträge in der Datenbank anlegen
	    handler.addLearnEntry(new LearnEntry("12.12.2012", "MoIT", "14:00", "21:30", "Flo", "ja"));
	    handler.addLearnEntry(new LearnEntry("15.12.2012", "EZS", "11:00", "20:30", "Simon", "ja"));
	    handler.addLearnEntry(new LearnEntry("20.12.2012", "ITS", "13:30", "19:30", "Jannis", "ja"));
	    handler.addLearnEntry(new LearnEntry("25.12.2012", "SWE", "10:00", "17:30", "Aki", "ja"));
	    
	    
	    Log.d("debug", "Reading all contacts..");
	    List<LearnEntry> entry = handler.getAllLearnEntrys();
	    for(LearnEntry le : entry)
	    {
	    	String log = "Id: " + le.getId() +" Datum: " +le.getDate()  + " Fach: " +le.getFach() + " Start: "+ le.getStart() + " Ende: " +le.getEnde() + " Ort: " + le.getOrt();
	    	Log.d("debug", "Name: " + log);
	    }
	}
	
}
