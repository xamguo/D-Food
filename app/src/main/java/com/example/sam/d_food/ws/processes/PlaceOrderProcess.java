package com.example.sam.d_food.ws.processes;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sam.d_food.entities.user.User;
import com.example.sam.d_food.presentation.intents.IntentToUserMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 5/1/2015.
 */
public class PlaceOrderProcess extends AsyncTask<String, Void, String> {

    int deliverymanID;
    Activity activity;

    public PlaceOrderProcess(Activity activity) {
        this.activity = activity;
    }

    @Override
    /* params 0 is dishID, 1 is quantity */
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/place_order?");
        try {
            // Add your data
            Log.v("order placing", "...");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("user_id", params[2]));
            nameValuePairs.add(new BasicNameValuePair("dish_id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("dish_quantity", params[1]));
            nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(User.getLatitude())));
            nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(User.getLongitude())));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            Log.v("place order", "success");

            StringBuilder builder = new StringBuilder();
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String resp = builder.toString();
            //Log.v("resp",resp);
            JSONTokener tokener = new JSONTokener(resp);
            JSONObject responseObject = (JSONObject) tokener.nextValue();
            deliverymanID = responseObject.getInt("deliverymanID");

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params[0];
    }

    @Override
    protected void onPostExecute(String result) {
        activity.finish();
        IntentToUserMap intent = new IntentToUserMap(activity);
        intent.putExtra("deliverymanID",deliverymanID);
        Log.v("PlaceOrderProcess xiao", String.valueOf(deliverymanID));
        activity.startActivity(intent);
    }

}
