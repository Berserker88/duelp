package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		ort_header.setOnItemClickListener(new OnItemClickListener() {		

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Intent intent = new Intent().setClass(arg0.getContext(), Orte_Alle.class);
				 startActivity(intent);
				
				
			}
		});
		
		
		}
	@Override
	public void onResume(){
		super.onResume();
		
		adapter.synchronizeBackend();
		Log.i("debug", "refresh table");
		
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
