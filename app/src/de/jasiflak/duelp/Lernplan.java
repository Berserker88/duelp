package de.jasiflak.duelp;


import java.util.ArrayList;
import java.util.List;
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
		
		//befüllen der Liste
		for (int i = 1; i <= 100; i++)
		{
			valueList.add(i+".Termin");
		}
		
		//generieren eines „ListAdapter“ für die „ListView“
		ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		
		//ListView aus layout holen und ihr den Adapter übergeben
	    final ListView lv = (ListView)findViewById(R.id.listView1);
	    lv.setAdapter(adapter);
	}
	

}
