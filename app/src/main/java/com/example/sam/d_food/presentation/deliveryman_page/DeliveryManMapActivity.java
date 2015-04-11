package com.example.sam.d_food.presentation.deliveryman_page;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.business.location.DeliverymanLocation;
import com.example.sam.d_food.presentation.user_page.TrackDeliveryManActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DeliveryManMapActivity extends Activity {
    private GoogleMap map;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private Location location;
    private Marker task1Marker;
    private Marker task2Marker;
    private Marker task3Marker;
    private String uri;
    private DeliverymanLocation mLocation;

    private final int LOCATION_REFRESH_TIME = 5;
    private final int LOCATION_REFRESH_DISTANCE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_man_map);

        location = updateLocation();
        uri = "geo:" + Double.toString(location.getLatitude()) + ","
                + Double.toString(location.getLongitude());
        zoomToLocation(location);
        Button saveButton = (Button) this.findViewById(R.id.ringButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackDeliveryMan(location);
            }
        });

    }

    protected void trackDeliveryMan(Location loc) {
//        Uri gmmIntentUri = Uri.parse(uri);
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
        Intent locationIntent = new Intent(this, TrackDeliveryManActivity.class);
        locationIntent.putExtra("location", loc);
        startActivity(locationIntent);
    }

    protected void zoomToLocation(Location currentLocation) {
        location = currentLocation;
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        final LatLng task2Location = new LatLng(40.449197, -79.934917);
        LatLng task3Location = new LatLng(40.446650, -79.951912);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        task1Marker = map.addMarker(new MarkerOptions().position(myLocation).title("Task 1"));
        task2Marker = map.addMarker(new MarkerOptions().position(task2Location).title("Task 2"));
        task3Marker = map.addMarker(new MarkerOptions().position(task3Location).title("Task 3"));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                TextView taskName = (TextView) findViewById(R.id.taskNameTextView);
                TextView phone = (TextView) findViewById(R.id.phoneNumTextView);
                if (arg0.getTitle().equals("Task 1")) {
                    task1Marker.showInfoWindow();
                    taskName.setText("Task 1");
                    phone.setText("412-667-888");

                } else if (arg0.getTitle().equals("Task 2")) {
                    task2Marker.showInfoWindow();
                    taskName.setText("Task 2");
                    phone.setText("412-667-999");
                } else if (arg0.getTitle().equals("Task 3")) {
                    task3Marker.showInfoWindow();
                    taskName.setText("Task 3");
                    phone.setText("412-555-888");
                }
                return true;
            }
        });
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
    }

    protected Location updateLocation() {
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);

        location = mLocationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return location;
    }

}
