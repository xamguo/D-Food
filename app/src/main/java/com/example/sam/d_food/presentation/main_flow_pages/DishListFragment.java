/* dish list fragment to show the dishes */
package com.example.sam.d_food.presentation.main_flow_pages;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.data.BuildRestaurant;
import com.example.sam.d_food.entities.data.DishesEditor;
import com.example.sam.d_food.entities.data.RestaurantEditor;
import com.example.sam.d_food.presentation.main_flow_pages.adapters.CustomDishArrayAdapter;

public class DishListFragment extends ListFragment
{
    private int restaurantIndex;    //index to which restaurant is now seeing
    DishesEditor dishesEditor;
    RestaurantEditor restaurantEditor;

    public void setRestaurantIndex(int restaurantIndex) {
        this.restaurantIndex = restaurantIndex;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dishesEditor = new BuildRestaurant();
        restaurantEditor = new BuildRestaurant();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Set up the list fragment view */
        setListAdapter(new CustomDishArrayAdapter(getActivity(), dishesEditor.getDishList(restaurantIndex)));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        DishInfoFragment fragment = new DishInfoFragment();
        fragment.setDish(dishesEditor.getDishList(restaurantIndex).get(position));
        fragment.setRestaurantName(restaurantEditor.getRestaurants().get(restaurantIndex).getName());
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().remove(manager.findFragmentById(R.id.fragmentContainer)).commit();

        manager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();
        FragmentContainerActivity.pageNum = 3;
    }
}
