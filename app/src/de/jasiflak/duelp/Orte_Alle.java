package de.jasiflak.duelp;

import java.io.IOException;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
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
import com.google.android.maps.MapActivity;

public class Orte_Alle extends MapActivity {
	private GoogleMap mMap;
	private LatLng mNortheast;
	private LatLng mSouthwest;
	private LatLng mActPosition;
	Marker mIndicator;
	List <LatLng> mAdresses = new ArrayList<LatLng>();
	Polygon mAccuracy;

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Automatisch generierter Methodenstub
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

				if (mIndicator != null) {
					mIndicator.remove();
					mAccuracy.remove();
				}
				mIndicator = mMap
						.addMarker(new MarkerOptions()
								.position(mActPosition)
								.title("Ihre Position")
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
				mIndicator.showInfoWindow();

				Log.i("debug", " " + (location.getAccuracy()));
				mAccuracy = mMap
						.addPolygon(new PolygonOptions()
								.add(new LatLng(mActPosition.latitude
										+ (location.getAccuracy() / 11111),
										mActPosition.longitude),
										new LatLng(
												mActPosition.latitude,
												mActPosition.longitude
														+ (location
																.getAccuracy() / 11111)),
										new LatLng(
												mActPosition.latitude
														- (location
																.getAccuracy() / 11111),
												mActPosition.longitude),
										new LatLng(
												mActPosition.latitude,
												mActPosition.longitude
														- (location
																.getAccuracy() / 11111)))
								.strokeColor(Color.GREEN).fillColor(Color.CYAN));
				Log.i("debug",
						"Farbe des Genauigkeitskreises: "
								+ mAccuracy.getFillColor());
				for(int i =0;i<mAdresses.size();i++){
					Polyline line = mMap.addPolyline(new PolylineOptions()
				    .add((mActPosition), mAdresses.get(i))
				    .width(5)
				    .color(Color.BLUE));
					
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
				0, locationListener);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapview_alle_orte)).getMap();

		// Geocoder for reverse-geocode the location(adress) to latitude -
		// longitude
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		Log.i("Debug:", "Geocoder initialisiert");
		List<Double> lats = new ArrayList<Double>();
		List<Double> longs = new ArrayList<Double>();
		for (String key : Orte_Adapter.map.keySet()) {
			try {
				String[] values = Orte_Adapter.map.get(key);
				Log.i("debug", "Addresses: " + values[0] + " " + values[1]
						+ " " + values[2]);
				List<Address> addresses = geoCoder.getFromLocationName(
						values[0] + " " + values[1] + " " + values[2], 1);
				if (addresses.size() > 0) {
					Log.i("Debug:", "Passende Lat,Lng gefunden");
					mAdresses.add(new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude()));
					mMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(
											(addresses.get(0).getLatitude()),
											(addresses.get(0).getLongitude())))
							.title(key + "\n" + values[0] + " " + values[1]
									+ "\n" + values[2] + " " + values[3])
							.visible(true));
					lats.add(addresses.get(0).getLatitude());
					longs.add(addresses.get(0).getLongitude());
					Log.i("Debug:", "Marker hinzugefuegt");
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// lats.add(mActPosition.latitude);
		// longs.add(mActPosition.longitude);
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

	}

}
