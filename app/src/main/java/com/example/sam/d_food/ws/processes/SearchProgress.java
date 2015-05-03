/*
* SearchProgress, a process thread to search the restaurants or dishes
* Mode - " "
*       search all restaurants
* Mode - "restaurant"
*       search based on location
*       latitude, longitude, length
* Mode - "dish"
*       search dishes
*       restaurant name
*
* This process will start restaurant list automatically if is first two kind of mode
* */
package com.example.sam.d_food.ws.processes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sam.d_food.exceptionHandler.SearchException;
import com.example.sam.d_food.entities.data.BuildRestaurant;
import com.example.sam.d_food.entities.data.Dish;
import com.example.sam.d_food.entities.data.DishesEditor;
import com.example.sam.d_food.entities.data.RestaurantEditor;
import com.example.sam.d_food.presentation.main_flow_pages.FragmentContainerActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
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
import java.util.LinkedList;
import java.util.List;

public class SearchProgress extends AsyncTask<String, Void, Boolean>  {

    String restaurantName;
    String longitude;
    String latitude;
    String distance;
    Activity activity;

    private String mode;
    private ProgressDialog dialog;
    private boolean searchError = false;
    RestaurantEditor restaurantEditor;
    DishesEditor dishesEditor;

    public SearchProgress(Activity c) {
        super();
        activity = c;
        dialog = new ProgressDialog(activity);
        restaurantEditor = new BuildRestaurant();
        dishesEditor = new BuildRestaurant();
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Progress start");
        this.dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (!searchError) {
            if (mode.equals("restaurant")) {
            /* Go to the next page from here */
                Intent intent = new Intent(activity, FragmentContainerActivity.class);
                activity.startActivity(intent);
                super.onPostExecute(aBoolean);
            } else if (mode.equals("dish")) {
                super.onPostExecute(aBoolean);
            }
        }
    }

    @Override
    protected Boolean doInBackground(String... strings)  {

        String Mode = strings[0];
        String result;

        Log.v("myMode",Mode);
        try {
            switch (Mode) {
                case "restaurant":
                    mode = "restaurant";
                    restaurantEditor.clearProxy();       //clear the proxy
                    longitude = strings[1];
                    //longitude =  String.valueOf(-79.9436244);
                    latitude = strings[2];
                    //latitude =  String.valueOf(40.4409227);
                    distance = strings[3];
                    //distance =  String.valueOf(10);
                    result = searchRestaurant(Mode);
                    Log.v("Search", result);
                    saveRestaurants(result);
                    break;
                case "dish":
                    mode = "dish";
                    restaurantName = strings[1];
                    result = searchDishes(restaurantName);
                    Log.v("Search", result);
                    saveDish(result);
                    break;
                default:
                    mode = "restaurant";
                    restaurantEditor.clearProxy();
                    result = searchRestaurant(" ");
                    Log.v("Search", result);
                    saveRestaurants(result);
                    break;
            }
        } catch( SearchException e ) {
            searchError = true;
        }
        return null;
    }

    /* search restaurants */
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
            //Log.v("mylongitude",longitude);
            String paramString = URLEncodedUtils.format(params, "utf-8");
            url += paramString;
            Log.v("myURL",url);
        }
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

    /* save restaurants into proxy */
    private void saveRestaurants(String result) throws SearchException  {
        try {
            JSONTokener tokener = new JSONTokener(result);
            JSONObject responseObject = (JSONObject) tokener.nextValue();
            JSONArray restaurantList = responseObject.getJSONArray("restaurantList");
            for (int i = 0; i < restaurantList.length(); i++) {
                JSONObject restaurant = restaurantList.getJSONObject(i);
                restaurantEditor.createRestaurant(restaurant.getString("name"), restaurant.getString("rating"), restaurant.getString("description"),restaurant.getString("pic_url"));
                Log.v("name", restaurant.getString("name"));
                Log.v("rating", restaurant.getString("rating"));
            }
        } catch (Exception e) {
            throw new SearchException("Search Failed", activity);
        }
    }

    /* search dishes */
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
        return builder.toString();
    }

    /* save dishes into proxy */
    private void saveDish(String result)  throws SearchException {
        ArrayList<Dish> dishes = new ArrayList<>();
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
            dishesEditor.setDishList(restaurantName,dishes);
        } catch (Exception e) {
            throw new SearchException("Search Failed", activity);
        }
    }
}