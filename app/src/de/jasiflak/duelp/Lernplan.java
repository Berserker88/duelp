package de.jasiflak.duelp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Lernplan extends Activity
{
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lernplan_layout);

		
		//Instanziieren einer Liste, erstellen einer Liste
		List<String> valueList = new ArrayList<String>();
		
		//Testdaten
		Map<Integer, String[]> testdaten = new HashMap<Integer, String[]>();
		String[] eintrag1 = {"15.11.12","MoIT","13:00","16:00","Flo","nein"};
	    String[] eintrag2 = {"16.11.12","ITS","14:00","19:00","Simon","ja"};
	    String[] eintrag3 = {"17.11.12","VSY","12:00","17:30","Jannis","nein"};
	    String[] eintrag4 = {"19.11.12","SWE","10:30","15:30","Aki","ja"};
	    String[] eintrag5 = {"23.11.12","EZS","11:00","20:30","Uni","nein"};
	    testdaten.put(1, eintrag1);
	    testdaten.put(2, eintrag2);
	    testdaten.put(3, eintrag3);
	    testdaten.put(4, eintrag4);
	    testdaten.put(5, eintrag5);
	
		    
		//bef�llen der Liste
		for (Map.Entry<Integer, String[]> entry: testdaten.entrySet())
		{
			String[] temp = entry.getValue();//temp-Array
			valueList.add(temp[0]+"	"+temp[1]+" "+temp[2]+" -"+temp[3]+"\nOrt: "+temp[4]+" Fr�hst�ck: "+temp[5]);
		}
		
		//generieren eines �ListAdapter� f�r die �ListView�
		ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		
		//ListView aus layout holen und ihr den Adapter �bergeben
	    final ListView lv = (ListView)findViewById(R.id.listView1);
	    lv.setAdapter(adapter);
	    
	    
	}
	

}
