package com.example.sam.d_food.presentation.restaurant_page;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.example.sam.d_food.R;

import java.io.InputStream;

/**
 * Created by Sam on 4/16/2015.
 */
public class CustomCursorAdapter extends SimpleCursorAdapter {
    public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView fieldImage = (ImageView) view.findViewById(R.id.img);
        //fieldImage.setImageResource(R.drawable.food);

        new DownloadImageTask(fieldImage)
                .execute("https://pbs.twimg.com/profile_images/378800000765715763/f1d67a9deb0bb6c4bb8a4144083cd7c8_normal.jpeg");



        //WebView webView = (WebView)view.findViewById(R.id.webView);
        //webView.loadUrl("http://chinastarharlingen.com/images/icon_menu.png");

        //if(webView != null)
        super.bindView(view, context, cursor);
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