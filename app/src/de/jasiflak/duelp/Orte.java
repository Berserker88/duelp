package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Orte extends Activity{
	
	private Orte_Adapter adapter;
	private Handler handler;
	
	public Runnable listviewupdate = new Runnable(){

		@Override
		public void run() {
			adapter.notifyDataSetChanged();			
		}
		
	};
	
	public void refreshList(){
		handler.post(listviewupdate);
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orte_layout);	
		
		handler = new Handler();
		adapter = new Orte_Adapter(this);
		ListView ort_liste = (ListView) findViewById(R.id.ort_liste);
		ort_liste.setAdapter(adapter);
		
		// Header-List view String Resource übergeben
		List<String> header = new ArrayList<String>();
		header.add("Alle Orte anzeigen");
		ListAdapter adapter_header = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, header);
		ListView ort_header = (ListView) findViewById(R.id.ort_header);
		ort_header.setAdapter(adapter_header);
		
		}
	
}
