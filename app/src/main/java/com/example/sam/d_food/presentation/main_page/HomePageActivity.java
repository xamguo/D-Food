package com.example.sam.d_food.presentation.main_page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.user.Search;
import com.example.sam.d_food.entities.user.SearchReceiver;
import com.example.sam.d_food.ws.remote.DataProgress;
import com.example.sam.d_food.ws.remote.DataService;
import com.example.sam.d_food.presentation.intents.IntentToLogin;
import com.example.sam.d_food.presentation.restaurant_page.RestaurantResultListActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class HomePageActivity extends Activity {

    public static boolean receiverSign = false;

    private String price;
    private SearchReceiver searchReceiver;
    private EditText textView_location;
    private Search s;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button searchButton;
    //public ProgressDialog dialog;

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //submission Test
        setContentView(R.layout.layout_home_page);



        //dialog = new ProgressDialog(this);


        textView_location = (EditText) findViewById(R.id.locationField);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_homepage);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_home_page);
        searchButton = (Button) findViewById(R.id.searchRestaurant);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.layout_list_item, getResources().getStringArray(R.array.planets_array)));

        //add onclick listener of list view here

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" support_home_page_description for accessibility */
                R.string.drawer_close  /* "close drawer" support_home_page_description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        startService();

        SeekBar seekBar = (SeekBar)findViewById(R.id.homeSeekBar);
        seekBar.incrementProgressBy(10);
        seekBar.setMax(20);
        seekBar.setProgress(20);
        price = "2";

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 10;
                progress = progress * 10;
                switch (progress){
                    case 0:
                        price = "0";
                        break;
                    case 10:
                        price = "1";
                        break;
                    case 20:
                        price = "2";
                        break;
                    default:
                        //Exception
                        price = "3";
                        break;
                }
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 search();
            }
        });
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel > 8) {
                search();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;    //click the icon
        }

        if(item.getItemId() == R.id.login)
        {
            IntentToLogin intent = new IntentToLogin(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void search(){
        //dialog.setMessage("Searching");
        //dialog.show();


//        String location = textView_location.getText().toString();
//        location = "CMU";
//        Log.v("location", location);
//
//        if(location == null){
//            location = "CMU";
//        }
//
//        if(s != null) {
//            s.unbindService();
//        }
//
//        if(receiverSign == true){
//            unReceiver();
//        }
//        receiver();
//        s = new Search(HomePageActivity.this);
//        double x,y;
//        x=1.1;
//        y=2.2;      //value for simulation
//        s.search(x,y,price,location);
        try {
            DataProgress dataProgress = new DataProgress(this);
            dataProgress.execute(" ").get();
            //ArrayList<HashMap<String, String>> restaurantList = dataProgress.getJsonlist();
            Intent intent = new Intent(this, RestaurantResultListActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //dialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
//        if(s != null) {
//            //unReceiver();
//            s.unbindService();
//            Log.v("unbindService","here");
//        }
//        if(receiverSign == true){
//            unReceiver();
//        }
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
    }

    private void startService() {
        startService(new Intent(this, DataService.class));
    }


    private void receiver() {
        searchReceiver = new SearchReceiver(this, RestaurantResultListActivity.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MY_ACTION");
        registerReceiver(searchReceiver, intentFilter);
        receiverSign = true;
    }

    private void unReceiver() {
        receiverSign = false;
        unregisterReceiver(searchReceiver);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();  //to change the icon on action bar
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
