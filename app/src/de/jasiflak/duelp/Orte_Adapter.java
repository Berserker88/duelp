package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Orte_Adapter extends BaseAdapter {

	public static Map<String, String[]> map = new HashMap<String, String[]>();
	public static ArrayList<String> keys = new ArrayList<String>();;
	private Context context;

	public Orte_Adapter(Context c) {

		context = c;
		try {
			HttpAction httpRequest = new HttpAction("http://" + Duelp.URL
					+ "/duelp-backend/rest/orte", false, null);
			httpRequest.execute();
			String answer = httpRequest.waitForAnswer();
			parseJSON(answer);
		} catch (Exception ex) {
			Toast.makeText(c, "DUELP-Server nicht erreichbar",
					Toast.LENGTH_SHORT).show();
		}
		// Test-Array bauen
		// ############################################################
		/*
		 * map = new HashMap<String, String[]>(); String[] jannis = {
		 * "Kölnerstraße", "229", "47805", "Krefeld" }; String[] simon = {
		 * "Verdistrasse", "30", "47623", "Kevelaer" }; String[] aki = {
		 * "Steegerstrasse", "75", "41334", "Viersen" }; String[] flo = {
		 * "Buscher Weg", "31a", "41751", "Viersen" };
		 * System.out.println(Arrays.toString(jannis)); map.put("Jannis Raiber",
		 * jannis); map.put("Simon Schiller", simon);
		 * map.put("Theodoros Georgiu", aki); map.put("Florian Reinsberg", flo);
		 * keys.add("Jannis Raiber"); keys.add("Simon Schiller");
		 * keys.add("Theodoros Georgiu"); keys.add("Florian Reinsberg");
		 */
	}

	public void parseJSON(String json) {
		List<String> adresses = new ArrayList<String>();
		Gson gson = new Gson();
		Log.i("debug", "Parse Json from Orte_Map");
		adresses = (List<String>) gson.fromJson(json, adresses.getClass());

		for (int i = 0; i < adresses.size(); i++) {
			String tmp[] = adresses.get(i).split(";");
			String tmpval[] = new String[6];
			
			tmpval[0] = tmp[1];
			tmpval[1] = tmp[2];
			tmpval[2] = tmp[3];
			tmpval[3] = tmp[4];
			tmpval[4] = tmp[5];
			tmpval[5] = tmp[6];
			map.put(tmp[0], tmpval);
			keys.add(tmp[0]);

		}

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
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listlayout = inflater.inflate(R.layout.orte_layout_listview_row,
					null);
		}
		TextView row1 = (TextView) listlayout.findViewById(R.id.row1);
		row1.setText(keys.get(position));
		TextView row2 = (TextView) listlayout.findViewById(R.id.row2);
		String[] value_arr = map.get(keys.get(position));
		row2.setText(value_arr[0] + " " + value_arr[1] + ", " + value_arr[2]
				+ " " + value_arr[3]);

		TextView hiddentext = (TextView) listlayout
				.findViewById(R.id.hiddentext);
		hiddentext.setText("" + position);

		listlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView position = (TextView) v.findViewById(R.id.hiddentext);
				Intent intent = new Intent().setClass(v.getContext(),
						Orte_Detail.class);
				intent.putExtra("name", keys.get(Integer.parseInt(position
						.getText().toString())));
				intent.putExtra("values", map.get(keys.get(Integer
						.parseInt(position.getText().toString()))));
				Log.i("Debug: ", "Hier nach dem Intent!");
				context.startActivity(intent);
				Log.i("Debug: ", "Hier nach dem Start der Activity!");
			}
		});

		return listlayout;
	}

}
