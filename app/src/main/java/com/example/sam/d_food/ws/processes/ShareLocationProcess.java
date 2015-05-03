/* This process is to upload the deliveryman location to server */
package com.example.sam.d_food.ws.processes;

import com.google.android.gms.maps.model.LatLng;

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

public class ShareLocationProcess {
    LatLng location;
    public ShareLocationProcess(LatLng loc) {
        location = loc;
    }

    public void shareLoc(String dManID, String bpSign) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/track?");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs;
            nameValuePairs = new ArrayList<>(3);
            nameValuePairs.add(new BasicNameValuePair("id", dManID));
            nameValuePairs.add(new BasicNameValuePair("latitude", Double.toString(location.latitude)));
            nameValuePairs.add(new BasicNameValuePair("longitude", Double.toString(location.longitude)));
            nameValuePairs.add(new BasicNameValuePair("beepSign", bpSign));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
    }
}
