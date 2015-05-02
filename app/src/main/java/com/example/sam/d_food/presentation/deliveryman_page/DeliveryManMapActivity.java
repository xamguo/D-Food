package com.example.sam.d_food.presentation.deliveryman_page;

import android.app.Activity;
import android.app.ProgressDialog;
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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DeliveryManMapActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap map;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private Marker task1Marker;
    private Marker task2Marker;
    private Marker task3Marker;
    private Marker dManMarker;
    private String uri;
    private ProgressDialog dialog;
    private Location dManLocation;
    private LatLng dManLL;
    private String dManID;
    private Timer t;
    private Activity activity;
    private ArrayList<Task> userList = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private Timer upLoadTimer;

    private Thread thread;
    private String bpSign;
    private Timer updateTimer;
    private int count;
    private int locFlag = 0;
    private boolean stop = false;
    private final int interval = 4000;
    private final int LOCATION_REFRESH_TIME = 5;
    private final int LOCATION_REFRESH_DISTANCE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_man_map);
        activity = DeliveryManMapActivity.this;
        stop = false;

        final Bundle myB = savedInstanceState;
        buildGoogleApiClient();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        onConnected(savedInstanceState);



        Intent intent = getIntent();
        dManID = intent.getStringExtra("deliverymanID");
        Button ringButton = (Button) this.findViewById(R.id.ringButton);
        initial();

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
                            }
                        } else {
                            Log.v("stop indicator", "stopped now");
                            updateTimer.cancel();
                        }
                    }
                });
            }
        }, 0, interval);

        ringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dManLL != null) {
                    bpSign = "true";
                    if (bpSign.equals("true")) {
                        thread = new Thread() {
                            @Override
                            public void run() {
                                postData(dManLL);
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
                postData(dManLL);
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
                String dName = "Deliveryman";
                dManLL = new LatLng(dManLocation.getLatitude(), dManLocation.getLongitude());
                dManMarker = map.addMarker(new MarkerOptions()
                        .position(dManLL)
                        .title(dName)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.deliveryman)));
                if (dManMarker.isInfoWindowShown()) {
                    dManMarker.hideInfoWindow();
                }
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
            String taskName = null;
            LatLng userLL = new LatLng(userList.get(i).getLatitude(),userList.get(i).getLongitude());
            taskName = "Task" + Integer.toString(i);
            Log.v("userLocation", Double.toString(userList.get(i).getLatitude()));
            map.addMarker(new MarkerOptions().position(userLL).title(taskName)).showInfoWindow();
        }

//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker arg0) {
//                TextView taskName = (TextView) findViewById(R.id.taskNameTextView);
//                TextView phone = (TextView) findViewById(R.id.phoneNumTextView);
//                if (arg0.getTitle().equals("Task 1")) {
//                    task1Marker.showInfoWindow();
//                    taskName.setText("Task 1");
//                    phone.setText("412-667-888");
//
//                } else if (arg0.getTitle().equals("Task 2")) {
//                    task2Marker.showInfoWindow();
//                    taskName.setText("Task 2");
//                    phone.setText("412-667-999");
//                } else if (arg0.getTitle().equals("Task 3")) {
//                    task3Marker.showInfoWindow();
//                    taskName.setText("Task 3");
//                    phone.setText("412-555-888");
//                }
//                return false;
//            }
//        });
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
                postData(dManLL);
                upLoadTimer.cancel();
            }

        }, 0, interval);
    }

    public void postData(LatLng location) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/track?");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("id", dManID));
            nameValuePairs.add(new BasicNameValuePair("latitude", Double.toString(location.latitude)));
            nameValuePairs.add(new BasicNameValuePair("longitude", Double.toString(location.longitude)));
            nameValuePairs.add(new BasicNameValuePair("beepSign", bpSign));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            bpSign = "false";
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
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
