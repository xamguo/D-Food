package com.example.sam.d_food.entities.location;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Weiwei on 4/11/2015.
 */
public class CustomerLocation {
    private double latitude;
    private double longitude;
    private Location mLocation;
    private LatLng mLatLng;

    public CustomerLocation() {
        latitude = 40.442027;
        longitude = -79.943080;
    }

    public CustomerLocation(double la, double lo) {
        latitude = la;
        longitude = lo;
    }

    public void setLocation(Location loc) {
        mLocation = loc;
    }

    public Location getLocation() {
        return mLocation;
    }

    public LatLng getLatLag() {
        return mLatLng;
    }

    public void setLatLng(LatLng ll) {
        mLatLng = ll;
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
