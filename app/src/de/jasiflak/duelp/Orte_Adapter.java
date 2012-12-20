package de.jasiflak.duelp;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Orte_Adapter extends BaseAdapter {

	public static Map<String, String[]> map = new HashMap<String,String[]>();
	public static ArrayList<String> keys;
	private Context context;

	public Orte_Adapter(Context c) {
		
		context = c;
		
		// Test-Array bauen
		// ############################################################
		map = new HashMap<String, String[]>();
		String[] jannis = { "Kölnerstraße", "229", "47805", "Krefeld" };
		String[] simon = { "Verdistrasse", "30", "47623", "Kevelaer" };
		String[] aki = { "Steegerstrasse", "75", "41334", "Viersen" };
		String[] flo = { "Buscher Weg", "31a", "41751", "Viersen" };
		System.out.println(Arrays.toString(jannis));
		map.put("Jannis Raiber", jannis);
		map.put("Simon Schiller", simon);
		map.put("Theodoros Georgiu", aki);
		map.put("Florian Reinsberg", flo);
		
		keys = new ArrayList<String>();
		keys.add("Jannis Raiber");
		keys.add("Simon Schiller");
		keys.add("Theodoros Georgiu");
		keys.add("Florian Reinsberg");

		

	}

	@Override
	public int getCount() {

		return map.size();
	}

	@Override
	public Object getItem(int position) {

		return keys.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listlayout = convertView;
		if (listlayout == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listlayout = inflater.inflate(R.layout.orte_layout_listview_row, null);
		}
		TextView row1 = (TextView) listlayout.findViewById(R.id.row1);
		row1.setText(keys.get(position));
		TextView row2 = (TextView) listlayout.findViewById(R.id.row2);
		String[] value_arr = map.get(keys.get(position));
		row2.setText(value_arr[0]+" "+value_arr[1]+", "+value_arr[2]+", "+value_arr[3]);
		
		
		TextView hiddentext = (TextView) listlayout.findViewById(R.id.hiddentext);
		hiddentext.setText(""+position);
		
		listlayout.setOnClickListener(new OnClickListener() {  
			        @Override
					public void onClick(View v) {
			        	   TextView position = (TextView) v.findViewById(R.id.hiddentext);
			        	   Intent intent = new Intent().setClass(v.getContext(), Orte_Detail.class);
						   intent.putExtra("name", keys.get(Integer.parseInt(position.getText().toString())));
						   intent.putExtra("values",map.get(keys.get(Integer.parseInt(position.getText().toString()))));						   
						   Log.i("Debug: ", "Hier nach dem Intent!");
						   context.startActivity(intent);
						   Log.i("Debug: ", "Hier nach dem Start der Activity!");
						   try {
								HttpClient httpclient = new DefaultHttpClient();			
							    HttpResponse response = httpclient.execute(new HttpGet("http://"+Duelp.URL+"/duelp-backend/rest/orte/"));
							    Log.i("debug", "httpresponse");
							    StatusLine statusLine = response.getStatusLine();
							    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
							        ByteArrayOutputStream out = new ByteArrayOutputStream();
							        response.getEntity().writeTo(out);
							        out.close();
							        String responseString = out.toString();
							        Log.i("debug", "Habe folgende Antwort erhalten: " + responseString);
							        
							    } else{
							        //Closes the connection.
							        response.getEntity().getContent().close();
							    }
							} catch(Exception ex) {
								Log.i("debug", "Fehler beim Verbindungsaufbau ANFANG");
								Log.e("debug", Log.getStackTraceString(ex));//("debug", "ERROR:" + ex.getMessage());
								Log.i("debug", "Fehler beim Verbindungsaufbau ENDE");
								//ex.printStackTrace();
							}

						
					}

			      }); 

		return listlayout;
	}

}
