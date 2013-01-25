package de.jasiflak.duelp;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolygonOptionsCreator;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.maps.MapActivity;

public class Orte_Alle extends MapActivity {
	private GoogleMap mMap;
	private LatLng mNortheast;
	private LatLng mSouthwest;
	private LatLng mActPosition;
	private List <Polyline> mAirlines = new ArrayList<Polyline>();
	private Double mDistance = 0.0;
	private Marker mIndicator;
	private List <LatLng> mAdresses = new ArrayList<LatLng>();
	private Polygon mAccuracy;
	private Integer map_type = 0;

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orte_layout_alle_orte);

		// determine your location
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		LocationProvider provider = locationManager
				.getProvider(LocationManager.GPS_PROVIDER);
		final boolean gpsEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		if (!gpsEnabled) {
			// Build an alert dialog here that requests that the user enable
			// the location services, then when the user clicks the "OK" button,
			// call enableLocationSettings()
			Log.i("debug", "GPS OFF!?");
			Toast.makeText(getBaseContext(),
					"Fuer hohe Genauigkeit GPS einschalten", Toast.LENGTH_LONG)
					.show();
		}

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				mActPosition = new LatLng(location.getLatitude(),
						location.getLongitude());
				Boolean infowindow = false;
				if (mIndicator != null) {
					infowindow = mIndicator.isInfoWindowShown();
					mIndicator.remove();
					mAccuracy.remove();
					for(int i=0;i<mAirlines.size();i++){
						mAirlines.get(i).remove();
									
					}
					
				}
				
				mIndicator = mMap
						.addMarker(new MarkerOptions()
								.position(mActPosition)
								.title("Ihre Position")
								.snippet("Genauigkeit: "+location.getAccuracy()+"m")
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
				if(infowindow){
				mIndicator.showInfoWindow();
				}

				Log.i("debug", "Accuracy: " + (location.getAccuracy()));
				
				mAccuracy = mMap
						.addPolygon(new PolygonOptions()
								.add(new LatLng(mActPosition.latitude
										+ (location.getAccuracy() / 14111),
										mActPosition.longitude),
										new LatLng(
												mActPosition.latitude,
												mActPosition.longitude
														- (location
																.getAccuracy() / 11111)),
										new LatLng(
												mActPosition.latitude
														- (location
																.getAccuracy() / 14111),
												mActPosition.longitude),
										new LatLng(
												mActPosition.latitude,
												mActPosition.longitude
														+ (location
																.getAccuracy() / 11111)))
								.strokeColor(0xff1919B3).strokeWidth(3).fillColor(0x601919B3));
				Log.i("debug",
						"Farbe des Genauigkeitskreises: "
								+ mAccuracy.getFillColor());
				for(int i =0;i<mAdresses.size();i++){
					Polyline line = mMap.addPolyline(new PolylineOptions()
				    .add((mActPosition), mAdresses.get(i))
				    .width(5)
				    .color(Color.BLUE));
					mAirlines.add(line);
				}
				
				

				// mMap.moveCamera(CameraUpdateFactory.newLatLng(mActPosition));
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				20, locationListener);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapview_alle_orte)).getMap();
		List<Double> lats = new ArrayList<Double>();
		List<Double> longs = new ArrayList<Double>();
		for (String key : Orte_Adapter.map.keySet()) {			
     			String[] values = Orte_Adapter.map.get(key);

					mAdresses.add(new LatLng(Double.parseDouble(values[4]),Double.parseDouble(values[5])));
					mMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(
											Double.parseDouble(values[4]),Double.parseDouble(values[5])))
							.title(key)
							.snippet(values[0] + " " + values[1]+"\n"+values[2] + " " + values[3])
									.visible(true));
					lats.add(Double.parseDouble(values[4]));
					longs.add(Double.parseDouble(values[5]));
					Log.i("Debug:", "Marker hinzugefuegt");

		}
		Log.i("debug", "Sortieren");
		Collections.sort(lats);
		Collections.sort(longs);
		Log.i("debug", "Sortiert!" + lats.toString());
		mNortheast = new LatLng(lats.get(lats.size() - 1), longs.get(longs
				.size() - 1));
		mSouthwest = new LatLng(lats.get(0), longs.get(0));
		Log.i("debug", mNortheast.latitude + " " + mSouthwest.longitude);
		mMap.setOnCameraChangeListener(new OnCameraChangeListener() {

			@Override
			public void onCameraChange(CameraPosition arg0) {

				LatLngBounds bounds = new LatLngBounds(mSouthwest, mNortheast);
				// Move camera.
				mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 70));
				// Remove listener to prevent position reset on camera move.
				mMap.setOnCameraChangeListener(null);
			}
		});
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				if(mActPosition!=null){
				Double dx = 71.5 * (arg0.getPosition().longitude - mActPosition.longitude);
				Double dy = 111.3 * (arg0.getPosition().latitude - mActPosition.latitude);
			mDistance = Math.sqrt(dx * dx + dy * dy);
			DecimalFormat df = new DecimalFormat("##0.0");
			if(!arg0.getTitle().equalsIgnoreCase("Ihre Position")){
			if(mDistance > 1.0){				
				arg0.setSnippet("Entfernung: "+df.format(mDistance)+"km");
			}else{
				mDistance = mDistance*1000;
				arg0.setSnippet("Entfernung: "+df.format(mDistance)+"m");
				}
				}
				}
				return false;
				
					}});
		ImageView typetoggle = (ImageView) findViewById(R.id.map_ic_type_toggle_all);
		map_type = mMap.getMapType();
		typetoggle.setOnClickListener(new OnClickListener() {
		
			
			@Override
			public void onClick(View v) {	
				Log.i("debug","map_toggle_clicked");
				
				switch(map_type){
					case 1:
						map_type = map_type +3;
						mMap.setMapType(mMap.MAP_TYPE_HYBRID);
					
					break;
					
					case 4:
						map_type = map_type -3;
					mMap.setMapType(mMap.MAP_TYPE_NORMAL);
						
						break;
					default:
						break;
				}
				
		}
	});
		
	}
}
	


