package com.example.sam.d_food.presentation.restaurant_page;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.data.Restaurant;
import com.example.sam.d_food.entities.data.RestaurantProxy;
import com.example.sam.d_food.ws.remote.DataProgress;

/**
 * Created by Sam on 4/16/2015.
 */
public class RestaurantListFragment extends ListFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /* Set up the list fragment view */
        setListAdapter(new CustomRestaurantArrayAdapter(getActivity(), RestaurantProxy.getRestaurants()));
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //setListAdapter(new CustomRestaurantArrayAdapter(getActivity(), RestaurantProxy.getRestaurants()));

//        String[] from = new String[] { "name", "location"};
//        int[] to = new int[] { R.id.title,R.id.info};
//        int flag = 0;
//
//        CursorAdapter historyAdapter = new CustomCursorAdapter(
//                inflater.getContext(), R.layout.support_restaurant_list, null, from, to, flag);

        //View v =  inflater.inflate(R.layout.support_restaurant_list, container, false);
        //WebView webView = (WebView)v.findViewById(R.id.webView);

        //if(webView != null)
        //webView.loadUrl("https://www.google.com/");

//        historyAdapter.changeCursor(Data.getC());
//        setListAdapter(historyAdapter);

        //ImageView img = (ImageView) v.findViewById(R.id.img);
        //img.setImageResource(R.drawable.food);
        return super.onCreateView(inflater, container, savedInstanceState);
        //return null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //v.setBackgroundColor(Color.GRAY);
        //Intent intent = new Intent(this,DishResultListActivity.class);
        //intent.putExtra("position",position);
        //startActivity(intent);
        Restaurant restaurantSelected = RestaurantProxy.getRestaurants().get(position);
        String restaurantName = restaurantSelected.getName();

        if(!restaurantSelected.isDishesSet()) {
            try {
                Log.v("restaurantName", restaurantName);
                DataProgress dataProgress = new DataProgress(getActivity());
                dataProgress.execute("dish", restaurantName).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* DishListFragment setting */
        DishListFragment fragment = new DishListFragment();
        fragment.setRestaurantIndex(position);  // pass the index to DishListFragment

        /* Fragment transaction */
        RestaurantResultListActivity.dishListFragment = fragment;  //tell the container
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().remove(manager.findFragmentById(R.id.fragmentContainer)).commit();
        manager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();
        RestaurantResultListActivity.pageNum = 2;

    }
}