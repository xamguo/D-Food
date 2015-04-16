package com.example.sam.d_food.presentation.restaurant_page;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.integration.service.Data;

/**
 * Created by Sam on 4/16/2015.
 */
public class RestaurantListFragment extends ListFragment
{
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //v.setBackgroundColor(Color.GRAY);
        //Intent intent = new Intent(this,DishResultListActivity.class);
        //intent.putExtra("position",position);
        //startActivity(intent);


        Fragment fragment = new DishListFragment();
        RestaurantResultListActivity.dishListFragment = fragment;

        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().remove(manager.findFragmentById(R.id.fragmentContainer)).commit();

        manager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();
        RestaurantResultListActivity.pageNum = 2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] from = new String[] { "name", "location"};
        int[] to = new int[] { R.id.title,R.id.info};
        int flag = 0;

        CursorAdapter historyAdapter = new CustomCursorAdapter(
                inflater.getContext(), R.layout.support_restaurant_list, null, from, to, flag);

        //View v =  inflater.inflate(R.layout.support_restaurant_list, container, false);
        //WebView webView = (WebView)v.findViewById(R.id.webView);

        //if(webView != null)
        //webView.loadUrl("https://www.google.com/");

        historyAdapter.changeCursor(Data.getC());
        setListAdapter(historyAdapter);

        //ImageView img = (ImageView) v.findViewById(R.id.img);
        //img.setImageResource(R.drawable.food);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}