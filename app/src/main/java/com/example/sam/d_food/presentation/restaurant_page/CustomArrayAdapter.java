package com.example.sam.d_food.presentation.restaurant_page;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.data.Restaurant;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 4/24/2015.
 */
public class CustomArrayAdapter extends ArrayAdapter {
    ArrayList<Restaurant> restaurants;
    Activity activity;
    public CustomArrayAdapter(Activity a, ArrayList list) {
        super(a, R.layout.support_restaurant_list, list);
        restaurants = list;
        activity = a;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        ImageView fieldImage;
        RatingBar ratingBar;
        TextView name;


        LayoutInflater inflater=activity.getLayoutInflater();
        row=inflater.inflate(R.layout.support_restaurant_list, parent, false);

        fieldImage = (ImageView) row.findViewById(R.id.img);
        ratingBar = (RatingBar)row.findViewById(R.id.ratingBar);
        name = (TextView)row.findViewById(R.id.title);

        new DownloadImageTask(fieldImage)
                .execute("https://pbs.twimg.com/profile_images/378800000765715763/f1d67a9deb0bb6c4bb8a4144083cd7c8_normal.jpeg");

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        name.setText(restaurants.get(position).getName());
        ratingBar.setRating(Float.parseFloat(restaurants.get(position).getRating()));

        return row;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
