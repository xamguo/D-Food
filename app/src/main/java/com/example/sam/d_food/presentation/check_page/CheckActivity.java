package com.example.sam.d_food.presentation.check_page;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.user.Cart;
import com.example.sam.d_food.presentation.intents.IntentToUserMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckActivity extends Activity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_check);

        /* Set the check-out list */
        listView = (ListView)findViewById(R.id.checklistView);
        SimpleAdapter adapter = new SimpleAdapter(this,getCheckData(),R.layout.support_check,
                new String[]{"check_name","check_restaurant","check_quantity","check_price"},
                new int[]{R.id.check_name,R.id.check_restaurant,R.id.check_quantity,R.id.check_price});
        listView.setAdapter(adapter);
    }

    /* Get the order info */
    private List<Map<String, String>> getCheckData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if(Cart.getOrderNum() != 0) {
            Map<String, String> map_total = new HashMap<String, String>();
            map_total.put("check_name", "Total");
            map_total.put("check_restaurant", Cart.getOrderRestaurantName(0));
            map_total.put("check_quantity", " ");
            map_total.put("check_price", "$" + Cart.getTotalPrice());
            list.add(map_total);
        }
        for(int i = 0; i < Cart.getOrderNum(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("check_name", Cart.getOrderName(i));
            map.put("check_restaurant", Cart.getOrderRestaurantName(i));
            map.put("check_quantity", String.valueOf(Cart.getOrderQuantity(i)));
            map.put("check_price", "$" + Cart.getOrderPrice(i));
            list.add(map);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void usermap(View view) {
        PlaceOrder order = new PlaceOrder();
        order.execute("http://guoxiao113.oicp.net/D_Food_Server/place_order?");
        finish();
        IntentToUserMap intent = new IntentToUserMap(this);
        startActivity(intent);
    }

    private class PlaceOrder extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/place_order?");
            try {
                // Add your data
                Log.v("order placing", "...");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("user_id", "1"));
                nameValuePairs.add(new BasicNameValuePair("dish_id", "1"));
                nameValuePairs.add(new BasicNameValuePair("dish_quantity", "1"));
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

    private void postOrder() {
        InputStream inputStream = null;
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://guoxiao113.oicp.net/D_Food_Server/place_order?");
        try {
            // Add your data
            Log.v("order placing", "...");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("user_id", "1"));
            nameValuePairs.add(new BasicNameValuePair("dish_id", "1"));
            nameValuePairs.add(new BasicNameValuePair("dish_quantity", "1"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            Log.v("place order", "success");
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
//        try {
//
//            String url = "http://guoxiao113.oicp.net/D_Food_Server/place_order?";
//
//            // 1. create HttpClient
//            HttpClient httpclient = new DefaultHttpClient();
//
//            // 2. make POST request to the given URL
//            HttpPost httpPost = new HttpPost(url);
//
//            String json = "";
//
//            // 3. build jsonObject
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("user_id", "1");
//            jsonObject.accumulate("dish_id", "1");
//            jsonObject.accumulate("dish_quantity", "1");
//
//            // 4. convert JSONObject to JSON to String
//            json = jsonObject.toString();
//
//            // 5. set json to StringEntity
//            StringEntity se = new StringEntity(json);
//
//            // 6. set httpPost Entity
//            httpPost.setEntity(se);
//
//            // 7. Set some headers to inform server about the type of the content
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
//
//            // 8. Execute POST request to the given URL
//            HttpResponse httpResponse = httpclient.execute(httpPost);
//
//            String resJson = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
//            Log.v("result", resJson);
//        } catch (Exception e) {
//            Log.d("InputStream", "faled to place order");
//        }
    }
}
