/*
* RegisterProcess, a process thread to register and get user id
* mode - user or deliveryman
* user_name
* user_password
* this process will close the register page automatically
* */
package com.example.sam.d_food.ws.processes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.sam.d_food.ExceptionHandler.RegisterException;
import com.example.sam.d_food.entities.user.User;
import com.example.sam.d_food.presentation.deliveryman_page.DeliveryHomePageActivity;
import com.example.sam.d_food.presentation.user_page.UserHomePageActivity;

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

public class RegisterProcess extends AsyncTask<String, Void, Integer> {
    Integer userID;
    String userName;
    String mode;
    Activity activity;
    private ProgressDialog dialog;

    public RegisterProcess(Activity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Progress start");
        this.dialog.show();
    }

    @Override
    /* params 0 is dishID, 1 is quantity */
    protected Integer doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/register?");
        try {
            // Add your data
            //Log.v("params[0]", params[0]);
            //Log.v("params[1]", params[1]);
            //Log.v("params[2]", params[2]);
            mode = params[0];
            userName = params[1];
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("mode", params[0]));
            nameValuePairs.add(new BasicNameValuePair("user_name", params[1]));
            nameValuePairs.add(new BasicNameValuePair("user_password", params[2]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            //Log.v("httppost",httppost.toString());

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
            userID = responseObject.getInt("userID");
            //Log.v("userID",String.valueOf(userID));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userID;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        try {
            User.setId(userID);
            if (userID > 0) {
                User.setName(userName);
                if (mode.equals("user")) {
                    Intent intent = new Intent(activity, UserHomePageActivity.class);
                    activity.startActivity(intent);
                } else if (mode.equals("deliveryman")) {
                    Intent intent = new Intent(activity, DeliveryHomePageActivity.class);
                    activity.startActivity(intent);
                }
                activity.finish();
            } else if (userID == -2) {
                throw new RegisterException("The user name is already taken", activity);
            } else {
                throw new RegisterException("Regiter Failed", activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
