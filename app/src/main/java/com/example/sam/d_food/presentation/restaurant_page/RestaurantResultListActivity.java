package com.example.sam.d_food.presentation.restaurant_page;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.example.sam.d_food.R;
import com.example.sam.d_food.business.data.Dish;
import com.example.sam.d_food.business.data.DishReceiver;
import com.example.sam.d_food.business.user.Search;
import com.example.sam.d_food.business.user.SearchReceiver;
import com.example.sam.d_food.integration.service.Data;
import com.example.sam.d_food.integration.service.DataService;
import com.example.sam.d_food.presentation.dish_page.DishResultListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RestaurantResultListActivity extends ListActivity {

    private List<Map<String, Object>> mData;
    View justview;
    DishReceiver dishReceiver;
    Dish dish;
    static boolean receiverSign = false;
    public static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resaurant__result);

//        mData = getData();
//        MyAdapter adapter = new MyAdapter(this);
//        setListAdapter(adapter);
        setMyAdapter();

    }

    @Override
    protected void onPause() {
        if(dish != null) {
            //unReceiver();
            dish.unbindService();
            Log.v("unbindService","here");
        }
        super.onPause();
    }

    private void setMyAdapter() {
        String[] from = new String[] { "name", "location"};
        int[] to = new int[] { R.id.title,R.id.info};
        int flag = 0;
        CursorAdapter historyAdapter = new SimpleCursorAdapter(
                this, R.layout.restaurant_list, null, from, to, flag);
        historyAdapter.changeCursor(Data.getC());
        setListAdapter(historyAdapter);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Phipps Conservatory and Botanical ");
        map.put("info", "Chinese Restaurant $$");
        map.put("img", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Gaucho Parrilla Argentina");
        map.put("info", "Bakeries, French $");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Espresso A Mano");
        map.put("info", "Coffee & Tea, Juice Bars & Smoothies $");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "La Gourmandine Bakery & Pastry Shop");
        map.put("info", "Italian $$$$");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Simpatico Espresso");
        map.put("info", "French $$$");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map.put("title", "Phipps Conservatory and Botanical ");
        map.put("info", "Chinese Restaurant $$");
        map.put("img", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Gaucho Parrilla Argentina");
        map.put("info", "Bakeries, French $");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Espresso A Mano");
        map.put("info", "Coffee & Tea, Juice Bars & Smoothies $");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "La Gourmandine Bakery & Pastry Shop");
        map.put("info", "Italian $$$$");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Simpatico Espresso");
        map.put("info", "French $$$");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map.put("title", "Phipps Conservatory and Botanical ");
        map.put("info", "Chinese Restaurant $$");
        map.put("img", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Gaucho Parrilla Argentina");
        map.put("info", "Bakeries, French $");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Espresso A Mano");
        map.put("info", "Coffee & Tea, Juice Bars & Smoothies $");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "La Gourmandine Bakery & Pastry Shop");
        map.put("info", "Italian $$$$");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Simpatico Espresso");
        map.put("info", "French $$$");
        map.put("img",  R.mipmap.ic_launcher);
        list.add(map);
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resaurant__result, menu);
        return true;
    }

    @Override
    protected void onResume() {
       if(justview != null)
            justview.setBackgroundColor(Color.WHITE);
        super.onResume();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        justview = v;
        v.setBackgroundColor(Color.GRAY);

        if(receiverSign == true){
            unReceiver();
        }

        receiver();
        dialog = new ProgressDialog(this);
        dialog.show();

        dish = new Dish(RestaurantResultListActivity.this);
        dish.getDish(0);
/*
        Intent intent = new Intent(this,DishResultListActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
*/
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        public final class ViewHolder{
            public ImageView img;
            public TextView title;
            public TextView info;
            public Button viewBtn;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                holder=new ViewHolder();

                convertView = mInflater.inflate(R.layout.restaurant_list, null);
                holder.img = (ImageView)convertView.findViewById(R.id.img);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.info = (TextView)convertView.findViewById(R.id.info);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder)convertView.getTag();
            }


            holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
            holder.title.setText((String)mData.get(position).get("title"));
            holder.info.setText((String)mData.get(position).get("info"));

            return convertView;
        }
    }

    private void receiver() {
        dishReceiver = new DishReceiver(this, DishResultListActivity.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MY_ACTION");
        registerReceiver(dishReceiver, intentFilter);
        receiverSign = true;
    }

    private void unReceiver() {
        receiverSign = false;
        unregisterReceiver(dishReceiver);
    }
}
