package de.jasiflak.duelp;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
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
	MapController mc;
	GeoPoint p;

	// Mapoverlay to draw a marker for the Mapview
	class MapOverlay extends com.google.android.maps.Overlay {
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);

			// Translate GeoPoint to screen Pixels
			Point screenPts = new Point();
			mapView.getProjection().toPixels(p, screenPts);

			// add marker to the Overlay with canvas
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.map_ic_pushpin);

			canvas.drawBitmap(bmp, screenPts.x - 15, screenPts.y - 50, null);
			return true;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Automatisch generierter Methodenstub
		return false;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orte_layout_alle_orte);
		MapView mapView = (MapView) findViewById(R.id.mapview_alle_orte);
		mapView.setBuiltInZoomControls(true);
		Log.i("Debug:", "Start: Klasse Orte Alle");


		// MapController to let the mapview move to a specific position
		mc = mapView.getController();

		// Geocoder for reverse-geocode the location(adress) to latitude -
		// longitude
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocationName("Burj al Arab", 1);

			if (addresses.size() > 0) {
				p = new GeoPoint((int) (addresses.get(0).getLatitude() * 1E6),
						(int) (addresses.get(0).getLongitude() * 1E6));

				// let the mapview move to the specific GeoPoint
				mc.animateTo(p);
				// setting zoomlevel of the mapview
				mc.setZoom(17);
				// adding the mapoverlay with the marker to the mapview
				MapOverlay mapOverlay = new MapOverlay();
				List<Overlay> listOfOverlays = mapView.getOverlays();
				listOfOverlays.clear();
				listOfOverlays.add(mapOverlay);

				mapView.invalidate();
			}
			// if google can't reverse-geocode the adress, throw an exception
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
