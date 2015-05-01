package com.example.sam.d_food.presentation.restaurant_page;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.data.RestaurantProxy;

/**
 * Created by Sam on 4/16/2015.
 */
public class DishListFragment extends ListFragment
{
    private int restaurantIndex;

    public void setRestaurantIndex(int restaurantIndex) {
        this.restaurantIndex = restaurantIndex;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Set up the list fragment view */
        setListAdapter(new CustomDishArrayAdapter(getActivity(), RestaurantProxy.getDishList(restaurantIndex)));
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        DishInfoFragment fragment = new DishInfoFragment();
        fragment.setDish(RestaurantProxy.getDishList(restaurantIndex).get(position));
        fragment.setRestaurantName(RestaurantProxy.getRestaurants().get(restaurantIndex).getName());
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().remove(manager.findFragmentById(R.id.fragmentContainer)).commit();

        manager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();
        RestaurantResultListActivity.pageNum = 3;
    }
}
