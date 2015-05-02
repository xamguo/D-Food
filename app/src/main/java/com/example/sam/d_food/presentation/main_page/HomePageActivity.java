package com.example.sam.d_food.presentation.main_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;    //changed to v7
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.user.Cart;
import com.example.sam.d_food.entities.user.User;
import com.example.sam.d_food.presentation.intents.IntentToCheck;
import com.example.sam.d_food.presentation.intents.IntentToDeliverymanHome;
import com.example.sam.d_food.presentation.intents.IntentToUserHome;
import com.example.sam.d_food.presentation.user_page.UserHomePageActivity;
import com.example.sam.d_food.ws.remote.SearchProgress;
import com.example.sam.d_food.presentation.intents.IntentToLogin;
import com.example.sam.d_food.presentation.main_flow_pages.FragmentContainerActivity;


public class HomePageActivity extends Activity {

    public static boolean isDeliveryman = false;

    private String price;
    private EditText textView_location;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button searchButton;

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private int shakeFlag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //submission Test
        setContentView(R.layout.layout_home_page);

        setLocation(-80.003079,40.440320);

        //dialog = new ProgressDialog(this);
        shakeFlag = 0;
        textView_location = (EditText) findViewById(R.id.locationField);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_homepage);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_home_page);
        searchButton = (Button) findViewById(R.id.searchRestaurant);

        /* Drawer settings */
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.layout_list_item, getResources().getStringArray(R.array.drawer_items)));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
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

        /* SeekBar settings */
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

        /* Sensor settings */
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        /* Search button settings */
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
                if (shakeFlag == 0) {
                    shakeFlag = 1;
                    Log.v("shake++++++++++++++++++", Integer.toString(shakeFlag));
                    search();
                }
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == 1){
                IntentToLogin intent = new IntentToLogin(HomePageActivity.this);
                startActivity(intent);
            } else if(position == 2) {
                IntentToCheck intentToCheck = new IntentToCheck(HomePageActivity.this);
                startActivity(intentToCheck);
            } else if(position == 3) {
                if( isDeliveryman ) {
                    Intent intent = new IntentToDeliverymanHome(HomePageActivity.this);
                    startActivity(intent);
                } else {
                    Intent intent = new IntentToUserHome(HomePageActivity.this);
                    startActivity(intent);
                }
            }
            mDrawerList.setItemChecked(position, false);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.login)
        {
            IntentToLogin intent = new IntentToLogin(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void search(){
        try {
            /* Go to the next page from dataProgress */
            SearchProgress searchProgress = new SearchProgress(this);
            searchProgress.execute(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        shakeFlag = 0;
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
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

    private void setLocation(double longitude,double latitude) {
        User.setLatitude(latitude);
        User.setLongitude(longitude);
    }
}
