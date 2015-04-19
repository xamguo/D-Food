package com.example.sam.d_food.integration.service;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Sam on 4/19/2015.
 */
public class DataProgress extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... strings) {

        String Mode = strings[0];
        String keyword = strings[1];
        String radius = strings[2];

        search();
        return null;
    }

    private String search() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        String url = "http://10.0.0.6:8080/D_Food_Server/search";
        HttpGet httpGet = new HttpGet(url);

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
        Log.v("Result", resp);

        JSONTokener tokener = new JSONTokener(resp);

        return resp;

    }
}