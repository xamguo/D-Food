package com.example.sam.d_food.integration.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.sam.d_food.integration.database.DatabaseConnector;
import com.example.sam.d_food.presentation.main_page.HomePageActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DataService extends IntentService {
    DatabaseConnector db;
    Cursor result;

    @Override
    public void onCreate() {
        Log.v("Service Started", "D-food");
        setupDatabase();//create database
        super.onCreate();
    }

    public DataService() {
        super("DataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Binded from service","xiao");
        Bundle extras = intent.getExtras();
        String mode = (String)extras.get("Mode");
        if(mode.equals("Search"))
            searchMode(extras);
        return super.onBind(intent);
    }

    private void searchMode(Bundle extras) {

        String longitude = (String)extras.get("longitude");
        String latitude = (String)extras.get("latitude");
        String price = (String)extras.get("price");
        String location = (String)extras.get("location");

        Log.v("Service get longitude",longitude);
        Log.v("Service get latitude",latitude);
        Log.v("Service get price",price);
        Log.v("Service get location",location);

        db.open();
        //Cursor c = db.getAllRestaurant();
        Cursor c = db.getRestaurantByLocation(location);
        Data data = new Data(c);
        new ProgressTask().execute("a");
        Intent intent = new Intent();
        intent.setAction("MY_ACTION");
        intent.putExtra("SearchResult", "Done");
        sendBroadcast(intent);
        //Search mySearch = new Search();
        //mySearch.start();
    }

    private String search() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        String url = "http://10.0.0.6:8080/D_Food_Server/search";
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
        }
        String resp = builder.toString();
        Log.v("Result", resp);

        JSONTokener tokener = new JSONTokener(resp);

        return resp;

    }

    public void setupDatabase() {
        db = new DatabaseConnector(this);
        for(int i = 0; i < 15; i++) {
            db.insertRestaurant("CMU", 0.52, 0.005, "Little Asia", 0);
            db.insertRestaurant("UCSD", 0.52, 0.005, "Test", 0);
            db.insertRestaurant("Columbia", 0.52, 0.005, "Test Restaurant", 1);
            db.insertRestaurant("USC", 0.52, 0.005, "Yours", 2);
            db.insertRestaurant("PURDUE", 0.52, 0.005, "Mine Dine", 0);
            db.insertRestaurant("OSU", 0.52, 0.005, "No Good Caffee", 0);
            db.insertRestaurant("WISCONSIN", 0.52, 0.005, "try food", 0);
        }
    }

    public Cursor getRestaurantByLocation(String location) {
        return db.getRestaurantByLocation(location);
    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            search();
            return null;
        }
    }
}
