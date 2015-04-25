package com.example.sam.d_food.presentation.user_page;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sam.d_food.R;
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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
    private TextView nameText;
    private GoogleMap map;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private Marker deliveryMarker;
    private Marker dManMarker;
    private String totalDuration;
    private String totalDistance = "";
    private String dInfo = " mile away";
    private Location location;
    private LatLng aLL;
    private LatLng dMan;
    private Marker myMarker;
    private int interval = 5500;
    private String dManLocData;
    private JSONObject dManJObject;
    private Button tractButton;
    private Button ringButton;

    private int locTest = 0;
    private final int LOCATION_REFRESH_TIME = 5;
    private final int LOCATION_REFRESH_DISTANCE = 100;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_track_delivery_man);


        numText = (TextView) findViewById(R.id.deliverymanNumTextView);
        initial();
        trackMyLocation();


        tractButton = (Button) findViewById(R.id.tractButton);
        ringButton = (Button) findViewById(R.id.customCallButton);
//        MyPost locMyPost = new MyPost();
//        locMyPost.execute(null);
//        uploadLocation();

        ringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vs = Context.VIBRATOR_SERVICE;
                Vibrator mVibrator = (Vibrator)getSystemService(vs);

                boolean isVibrator = mVibrator.hasVibrator();
                notifyCustomer(savedInstanceState);
                Log.v("Ring","hi");
                Log.v("Vibrator",Boolean.toString(isVibrator));
//                Vibrator vib;
//                vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                vib.vibrate(5000);
//                Log.v("Ring","hi");
            }
        });


//        Timer t = new Timer();
//        t.scheduleAtFixedRate(new TimerTask() {
//
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable()
//                {
//                    public void run()
//                    {
//                        try {
//                            zoomToDeliveryman();
//                            tractButton.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    trackDeliveryMan();
//                                }
//                            });
//
//                            ringButton.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    notifyCustomer();
//                                    Log.v("Ring","hi");
//                                }
//                            });
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        }, 0, interval);

    }

    protected void notifyCustomer(Bundle bundle) {
//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        MediaPlayer player = MediaPlayer.create(this, notification);
//        player.setLooping(true);
//        player.start();

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        int icon = R.drawable.logo4;
        CharSequence tickerText = "bla bla";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        Context context = getApplicationContext();
        CharSequence contentTitle = "My notification";
        CharSequence contentText = "Hello World!";
        Intent notificationIntent = new Intent(this, TrackDeliveryManActivity.class);
        if(bundle!=null)
            notificationIntent.putExtras(bundle); //you may put bundle or not
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        int any_ID_you_want = 1;
        //if you send another notification with same ID, this will be replaced by the other one
        mNotificationManager.notify(any_ID_you_want,notification);

    }

    protected void initial() {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.trackMap))
                .getMap();
        map.setMyLocationEnabled(true);

        String dName = "Deliveryman";
        aLL = new LatLng(40.440320, -80.003079);
        dManMarker = map.addMarker(new MarkerOptions()
                .position(aLL)
                .title(dName)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.deliveryman)));
    }

    protected void uploadLocation() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Log.v("Counter", "ggg");
                postData();
                updateDmanLocation("http://guoxiao113.oicp.net/D_Food_Server/user_track?id=1\n");
//                try {
//                    zoomToDeliveryman();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

        }, 0, interval);
    }

    protected void updateDmanLocation(String url) {
        try {
            dManLocData = downloadUrl(url);
            try {
                dManJObject = new JSONObject(dManLocData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v("update dman loc", dManLocData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void zoomToDeliveryman() throws JSONException {
        if (dManJObject != null) {
            locTest++;
            Double lat = 40.440320;
            Double lng = -80.003079;
            lat = Double.parseDouble(dManJObject.getString("latitude")) + locTest*0.0003;
            lng = Double.parseDouble(dManJObject.getString("longitude"))+ locTest*0.0003;
            dMan = new LatLng(lat,lng);
            Log.v("log", lat.toString());
            Log.v("lng", lng.toString());
            dManMarker.setPosition(dMan);
            dManMarker.hideInfoWindow();
            dManMarker.showInfoWindow();
        }
    }

    public void getDmanLocation() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/user_track?");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("id", "1"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    protected void trackDeliveryMan() {
        LatLng newLoc = null;
        LatLng myLocation = null;
        Location location = null;


        map.setMyLocationEnabled(true);
        location = map.getMyLocation();


        if (location == null) {

        } else {
            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            myMarker =  map.addMarker(new MarkerOptions().position(myLocation).title("Your Location"));

//            dInfo = totalDistance + " mile away";

            String url = getDirectionsUrl(myLocation, aLL);

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

    public class MyPost extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] params) {
            postData();
            return null;
        }
    }

    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/track?");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("id", "1"));
            nameValuePairs.add(new BasicNameValuePair("latitude", "40.440320"));
            nameValuePairs.add(new BasicNameValuePair("longitude", "-80.003079"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    protected void trackMyLocation() {

        LatLng myLocation;

        myLocation = new LatLng(40.446650, -79.951912);
        zoomToLocation();
        numText = (TextView) findViewById(R.id.dManNumText);
        numText.setText("412-111-2222");

        nameText = (TextView) findViewById(R.id.dManNameText);
        nameText.setText("Function Li");
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

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String bestProvider = mLocationManager.getBestProvider(criteria, false);

        mLocationManager.requestLocationUpdates(bestProvider, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);
        location = mLocationManager
                .getLastKnownLocation(bestProvider);

        return location;

//        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
//                LOCATION_REFRESH_DISTANCE, mLocationListener);
//
//        Location customerLocation = mLocationManager
//                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        return customerLocation;
    }
    protected void zoomToLocation() {
        LatLng newLoc = null;
        LatLng myLocation = null;

        location = map.getMyLocation();
        if (location == null) {
            location = updateLocation();
        }

        if (location == null) {

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

        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
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
            String d = String.format("%.1f", dis/1600);
            return d;
        }

        protected String toTime(double t) {
            double stringTime = t;
            int min = 0;
            int hour = 0;

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
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
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

}
