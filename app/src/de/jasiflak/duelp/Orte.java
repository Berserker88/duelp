package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Orte extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orte_layout);	
		
		//Test-Array bauen ############################################################
		Map<String,String[]> map = new HashMap<String,String[]>();
		String[] jannis = {"Am Königshof","1","47805", "Krefeld"};
		String[] simon = {"Verdistrasse","30","47623", "Kevelaer"};
		String[] aki = {"Steegerstrasse","75","41334", "Viersen"};
		String[] flo = {"Buscher Weg","31a","41751", "Viersen"};
		System.out.println(Arrays.toString(jannis));		
		List<String> valueList = new ArrayList<String>();
		map.put("Jannis Raiber" ,jannis);
		map.put("Simon Schiller", simon);
		map.put("Theodoros Georgiu", aki);
		map.put("Florian Reinsberg", flo);
		for (Map.Entry<String,String[]> entry : map.entrySet())
		{
			String[] temp_arr = entry.getValue();
			valueList.add(temp_arr[0]+", "+temp_arr[1]+", "+temp_arr[2]+", "+temp_arr[3]);
		}	   
		// Werte des Arrays dem ListApdapter übergeben
		ListAdapter adapter_liste = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		ListView ort_liste = (ListView) findViewById(R.id.ort_liste);
		ort_liste.setAdapter(adapter_liste);
		
		// Header-List view String Resource übergeben
		List<String> header = new ArrayList<String>();
		header.add("Alle Orte anzeigen");
		ListAdapter adapter_header = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, header);
		ListView ort_header = (ListView) findViewById(R.id.ort_header);
		ort_header.setAdapter(adapter_header);
		
		//Aktion bei Klick auf ein Listenelement	
		//ort_liste.setClickable(true);
		ort_liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
			   public void onItemClick(AdapterView parentView, View childView, int position, long id) {  
			  
			        }
			        public void onNothingSelected(AdapterView parentView) {  

			        }

			      }); 
		
		
		
		
		
						
	}
	
}
