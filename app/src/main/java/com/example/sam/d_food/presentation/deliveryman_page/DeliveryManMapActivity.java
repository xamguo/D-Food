/*
* DeliveryManMapActivity - activity to guide the deliveryman find the user
* and notify the user. This activity is responsible to continue contact with
* server and send the beep signal to server.
* */
package com.example.sam.d_food.presentation.deliveryman_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.deliveryman.Task;
import com.example.sam.d_food.entities.deliveryman.TaskProxy;
import com.example.sam.d_food.ws.processes.ShareLocationProcess;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DeliveryManMapActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    /* location support modules */
    private GoogleMap map;
    private LocationManager mLocationManager;
    private Marker dManMarker;
    private Location dManLocation;
    private GoogleApiClient mGoogleApiClient;

    /* user and deliveryman info */
    private LatLng dManLL;
    private String dManID;
    private ArrayList<Task> userList = new ArrayList<>();

    /* beep signal */
    private String bpSign;

    /* Update timers */
    private Thread thread;
    private Timer upLoadTimer;
    private Timer updateTimer;
    private boolean stop = false;
    private final int interval = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_man_map);
        stop = false;
        final Bundle myB = savedInstanceState;

        /* location setting */
        buildGoogleApiClient();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        onConnected(savedInstanceState);


        Intent intent = getIntent();
        dManID = intent.getStringExtra("deliverymanID");
        Button ringButton = (Button) this.findViewById(R.id.ringButton);
        initial();

        /* update process settings */
        MyPost updateLoc = new MyPost();
        updateLoc.execute();
        updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if(!stop) {
                            onConnected(myB);
                            String dName = "Deliveryman";
                            if (dManLocation != null) {
                                uploadLocation();
                                dManLL = new LatLng(dManLocation.getLatitude(), dManLocation.getLongitude());
                                if (map != null) {
                                    map.clear();
                                }
                                dManMarker = map.addMarker(new MarkerOptions()
                                        .position(dManLL)
                                        .title(dName)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.deliveryman)));
                                //                            zoomToLocation(dManLocation, dManMarker);
                                Log.v("lat", Double.toString(dManLocation.getLatitude()));
                                Log.v("lon", Double.toString(dManLocation.getLongitude()));
                                showTaskLocation();
                            }
                        } else {
                            Log.v("stop indicator", "stopped now");
                            updateTimer.cancel();
                        }
                    }
                });
            }
        }, 0, interval);

        /* ringButton settings */
        ringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dManLL != null) {
                    bpSign = "true";
                    if (bpSign.equals("true")) {
                        thread = new Thread() {
                            @Override
                            public void run() {
                                ShareLocationProcess dShare = new ShareLocationProcess(dManLL);
                                dShare.shareLoc(dManID, bpSign);
                                bpSign = "false";
                            }
                        };
                        thread.start();
                    } else {
                        thread.stop();
                    }
                }
            }
        });

    }

    private void putToList(Task t) {
        int flag = 0;
        for (int i = 0; i < userList.size(); i++) {
            if (t.getUserName().equals(userList.get(i).getUserName()) ) {
                flag = 1;
            }
        }
        if (flag == 0) {
            userList.add(t);
        }
    }

    public void onConnected(Bundle b) {
        Log.v("Connection Test", Boolean.toString(mGoogleApiClient.isConnected()));
        dManLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public class MyPost extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] params) {
            if (dManLL != null) {
                ShareLocationProcess dShare = new ShareLocationProcess(dManLL);
                dShare.shareLoc(dManID, bpSign);
                bpSign = "false";
            }

            return null;
        }
    }

    protected boolean initial() {
        ArrayList<Task> myTasks = TaskProxy.getTaskList();
        for(int i = 0; i < myTasks.size(); i++) {
            putToList(myTasks.get(i));
        }
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        if (map != null) {
            map.setMyLocationEnabled(true);
            dManLocation = map.getMyLocation();
            if (dManLocation == null) {
                dManLocation = updateLocation();
            }
            if (dManLocation != null) {
                String dName = "My Location";
                dManLL = new LatLng(dManLocation.getLatitude(), dManLocation.getLongitude());
                dManMarker = map.addMarker(new MarkerOptions()
                        .position(dManLL)
                        .title(dName)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.deliveryman)));
                if (dManMarker.isInfoWindowShown()) {
                    dManMarker.hideInfoWindow();
                }
                dManMarker.showInfoWindow();
                zoomToLocation(dManLocation, dManMarker);
            }

            showTaskLocation();
        } else {
            return false;
        }
        return true;
    }


    protected void zoomToLocation(Location loc, Marker mk) {
        if (mk.isInfoWindowShown()) {
            mk.hideInfoWindow();
        }
        if (loc != null) {
            LatLng myLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
            if (mk.isInfoWindowShown()) {
                mk.hideInfoWindow();
            }
//            mk.showInfoWindow();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
        }
    }


    protected void showTaskLocation() {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        for (int i = 0; i < userList.size(); i++) {
            String taskName;
            LatLng userLL = new LatLng(userList.get(i).getLatitude(),userList.get(i).getLongitude());
            taskName = "Task" + Integer.toString(i);
            Log.v("userLocation", Double.toString(userList.get(i).getLatitude()));
            map.addMarker(new MarkerOptions().position(userLL).title(taskName)).showInfoWindow();
        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
    }


    protected void uploadLocation() {
        upLoadTimer = new Timer();
        upLoadTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Log.v("Counter", "ggg");
                ShareLocationProcess dShare = new ShareLocationProcess(dManLL);
                dShare.shareLoc(dManID, bpSign);
                bpSign = "false";
                upLoadTimer.cancel();
            }

        }, 0, interval);
    }


    protected Location updateLocation() {
        LocationListener mLocationListener = new LocationListener() {
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

        int LOCATION_REFRESH_DISTANCE = 100;
        int LOCATION_REFRESH_TIME = 5;
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);

        dManLocation = mLocationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return dManLocation;
    }

    @Override
    protected void onPause() {
        stop = true;
        Log.v("activity ", "paused");
        upLoadTimer.cancel();
        updateTimer.cancel();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        stop = true;
        Log.v("activity ", "stopped");
        upLoadTimer.cancel();
        updateTimer.cancel();
        super.onDestroy();
    }
}
