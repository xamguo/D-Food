package com.example.sam.d_food.presentation.restaurant_page;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sam.d_food.R;
import com.example.sam.d_food.presentation.intents.IntentToCheck;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Sam on 4/16/2015.
 */
public class DishInfoFragment extends Fragment{
    Button checkButton;
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_dish_info, container, false);
        spinner = (Spinner)v.findViewById(R.id.dishSpinner);

        checkButton = (Button)v.findViewById(R.id.addToCart);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = spinner.getSelectedItemPosition();
                checkout();
            }
        });
        return v;
    }
    public void checkout() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Check out?")
                .setMessage("Cart added, check out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        goCheck();
                        postOrder();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // do nothing
                        getActivity().onBackPressed();
                    }
                })
                .show();

    }

    private void postOrder() {
        InputStream inputStream = null;
        String result = "";
        try {

            String url = "http://guoxiao113.oicp.net/D_Food_Server/place_order?";

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("order_id", "1");
            jsonObject.accumulate("user_id", "1");
            jsonObject.accumulate("restaurant_id", "1");
            jsonObject.accumulate("dish_id", "1");
            jsonObject.accumulate("dish_quantity", "1");
            jsonObject.accumulate("dMan_id", "1");

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
    }

    private void goCheck(){
        IntentToCheck intentToCheck = new IntentToCheck(getActivity());
        startActivity(intentToCheck);
    }
}
