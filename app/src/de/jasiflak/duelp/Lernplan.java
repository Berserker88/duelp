package de.jasiflak.duelp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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
		String[] eintrag1 = {"15.11.12","MoIT","10:00","18:00","Flo","ja"};
		    String[] eintrag2 = {"16.11.12","ITS","10:00","18:00","Flo","ja"};
		    String[] eintrag3 = {"17.11.12","VSY","10:00","18:00","Flo","ja"};
		    String[] eintrag4 = {"19.11.12","MoIT","10:00","18:00","Flo","ja"};
		    String[] eintrag5 = {"23.11.12","MoIT","10:00","18:00","Flo","ja"};
		    testdaten.put(1, eintrag1);
		    testdaten.put(2, eintrag2);
		    testdaten.put(3, eintrag3);
		    testdaten.put(4, eintrag4);
		    testdaten.put(5, eintrag5);
		
		    
		//befüllen der Liste
		for (Map.Entry<Integer, String[]> entry: testdaten.entrySet())
		{
			String[] temp = entry.getValue();//temp-Array
			valueList.add(temp[0]+" "+temp[1]+temp[2]+temp[3]+temp[4]+temp[5]);
		}
		
		//generieren eines „ListAdapter“ für die „ListView“
		ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		
		//ListView aus layout holen und ihr den Adapter übergeben
	    final ListView lv = (ListView)findViewById(R.id.listView1);
	    lv.setAdapter(adapter);
	    
	}
	

}
