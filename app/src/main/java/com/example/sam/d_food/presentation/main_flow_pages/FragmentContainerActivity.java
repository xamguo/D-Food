package com.example.sam.d_food.presentation.main_flow_pages;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
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

    View justview;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    static Fragment restaurantListFragment;
    static Fragment dishListFragment;
    static int pageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_resaurant__result);

//        mData = getData();
//        MyAdapter adapter = new MyAdapter(this);
//        setListAdapter(adapter);
        restaurantListFragment = new RestaurantListFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragmentContainer, restaurantListFragment)
                .commit();
        FragmentContainerActivity.pageNum = 1;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_restaurant);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_restaurant);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resaurant__result, menu);
        return true;
    }

    @Override
    protected void onResume() {
        if (justview != null)
            justview.setBackgroundColor(Color.WHITE);
        super.onResume();
    }

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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == 1){
                IntentToLogin intent = new IntentToLogin(FragmentContainerActivity.this);
                startActivity(intent);
                mDrawerList.setItemChecked(position, false);
                mDrawerLayout.closeDrawer(mDrawerList);
            } else if(position == 0) {
                finish();
            } else if(position == 2) {
                IntentToCheck intentToCheck = new IntentToCheck(FragmentContainerActivity.this);
                startActivity(intentToCheck);
                finish();
            } else if(position == 3) {
                Intent intenet = new Intent(FragmentContainerActivity.this, UserHomePageActivity.class);
                startActivity(intenet);
                finish();
            }
        }
    }
}
