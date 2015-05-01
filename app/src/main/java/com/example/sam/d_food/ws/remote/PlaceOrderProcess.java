package com.example.sam.d_food.ws.remote;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 5/1/2015.
 */
public class PlaceOrderProcess extends AsyncTask<String, Void, String> {

    @Override
    /* params 0 is dishID, 1 is quantity */
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/place_order?");
        try {
            // Add your data
            Log.v("order placing", "...");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("user_id", params[2]));
            nameValuePairs.add(new BasicNameValuePair("dish_id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("dish_quantity", params[1]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            Log.v("place order", "success");
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return params[0];
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}
