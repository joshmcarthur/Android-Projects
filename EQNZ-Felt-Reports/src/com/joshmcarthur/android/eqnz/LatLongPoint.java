package com.joshmcarthur.android.eqnz;

import com.google.android.maps.GeoPoint;

public final class LatLongPoint extends GeoPoint {

	public LatLongPoint(double latitude, double longitude) {
		super((int) (longitude * 1E6), (int)(latitude * 1E6));
	}

}
