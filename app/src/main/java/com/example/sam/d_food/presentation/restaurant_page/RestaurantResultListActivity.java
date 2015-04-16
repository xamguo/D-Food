package com.example.sam.d_food.presentation.restaurant_page;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.webkit.WebView;
import android.widget.*;

import com.example.sam.d_food.R;
import com.example.sam.d_food.integration.service.Data;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class RestaurantResultListActivity extends Activity {

    View justview;
    static Fragment restaurantListFragment;
    static Fragment dishListFragment;
    static int pageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resaurant__result);

//        mData = getData();
//        MyAdapter adapter = new MyAdapter(this);
//        setListAdapter(adapter);
        restaurantListFragment = new RestaurantListFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragmentContainer, restaurantListFragment)
                .commit();
        RestaurantResultListActivity.pageNum = 1;
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
        }
        else if(pageNum == 2) {
            FragmentManager manager = getFragmentManager();

            manager.beginTransaction().remove(manager.findFragmentById(R.id.fragmentContainer)).commit();

            manager.beginTransaction()
                    .add(R.id.fragmentContainer, restaurantListFragment)
                    .commit();
            pageNum = 1;
        }
    }
}
