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

    String[] numbers_text = new String[] { "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine", "ten", "eleven",
            "twelve", "thirteen", "fourteen", "fifteen" };
    String[] numbers_digits = new String[] { "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15" };

    public void setRestaurantIndex(int restaurantIndex) {
        this.restaurantIndex = restaurantIndex;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        DishInfoFragment fragment = new DishInfoFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().remove(manager.findFragmentById(R.id.fragmentContainer)).commit();

        manager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();
        RestaurantResultListActivity.pageNum = 3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                numbers_text);
        setListAdapter(adapter);
        */
        setListAdapter(new CustomDishArrayAdapter(getActivity(), RestaurantProxy.getDishList(restaurantIndex)));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
