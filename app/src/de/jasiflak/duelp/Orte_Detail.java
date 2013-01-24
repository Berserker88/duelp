package de.jasiflak.duelp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Orte_Detail extends MapActivity {
	
	Boolean map_type = false;
	MapController mc;
	GeoPoint p;
	MapOverlay mMapOverlay = new MapOverlay();
	List<Overlay> mListOfOverlays;
	MapView mMapView;

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
		
		return false;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orte_layout_detail);
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);
		
		Log.i("Debug:", "Start: Klasse Orte Detail");
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		final String[] values = intent.getStringArrayExtra("values");
		final Double[] latlng = new Double[2];
		latlng[0] = Double.parseDouble(values[4]);
		latlng[1] = Double.parseDouble(values[5]);
		

		TextView text_detail_name = (TextView) findViewById(R.id.name);
		text_detail_name.setText(name);
		TextView text_detail_street = (TextView) findViewById(R.id.street);
		text_detail_street.setText(values[0] + " " + values[1]);
		TextView text_detail_zipcode = (TextView) findViewById(R.id.zipcode);
		text_detail_zipcode.setText(values[2]);
		TextView text_detail_city = (TextView) findViewById(R.id.city);
		text_detail_city.setText(values[3]);

		// MapController to let the mapview move to a specific position
		mc = mMapView.getController();

		p = new GeoPoint((int) (latlng[0] * 1E6),
				(int) (latlng[1] * 1E6));

		// let the mapview move to the specific GeoPoint
		mc.animateTo(p);
		// setting zoomlevel of the mapview
		mc.setZoom(17);
		// adding the mapoverlay with the marker to the mapview
		
		mListOfOverlays = mMapView.getOverlays();
		mListOfOverlays.clear();
		
		mListOfOverlays.add(mMapOverlay);		
		mMapView.invalidate();
		ImageView typetoggle = (ImageView) findViewById(R.id.map_ic_type_toggle);
		
		
		ImageView button = (ImageView) findViewById(R.id.start_routing);
		
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("debug","Start routing with: "+latlng[0].toString()+latlng[1].toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("google.navigation:q="+values[0]+" "+values[1]+","+values[2]+" "+values[3]));
                startActivity(intent);
            }
        });
		
		typetoggle.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {	
				Log.i("debug","map_toggle_clicked");
				
				mListOfOverlays.clear();
				if(map_type){
					mMapView.setSatellite(false);
					map_type = false;
				}else
				{
					mMapView.setSatellite(true);
					map_type = true;
				}
				
				mListOfOverlays.add(mMapOverlay);		
				mMapView.invalidate();
			}
		});
				
		
	}
	
	

}
