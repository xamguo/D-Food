/*
* This activity is a fragment container, for the restaurant list, dish list
* and dish info pages. The fragment is put in the container, and this class
* is the controller of them.
* */
package com.example.sam.d_food.presentation.main_flow_pages;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.presentation.intents.IntentToCheck;
import com.example.sam.d_food.presentation.intents.IntentToLogin;
import com.example.sam.d_food.presentation.user_page.UserHomePageActivity;


public class FragmentContainerActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;       //drawer
    static Fragment restaurantListFragment;//fragment for restauarnt list
    static Fragment dishListFragment;   //fragment for dish list
    static int pageNum;                 //indicate with page is now showing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_resaurant__result);

        /* Fragment container set up */
        restaurantListFragment = new RestaurantListFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragmentContainer, restaurantListFragment)
                .commit();
        FragmentContainerActivity.pageNum = 1;

        /* drawer settings */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_restaurant);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_restaurant);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.layout_list_item, getResources().getStringArray(R.array.drawer_items)));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resaurant__result, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /* The reaction of back key press, to quit fragment instead of finishing the activity */
    @Override
    public void onBackPressed() {
        if(pageNum == 1){
            super.onBackPressed();
        } else if(pageNum == 2) {
            FragmentManager manager = getFragmentManager();

            manager.beginTransaction().remove(manager.findFragmentById(R.id.fragmentContainer)).commit();

            manager.beginTransaction()
                    .add(R.id.fragmentContainer, restaurantListFragment)
                    .commit();
            pageNum = 1;
        } else if(pageNum == 3) {
            FragmentManager manager = getFragmentManager();

            manager.beginTransaction().remove(manager.findFragmentById(R.id.fragmentContainer)).commit();

            manager.beginTransaction()
                    .add(R.id.fragmentContainer, dishListFragment)
                    .commit();
            pageNum = 2;
        }
    }

    /* drawer listener */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == 1){
                /* go to login page */
                IntentToLogin intent = new IntentToLogin(FragmentContainerActivity.this);
                startActivity(intent);
                mDrawerList.setItemChecked(position, false);
                mDrawerLayout.closeDrawer(mDrawerList);
            } else if(position == 0) {
                /* go to main page */
                finish();
            } else if(position == 2) {
                /* go to check page */
                IntentToCheck intentToCheck = new IntentToCheck(FragmentContainerActivity.this);
                startActivity(intentToCheck);
                finish();
            } else if(position == 3) {
                /* go to Home page */
                Intent intent = new Intent(FragmentContainerActivity.this, UserHomePageActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
