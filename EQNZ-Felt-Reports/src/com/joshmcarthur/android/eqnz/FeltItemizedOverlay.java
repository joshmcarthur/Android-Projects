package com.joshmcarthur.android.eqnz;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.*;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import android.util.Log;
import org.json.*;

public class FeltItemizedOverlay extends ItemizedOverlay {

	// A collection of all overlay items on the map
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context context;
	
	public FeltItemizedOverlay(Drawable defaultMarker, Context context) {
		//Initialize overlays to be bound at the center-bottom of the image (i.e. pin at this point)
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return this.context;
	}
	
	//Add an overlay to the map, and call populate to read out overlay items and prepare them for drawing
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	
	public void addFeltReportsFrom(String uri) {
		try {
			JSONArray felt_reports = JsonFetcher.fetch(uri).getJSONArray("features");
			for(int i = 0; i < felt_reports.length(); i++) {
				JSONArray coordinates = felt_reports.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
				Double latitude = (double) coordinates.getDouble(0);
				Double longitude = (double) coordinates.getDouble(1);
				int magnitude = (int) felt_reports.getJSONObject(i).getJSONObject("properties").getInt("mm");
				
				OverlayItem item = new OverlayItem((GeoPoint) new LatLongPoint(latitude, longitude), "", "");
				if (magnitude > 0 && magnitude <= 3) {
					item.setMarker(getContext().getResources().getDrawable(R.drawable.felt_pin_mm1_3));
				} else if (magnitude > 3 && magnitude <= 6) {
					item.setMarker(getContext().getResources().getDrawable(R.drawable.felt_pin_mm4_6));
				} else if (magnitude > 6) {
					item.setMarker(getContext().getResources().getDrawable(R.drawable.felt_pin_mm7_10));
				}
				this.addOverlay(item);
			}
			
		} catch (Exception e) {
			Log.e("Adding Felt Reports", e.getMessage());
		}
	}
	
	//Populate() will call createItem - define method to correctly read from our own array list
	@Override
	protected OverlayItem createItem(int index) {
		return mOverlays.get(index);
	}

	//We are required to implement the size method to fetch the size of our array list
	@Override
	public int size() {
		return mOverlays.size();
	}
	
}