package com.example.sam.d_food.entities.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Weiwei on 4/11/2015.
 */
public class DeliverymanLocation {
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private double latitude;
    private double longitude;
    private Location mLocation;
    private LatLng mLatLng;
    private int dManID;

    public DeliverymanLocation() {
        latitude = 40.442027;
        longitude = -79.943080;
    }

    public DeliverymanLocation(LatLng dManLL, int id) {
        latitude = dManLL.latitude;
        longitude = dManLL.longitude;
        dManID = id;
    }

    public LatLng getLatLag() {
        return mLatLng;
    }

    public void setLatLng(LatLng ll) {
        mLatLng = ll;
    }

    public void setLocation(Location loc) {
        mLocation = loc;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLatitude(double la) {
        latitude = la;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double lo) {
        longitude = lo;
    }

    public double getLongitude() {
        return longitude;
    }
}
