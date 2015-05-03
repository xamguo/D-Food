/*
* LoginProcess, a process thread to login and get user id
* mode - user or deliveryman
* user_name
* user_password
* this process will close the login page automatically
* */
package com.example.sam.d_food.ws.processes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sam.d_food.ExceptionHandler.LoginException;
import com.example.sam.d_food.entities.user.User;
import com.example.sam.d_food.presentation.deliveryman_page.DeliveryHomePageActivity;
import com.example.sam.d_food.presentation.main_page.HomePageActivity;
import com.example.sam.d_food.presentation.user_page.UserHomePageActivity;
import com.example.sam.d_food.ws.processes.services.UserTypeService;

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

public class LoginProcess extends AsyncTask<String, Void, Integer> {
    Integer userID;
    String userName;
    String mode;
    Activity activity;
    private ProgressDialog dialog;

    public LoginProcess(Activity activity) {
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
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/login?");
        try {
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
            JSONTokener tokener = new JSONTokener(resp);
            JSONObject responseObject = (JSONObject) tokener.nextValue();
            userID = responseObject.getInt("userID");

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

        User.setId(result);
        try {
            if (userID != -1) {
                User.setName(userName);
                if (mode.equals("user")) {
                    HomePageActivity.isDeliveryman = false;
                    UserTypeService.setUserType("Signed user");
                    Intent intent = new Intent(activity, UserHomePageActivity.class);
                    activity.startActivity(intent);
                } else if (mode.equals("deliveryman")) {
                    UserTypeService.setUserType("deliveryman");
                    HomePageActivity.isDeliveryman = true;
                    Intent intent = new Intent(activity, DeliveryHomePageActivity.class);
                    activity.startActivity(intent);
                }
                activity.finish();
            } else {
                throw new LoginException("failed login", activity);
            }
        } catch (Exception e) {
            User.setId(-1);
        }
    }

}
