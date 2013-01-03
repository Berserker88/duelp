package de.jasiflak.duelp;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;

public class HttpAction extends AsyncTask<String, Void, Void> {

	@Override
	protected Void doInBackground(String... params) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
		    HttpResponse response = httpclient.execute(new HttpGet("http://" + Duelp.URL + "/duelp-backend/rest/termine"));
		    StatusLine statusLine = response.getStatusLine();
		    Log.i("debug", "request sent");
		    Log.i("debug", "answer: " + statusLine.getStatusCode());
		    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        out.close();
		        String responseString = out.toString();
		        Log.i("debug", "Habe folgende Antwort erhalten: " + responseString);
		        parseJSON(responseString);
		    } else{
		        //Closes the connection.
		        response.getEntity().getContent().close();
		    }
		} catch(Exception ex) {
			Log.i("debug", "error while calling url: " + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
	
	public void parseJSON(String json) {
		JSONObject obj;
		HashMap<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		Log.i("debug", "Hallo hier bin ich!!!!");
		map = (HashMap<String, String>) gson.fromJson(json, map.getClass());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(String date : map.keySet()) {
			Date parsed = null;
			try {
				parsed = df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			GregorianCalendar datum = new GregorianCalendar();
			datum.setTime(parsed);
			TermineKalendarAdapter.mDateItems.put(datum, Integer.parseInt(map.get(date)));
		}
		Log.i("debug", "all ok: " + map.toString());
	}
}
