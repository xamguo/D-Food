package com.example.sam.d_food.ws.remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sam.d_food.entities.data.Dish;
import com.example.sam.d_food.entities.data.RestaurantProxy;
import com.example.sam.d_food.presentation.main_page.HomePageActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sam on 4/19/2015.
 */
public class DataProgress extends AsyncTask<String, Void, Boolean> {
    String restaurantName;
    String longitude;
    String latitude;
    String distance;
    Activity activity;

    public DataProgress() {
        super();
    }

    public DataProgress(Activity c) {
        super();
        activity = c;
    }

    @Override
    protected Boolean doInBackground(String... strings) {

        String Mode = strings[0];
        String result;

        if(Mode.equals("restaurant")) {
            RestaurantProxy.clearProxy();       //clear the proxy
            result = searchRestaurant(Mode);
            longitude = strings[1];
            latitude = strings[2];
            distance = strings[3];
            Log.v("Search", result);
            saveRestaurants(result);
        } else if(Mode.equals("dish")) {
            restaurantName = strings[1];
            result = searchDishes(restaurantName);
            Log.v("Search", result);
            saveDish(result);
        } else {
            RestaurantProxy.clearProxy();
            //Mode.equals(" ");
            result = searchRestaurant(" ");
            Log.v("Search", result);
            saveRestaurants(result);
        }

        //activity.dialog.dismiss();
        return null;
    }

    private String searchRestaurant(String mode) {
        String url;
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet;
        if(mode.equals(" ")) {
            url = "http://guoxiao113.oicp.net/D_Food_Server/search";
        } else {
            url = "http://guoxiao113.oicp.net/D_Food_Server/search?";
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("mode", mode));
            params.add(new BasicNameValuePair("longitude", longitude));
            params.add(new BasicNameValuePair("latitude", latitude));
            params.add(new BasicNameValuePair("distance", distance));

            String paramString = URLEncodedUtils.format(params, "utf-8");
            url += paramString;
        }
        Log.v("URL",url);
        httpGet = new HttpGet(url);

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
        return resp;
    }

    private void saveRestaurants(String result) {
        try {
            JSONTokener tokener = new JSONTokener(result);
            JSONObject responseObject = (JSONObject) tokener.nextValue();
            JSONArray restaurantList = responseObject.getJSONArray("restaurantList");
            for (int i = 0; i < restaurantList.length(); i++) {
                JSONObject restaurant = restaurantList.getJSONObject(i);
                RestaurantProxy.createRestaurant(restaurant.getString("name"), restaurant.getString("rating"), restaurant.getString("description"),restaurant.getString("pic_url"));
                Log.v("name", restaurant.getString("name"));
                Log.v("rating", restaurant.getString("rating"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String searchDishes(String restaurantName) {
        String url;
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet;
        url = "http://guoxiao113.oicp.net/D_Food_Server/search?";
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("mode", "dish"));
        params.add(new BasicNameValuePair("restaurantName", restaurantName));

        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += paramString;

        Log.v("URL",url);
        httpGet = new HttpGet(url);

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
        return resp;
    }

    private void saveDish(String result) {
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        try {
            JSONTokener tokener = new JSONTokener(result);
            JSONObject responseObject = (JSONObject) tokener.nextValue();
            JSONArray dishList = responseObject.getJSONArray("dishList");
            for (int i = 0; i < dishList.length(); i++) {
                JSONObject dish = dishList.getJSONObject(i);
                String dishName = dish.getString("name");
                Double dishPrice = dish.getDouble("price");
                Integer dishId = dish.getInt("id");

                Log.v("dishName", dishName);
                Log.v("dishPrice", String.valueOf(dishPrice));
                Log.v("dishId", String.valueOf(dishId));

                Dish dishTemp = new Dish(dishId, dishPrice, dishName);
                dishes.add(dishTemp);
            }
            RestaurantProxy.setDishList(restaurantName,dishes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}