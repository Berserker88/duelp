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

		
		//erstellen einer Liste
		List<String> valueList = new ArrayList<String>();
		
		//
		for (int i = 1; i <= 10; i++)
		{
			valueList.add(i+".Termin");
		}
		
		ListAdapter adapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		
	    final ListView lv = (ListView)findViewById(R.id.listView1);

	    lv.setAdapter(adapter);
	}
	

}
