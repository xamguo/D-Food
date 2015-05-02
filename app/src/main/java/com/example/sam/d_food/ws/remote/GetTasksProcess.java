package com.example.sam.d_food.ws.remote;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sam.d_food.entities.deliveryman.TaskProxy;
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
import org.json.JSONArray;
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
public class GetTasksProcess extends AsyncTask<String, Void, Void> {

    int deliverymanID;
    Activity activity;

    public GetTasksProcess(int deliverymanID, Activity activity) {
        this.deliverymanID = deliverymanID;
        this.activity = activity;
    }

    @Override
    /* params 0 is dishID, 1 is quantity */
    protected Void doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/task?");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("deliverymanID", String.valueOf(deliverymanID)));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

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
            JSONArray taskList = responseObject.getJSONArray("taskList");
            for (int i = 0; i < taskList.length(); i++) {
                JSONObject task = taskList.getJSONObject(i);

                TaskProxy taskProxy = new TaskProxy();
                taskProxy.addTask(task.getString("UserName"), task.getDouble("latitude"), task.getDouble("longitude"));
                Log.v("name", task.getString("UserName"));
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
