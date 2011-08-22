package com.joshmcarthur.android.eqnz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.android.maps.*;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.*;

public class FeltMapActivity extends MapActivity {
	/** Instantiate required handles on map objects */
	LinearLayout linearLayout;
	MapView mapView;
	MapController mapController;
	List<Overlay> mapOverlays;
	Drawable drawable;
	FeltItemizedOverlay itemizedOverlay;
	LocationManager locationManager;
	SimpleDateFormat geonetDateFormat;
	

	/** Triggered when the application is started */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/* Required setup */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        geonetDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        setupMapView();
	}
	
	
   private void setupMapView() {
	   
		mapView = (MapView) findViewById(R.id.mapview);
		mapController = mapView.getController();
		mapView.setBuiltInZoomControls(true);
		
		//Set map to last-known location
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		if(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
			//setMapCenter((GeoPoint) new LatLongPoint(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude(), locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude()));	
		}
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mapLocationListener);
		
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.felt_pin);
		itemizedOverlay = new FeltItemizedOverlay(drawable, getApplication());
		itemizedOverlay.addFeltReportsFrom("http://magma.geonet.org.nz/services/quake/geojson/felt?startDate=2011-08-21&endDate=2011-08-22");
		mapOverlays.add(itemizedOverlay);	
	}
   
   private String geonetURL() {
	Calendar start_date = Calendar.getInstance();
    start_date.add(Calendar.HOUR_OF_DAY, -24);
	return String.format("http://magma.geonet.org.nz/services/quake/geojson/felt?startDate=%s&endDate=%s", geonetDateFormat.format(start_date.getTime()), geonetDateFormat.format(new Date()));
}


private void setMapCenter(GeoPoint geoPoint) {
	mapController.animateTo(geoPoint);
	}

/** Setup a Location Listener to respond to changes in location */
   LocationListener mapLocationListener = new LocationListener() {
	   
	   @Override
	   public void onLocationChanged(Location location) {
		   setMapCenter((GeoPoint) new LatLongPoint(location.getLatitude(), location.getLongitude()));	   
	   }

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
	   
	   
   };


/** Required by Map Activity */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}