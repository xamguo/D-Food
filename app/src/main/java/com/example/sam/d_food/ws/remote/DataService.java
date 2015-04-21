package com.example.sam.d_food.ws.remote;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


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
        //new DataProgress().execute("a");      //to start a data transfer with server
        Intent intent = new Intent();
        intent.setAction("MY_ACTION");
        intent.putExtra("SearchResult", "Done");
        sendBroadcast(intent);
        //Search mySearch = new Search();
        //mySearch.start();
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
}
