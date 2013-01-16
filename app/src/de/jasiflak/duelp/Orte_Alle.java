package de.jasiflak.duelp;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import de.jasiflak.duelp.Orte_Detail.MapOverlay;

public class Orte_Alle extends MapActivity {
	private GoogleMap mMap;
	private LatLng mNortheast;
	private LatLng mSouthwest;
	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Automatisch generierter Methodenstub
		return false;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orte_layout_alle_orte);
		
		//determine your location
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		 if (!gpsEnabled) {
		        // Build an alert dialog here that requests that the user enable
		        // the location services, then when the user clicks the "OK" button,
		        // call enableLocationSettings()
			 Log.i("debug", "GPS OFF!?");
		    }
		 
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapview_alle_orte)).getMap();
		
		// Geocoder for reverse-geocode the location(adress) to latitude -
				// longitude
				Geocoder geoCoder = new Geocoder(this,Locale.getDefault());
				Log.i("Debug:", "Geocoder initialisiert");
				List<Double> lats = new ArrayList<Double>();
				List<Double> longs = new ArrayList<Double>();				
				for(String key: Orte_Adapter.map.keySet()){
				try {
					String[] values = Orte_Adapter.map.get(key);
					Log.i("Debug:", "Values"+ values.toString());
					List<Address> addresses = geoCoder.getFromLocationName(values[0]+" "+values[1]+" "+values[2],1);
					if (addresses.size() > 0) {
						Log.i("Debug:", "Passende Lat,Lng gefunden");						
						mMap.addMarker(new MarkerOptions()
				        .position(new LatLng((addresses.get(0).getLatitude()),
								(addresses.get(0).getLongitude()))).title(key+"\n"+values[0]+" "+values[1]+"\n"+values[2]+" "+values[3]).visible(true));
						lats.add(addresses.get(0).getLatitude());
						longs.add(addresses.get(0).getLongitude());
						Log.i("Debug:", "Marker hinzugefügt");
						
						}
				} catch (IOException e) {
					e.printStackTrace();
					}
				}
				
				Log.i("debug","Sortieren");
				Collections.sort(lats);
				Collections.sort(longs);
				Log.i("debug","Sortiert!"+lats.toString());
				mNortheast = new LatLng(lats.get(lats.size()-1), longs.get(longs.size()-1));
				mSouthwest = new LatLng(lats.get(0), longs.get(0));
				Log.i("debug", mNortheast.latitude+" "+mSouthwest.longitude);
				mMap.setOnCameraChangeListener(new OnCameraChangeListener() {

				    @Override
				    public void onCameraChange(CameraPosition arg0) {
				    	
						LatLngBounds bounds = new LatLngBounds(mSouthwest, mNortheast);
				        // Move camera.
				    	mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,70));
				        // Remove listener to prevent position reset on camera move.
				        mMap.setOnCameraChangeListener(null);
				    }
				});
		

	}

}
