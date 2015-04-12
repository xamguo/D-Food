package com.example.sam.d_food.integration.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.sam.d_food.integration.database.DatabaseConnector;


public class DataService extends IntentService {
    DatabaseConnector db;
    Cursor result;

    @Override
    public void onCreate() {
        Log.v("Service Started", "D-food");
        setupDatabase();//create database
        super.onCreate();
    }

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.sam.d_food.integration.service.action.FOO";
    private static final String ACTION_BAZ = "com.example.sam.d_food.integration.service.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.example.sam.d_food.integration.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.sam.d_food.integration.service.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DataService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DataService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public DataService() {
        super("DataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.v("Binded from service","xiao");

        Bundle extras = intent.getExtras();
        String mode = (String)extras.get("Mode");
        if(mode.equals("Search"))
            searchMode(extras);
        else if(mode.equals("Dish"))
            dishMode(extras);
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

        Intent intent = new Intent();
        intent.setAction("MY_ACTION");
        intent.putExtra("SearchResult", "Done");
        sendBroadcast(intent);
        //Search mySearch = new Search();
        //mySearch.start();

    }

    private void dishMode(Bundle extras) {
        String restaurant_id = (String)extras.get("restaurant_id");
        Intent intent = new Intent();
        intent.setAction("MY_ACTION");
        intent.putExtra("SearchResult", "Done");
        sendBroadcast(intent);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class Search extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                Thread.sleep(500);
                Intent intent = new Intent();
                intent.setAction("MY_ACTION");

                //db.open();
                //result = db.getAllHistory();
                //result = db.getAllRestaurant();
                //Data data = new Data(result);
                db.insertRestaurant("CMU", 0.52, 0.005, "Little Asia", 0);
                intent.putExtra("SearchResult", "Done");
                sendBroadcast(intent);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            stopSelf();
        }
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
