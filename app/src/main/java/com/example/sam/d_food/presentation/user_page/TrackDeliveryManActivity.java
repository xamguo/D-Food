package com.example.sam.d_food.presentation.user_page;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sam.d_food.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class TrackDeliveryManActivity extends Activity {
    private TextView numText;
    private GoogleMap map;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private Marker deliveryMarker;
    private final int LOCATION_REFRESH_TIME = 5;
    private final int LOCATION_REFRESH_DISTANCE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_delivery_man);


        numText = (TextView) findViewById(R.id.deliverymanNumTextView);
        tractDeliveryMan();

    }

    protected void tractDeliveryMan() {
//        location = (Location) getIntent().getSerializableExtra("location");
        LatLng myLocation;
//        if (location == null) {
//            myLocation = new LatLng(40.446650, -79.951912);
//            zoomToLocation(myLocation);
//        } else {
//            LatLng deliveryLocation = new LatLng(location.getLatitude(), location.getLongitude());
//            zoomToLocation(deliveryLocation);
//        }

        myLocation = new LatLng(40.446650, -79.951912);
        zoomToLocation(myLocation);
        numText = (TextView) findViewById(R.id.deliverymanNumTextView);
        numText.setText("412-111-2222");
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

        Location customerLocation = mLocationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return customerLocation;
    }
    protected void zoomToLocation(LatLng dLoc) {
        LatLng myLocation = dLoc;
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.trackMap))
                .getMap();

        deliveryMarker = map.addMarker(new MarkerOptions().position(myLocation).title("Delivery Man"));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                TextView taskName = (TextView) findViewById(R.id.taskNameTextView);
                TextView phone = (TextView) findViewById(R.id.phoneNumTextView);
                if (arg0.getTitle().equals("Task 1")) {
                    deliveryMarker.showInfoWindow();
                    taskName.setText("Task 1");
                    phone.setText("412-667-888");
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
}
