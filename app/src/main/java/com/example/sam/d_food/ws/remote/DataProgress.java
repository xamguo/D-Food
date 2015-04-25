package com.example.sam.d_food.ws.remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
    String longitude;
    String latitude;
    String distance;
    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    Activity activity;


    public DataProgress(Activity c) {
        super();
        activity = c;
    }

    @Override
    protected Boolean doInBackground(String... strings) {

        RestaurantProxy.clearProxy();
        String Mode = strings[0];
        String result;

        if(!Mode.equals(" ")) {
            result = search(Mode);
            longitude = strings[1];
            latitude = strings[2];
            distance = strings[3];
        } else {
            result = search(" ");
        }
        Log.v("Search", result);
        try {
            JSONTokener tokener = new JSONTokener(result);
            JSONObject responseObject = (JSONObject) tokener.nextValue();
            JSONArray restaurantList = responseObject.getJSONArray("restaurantList");
            for (int i = 0; i < restaurantList.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject restaurant = restaurantList.getJSONObject(i);
                map.put("name", restaurant.getString("name"));
                map.put("rating", restaurant.getString("rating"));
                RestaurantProxy.createRestaurant(restaurant.getString("name"), restaurant.getString("rating"));
                Log.v("name", restaurant.getString("name"));
                Log.v("rating", restaurant.getString("rating"));
                jsonlist.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //activity.dialog.dismiss();
        return null;
    }

    private String search(String mode) {
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

    public ArrayList<HashMap<String, String>> getJsonlist() {
        return jsonlist;
    }
}