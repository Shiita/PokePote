package com.example.martin.pokepote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Martin on 20/03/2015.
 */
public class DownloadImageTask extends AsyncTask<String,Bitmap,Bitmap> {
    ImageView bmImage;
    //constructor
    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    // laoding picture and put it into bitmap
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

    //after downloading
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
