/* restaurant list fragment to show the restaurants */
package com.example.sam.d_food.presentation.main_flow_pages;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.data.BuildRestaurant;
import com.example.sam.d_food.entities.data.Restaurant;
import com.example.sam.d_food.entities.data.RestaurantEditor;
import com.example.sam.d_food.presentation.main_flow_pages.adapters.CustomRestaurantArrayAdapter;
import com.example.sam.d_food.ws.processes.SearchProgress;

public class RestaurantListFragment extends ListFragment
{
    RestaurantEditor restaurantEditor;  //restaurant editor

    @Override
    public void onCreate(Bundle savedInstanceState) {

        /* Set up the list fragment view */
        restaurantEditor = new BuildRestaurant();

        /* Set the self-defined list adapter */
        setListAdapter(new CustomRestaurantArrayAdapter(getActivity(), restaurantEditor.getRestaurants()));
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        /* get the restaurant that is selected */
        Restaurant restaurantSelected = restaurantEditor.getRestaurants().get(position);
        String restaurantName = restaurantSelected.getName();

        /* check the dishes in the restaurant */
        if(!restaurantSelected.isDishesSet()) {
            /* download the dishes if the dishes is not stored */
            try {
                Log.v("restaurantName", restaurantName);
                SearchProgress searchProgress = new SearchProgress(getActivity());
                searchProgress.execute("dish", restaurantName).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* DishListFragment setting */
        DishListFragment fragment = new DishListFragment();
        fragment.setRestaurantIndex(position);  // pass the index to DishListFragment

        /* Fragment transaction to next fragment page - dish list fragment */
        FragmentContainerActivity.dishListFragment = fragment;  //tell the container
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().remove(manager.findFragmentById(R.id.fragmentContainer)).commit();
        manager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();
        FragmentContainerActivity.pageNum = 2;  //set the pageNum value to be aware of the page change
    }
}