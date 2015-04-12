package com.example.sam.d_food.presentation.dish_page;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sam.d_food.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DishResultListActivity extends ListActivity {

    private List<Map<String, Object>> mData;
    String restaurant_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish__result);

        Bundle extras = getIntent().getExtras();
        restaurant_id = extras.getString("position");

        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this,DishInfoActivity.class);
        startActivity(intent);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Mixed Greens Salad");
        map.put("info", "$5.00");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Grilled Escarole Salad");
        map.put("info", "$7.50");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Smoked Bluefish Pt");
        map.put("info", "$7.00");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Daily Double");
        map.put("info", "$12.00");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Pierogies");
        map.put("info", "$14.00");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Hamburger");
        map.put("info", "$12.00");
        list.add(map);
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dish__result, menu);
        return true;
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
            public TextView title;
            public TextView info;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {

                holder=new ViewHolder();

                convertView = mInflater.inflate(R.layout.dish_list, null);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.info = (TextView)convertView.findViewById(R.id.info);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder)convertView.getTag();
            }


            holder.title.setText((String)mData.get(position).get("title"));
            holder.info.setText((String)mData.get(position).get("info"));

            return convertView;
        }
    }

}
