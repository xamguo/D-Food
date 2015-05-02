package com.example.sam.d_food.presentation.user_page;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sam.d_food.ExceptionHandler.DownloadDataException;
import com.example.sam.d_food.ExceptionHandler.GPSServiceException;
import com.example.sam.d_food.ExceptionHandler.WiFiServiceException;
import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.user.User;
import com.example.sam.d_food.presentation.user_page.json_parser.DirectionsJSONParser;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TrackDeliveryManActivity extends Activity {
    private TextView numText;
    private GoogleMap map;
    private Marker dManMarker;
    private String totalDuration;
    private String totalDistance = "";
    private String deliverymanID;
    private String dInfo = " mile away";
    private Location location;
    private LatLng dMan;
    private Marker myMarker;
    private int interval = 4000;
    private JSONObject dManJObject;
    private Button tractButton;
    private Context TD_Context;
    private Timer updateDmanTimer;
    private Timer t;
    private String bpSign = "false";
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_track_delivery_man);
        activity = getParent();
        TD_Context = TrackDeliveryManActivity.this;

//        Bundle extras = getIntent().getExtras();
        deliverymanID = Integer.toString(User.getDeliverymanID());
//        deliverymanID = String.valueOf(extras.getInt("deliverymanID"));
        Log.v("Track deliverymanID",deliverymanID);

        numText = (TextView) findViewById(R.id.deliverymanNumTextView);
        tractButton = (Button) findViewById(R.id.tractButton);
        try {
            checkLocServiceEnabled();
        } catch (GPSServiceException | WiFiServiceException e) {
            e.printStackTrace();
        }

        //Initial the map fragment.
        if (initial()) {
            // Focus on my own location.
            trackMyLocation();
        }

        //Update the deliveryman location every 4 seconds.
        updateDmanLocation();

        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        try {
                            zoomToDeliveryman();
                            tractButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    trackDeliveryMan();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 0, interval);
    }

    protected void checkLocServiceEnabled() throws GPSServiceException, WiFiServiceException {
        LocationManager lm;
        boolean gps_enabled,network_enabled;
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch(Exception ex){
            throw new GPSServiceException("Please Enable GPS Servie");
        }
        try{
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception ex){
            throw new WiFiServiceException("Please Enable Wifi Service");
        }

        if(!gps_enabled && !network_enabled){
            AlertDialog.Builder dialog = new AlertDialog.Builder(TD_Context);
            dialog.setMessage(TD_Context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(TD_Context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    TD_Context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(TD_Context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();

        }
    }

    protected void notifyCustomer() {
//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        MediaPlayer player = MediaPlayer.create(this, notification);
//        player.setLooping(true);
//        player.start();

        Intent notificationIntent = new Intent(TD_Context, TrackDeliveryManActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(TD_Context, 0, notificationIntent, 0);

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        Notification dManNotification = new Notification.Builder(TD_Context)
                .setSmallIcon(R.drawable.logo4)
                .setContentTitle(TD_Context.getString(R.string.app_name))
                .setContentIntent(intent)
                .setPriority(5) //private static final PRIORITY_HIGH = 5;
                .setContentText("I'm in front of your door")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS).build();

        int any_ID_you_want = 1;
        //if you send another notification with same ID, this will be replaced by the other one
        mNotificationManager.notify(any_ID_you_want,dManNotification);

    }

    protected boolean initial() {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.trackMap))
                .getMap();

        if (map != null) {
            map.setMyLocationEnabled(true);
            String dName = "Deliveryman";
            LatLng aLL = new LatLng(40.440320, -80.003079);
            dManMarker = map.addMarker(new MarkerOptions()
                    .position(aLL)
                    .title(dName)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.deliveryman)));

        } else {
            return false;
        }
        return true;
    }

    // Update the deliveryman location
    protected void updateDmanLocation() {
        updateDmanTimer = new Timer();
        updateDmanTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Log.v("Counter", "ggg");
                String dManUrl = "http://guoxiao113.oicp.net/D_Food_Server/user_track?id=" + deliverymanID;
                if (!deliverymanID.equals("-1")) {
                    freshDmanLocation(dManUrl);
                }
                if (bpSign.equals("true")) {
                    notifyCustomer();
                }
            }

        }, 0, interval);
    }

    //Get the deliveryman location info from the server
    protected void freshDmanLocation(String url) {
        try {
            String dManLocData = downloadUrl(url);
            try {
                dManJObject = new JSONObject(dManLocData);
            } catch (JSONException e) {
                DownloadDataException dataException = new DownloadDataException();
                dataException.print(activity);
                e.printStackTrace();
            }
            Log.v("update dman loc", dManLocData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Show the deliveryman location
    protected void zoomToDeliveryman() throws JSONException {
        if (dManJObject != null) {
            Double lat;
            Double lng;
            lat = Double.parseDouble(dManJObject.getString("latitude"));
            lng = Double.parseDouble(dManJObject.getString("longitude"));
            bpSign = dManJObject.getString("beepSign");
            dMan = new LatLng(lat,lng);
            Log.v("log", lat.toString());
            Log.v("lng", lng.toString());
            dManMarker.setPosition(dMan);
            dManMarker.hideInfoWindow();
            dManMarker.showInfoWindow();
        }
    }


    protected void trackDeliveryMan() {
        LatLng myLocation;
        String dName = "Deliveryman";

        map.setMyLocationEnabled(true);
        map.clear();
        location = map.getMyLocation();


        if (location == null || dMan == null) {
            location = null;
        } else {
            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            myMarker =  map.addMarker(new MarkerOptions().position(myLocation).title("Your Location"));
            dManMarker = map.addMarker(new MarkerOptions()
                    .position(dMan)
                    .title(dName)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.deliveryman)));
//            dInfo = totalDistance + " mile away";
            String url = getDirectionsUrl(myLocation, dMan);
            DownloadTask downloadTask = new DownloadTask();

            downloadTask.execute(url);

//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(myMarker.getPosition());
            builder.include(dManMarker.getPosition());
            LatLngBounds bounds = builder.build();

            int padding = 140;
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            map.animateCamera(cu);

            dManMarker.setSnippet(totalDistance + dInfo);

            if (dManMarker.isInfoWindowShown()) {
                dManMarker.hideInfoWindow();
            }
            dManMarker.showInfoWindow();
            myMarker.showInfoWindow();

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {

                    if (arg0.getTitle().equals("Your Location")) {
                        myMarker.showInfoWindow();
                    } else {
                        dManMarker.setSnippet(totalDistance + dInfo);
                        Log.v("after", Boolean.toString(dManMarker.isInfoWindowShown()));
                        if (dManMarker.isInfoWindowShown()) {
                            dManMarker.hideInfoWindow();
                        }
                        dManMarker.showInfoWindow();
                    }
                    return false;
                }
            });
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                }
            });

            TextView durationView = (TextView) findViewById(R.id.durationTextView);
            durationView.setText(totalDuration);

        }
    }


    protected void trackMyLocation() {
        zoomToLocation();
        numText = (TextView) findViewById(R.id.dManNumText);
        numText.setText("412-111-2222");

        TextView nameText = (TextView) findViewById(R.id.dManNameText);
        nameText.setText("Function Li");
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

        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String bestProvider = mLocationManager.getBestProvider(criteria, false);

        int LOCATION_REFRESH_TIME = 5;
        int LOCATION_REFRESH_DISTANCE = 100;
        mLocationManager.requestLocationUpdates(bestProvider, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);
        location = mLocationManager
                .getLastKnownLocation(bestProvider);

        return location;

    }
    protected void zoomToLocation() {
        LatLng myLocation;

        location = map.getMyLocation();
        if (location == null) {
            location = updateLocation();
        }

        if (location == null) {
            location = updateLocation();
        } else {
            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
        }
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        return "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            // Reading data from url
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sb;
            sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (IOException e) {
            Log.d("Exception url", e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
                double t = parser.parseDuration(jObject);
                double dis = parser.getDistance();

                totalDistance = toDistance(dis);
                totalDuration = toTime(t);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        protected String toDistance(double dis) {
            return String.format("%.1f", dis/1600);
        }

        protected String toTime(double t) {
            int min;
            int hour;

            if (t < 60) {
                return "1 min";
            } else {
                if (t < 3600) {
                    min =1 + (int) t/60;
                    return min + "min";
                } else {
                    hour = (int) t/3600;
                    min = 1 + (int) (t - hour*3600)/60;
                    return hour + "hour" + min + "min";
                }
            }
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.parseColor("#FF419AFF"));
            }

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }

    @Override
    protected void onPause() {
        Log.v("activity ", "paused");
        t.cancel();
        updateDmanTimer.cancel();
        super.onPause();
    }



    @Override
    public void onDestroy() {
        Log.v("activity ", "stopped");
        t.cancel();
        updateDmanTimer.cancel();
        super.onDestroy();
    }
}
