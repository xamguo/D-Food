package com.example.sam.d_food.presentation.deliveryman_page;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.example.sam.d_food.presentation.user_page.TrackDeliveryManActivity;
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
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DeliveryManMapActivity extends Activity {
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

    private int count;
    private int locFlag = 0;
    private final int interval = 1000;
    private final int LOCATION_REFRESH_TIME = 5;
    private final int LOCATION_REFRESH_DISTANCE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_man_map);
        activity = DeliveryManMapActivity.this;
        Intent intent = getIntent();
        dManID = intent.getStringExtra("deliverymanID");
        initial();
        uploadLocation();
        Button saveButton = (Button) this.findViewById(R.id.ringButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    protected void uploadLocation() {
        if (dManLocation == null) {
            dManLocation = updateLocation();
        }
        dManLL = new LatLng(dManLocation.getLatitude(), dManLocation.getLongitude());
        Timer uploadTimer = new Timer();
        uploadTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                ShareLocationProcess shareLoc = new ShareLocationProcess();
                shareLoc.execute(dManLL);
//                Log.v("Counter", "ggg");
//                postData();
//                updateDmanLocation("http://guoxiao113.oicp.net/D_Food_Server/user_track?id=1\n");
//                try {
//                    zoomToDeliveryman();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

        }, 0, interval);
    }

    protected boolean initial() {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        FindLocation();
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

                zoomToLocation(dManLocation);
            }


        } else {
            return false;
        }
        return true;
    }

    protected void FindLocation() {
        count = 5000;
        t = new Timer();

        dialog = new ProgressDialog(activity);
        dialog.setMessage("Progress start");
        dialog.show();
        Log.v("start", "ssssssssss");
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                                .getMap();
                        dManLocation = map.getMyLocation();
                        if (dManLocation != null) {
                            locFlag = 1;
                            t.cancel();
                        } else if (count == 0) {
                            locFlag = 0;
                            t.cancel();
                        }
                        count = count - 1000;
                        Log.v("count", "cc");
                    }
                });
            }
        }, 0, interval);

        if (dialog.isShowing()) {
            dialog.cancel();
        }

    }

    protected void zoomToLocation(Location loc) {

        if (loc == null) {
            loc = updateLocation();
        }

        if (loc == null) {

        } else {
            LatLng myLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
        }
    }

    protected void trackDeliveryMan(Location loc) {
//        Uri gmmIntentUri = Uri.parse(uri);
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
        Intent locationIntent = new Intent(this, TrackDeliveryManActivity.class);
        locationIntent.putExtra("location", loc);
        startActivity(locationIntent);
    }

//    protected void showTaskLocation(Location currentLocation) {
//        location = currentLocation;
//        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
//        final LatLng task2Location = new LatLng(40.449197, -79.934917);
//        LatLng task3Location = new LatLng(40.446650, -79.951912);
//        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
//                .getMap();
//
//        task1Marker = map.addMarker(new MarkerOptions().position(myLocation).title("Task 1"));
//        task2Marker = map.addMarker(new MarkerOptions().position(task2Location).title("Task 2"));
//        task3Marker = map.addMarker(new MarkerOptions().position(task3Location).title("Task 3"));
//
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
//        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//            }
//        });
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
//    }

    private class ShareLocationProcess extends AsyncTask<LatLng, Void, String> {

//    public ShareLocationProcess(Activity act) {
//        super();
//        activity = act;
//    }

        protected String doInBackground(LatLng... location) {
            postData(location[0]);
            return null;
        }

        protected void onPostExecute(String result) {

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
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
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

}
