package com.example.martin.pokepote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import  android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;


/**
 * Created by Martin on 20/03/2015.
 */
public class DownloadImageTask {

    public DownloadImageTask(ImageView bmImage, String url, Context c) {
        download(bmImage, url, c);
    }

    public void download(ImageView bmImage, String url, Context c) {

        if (cancelPotentialDownload(url, bmImage)) {
            DownloadImageTaskTask task = new DownloadImageTaskTask(bmImage,url, c);
            DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
            bmImage.setImageDrawable(downloadedDrawable);
            task.execute(url);
        }

    }

    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        DownloadImageTaskTask downloadImageTask = getBitmapDownloaderTask(imageView);

        if (downloadImageTask != null) {
            String bitmapUrl = downloadImageTask.url;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                downloadImageTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    private static DownloadImageTaskTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }
        return null;
    }

    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<DownloadImageTaskTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(DownloadImageTaskTask bitmapDownloaderTask) {
            super(Color.BLACK);
            bitmapDownloaderTaskReference = new WeakReference<DownloadImageTaskTask>(bitmapDownloaderTask);
        }

        public DownloadImageTaskTask getBitmapDownloaderTask() {
            return bitmapDownloaderTaskReference.get();
        }
    }

    public class DownloadImageTaskTask extends AsyncTask<String, Bitmap, Bitmap> {

            String url;
            ImageView bmImage;
            String imagePath;
            Context c;
            private final WeakReference<ImageView> imageViewReference;

            //constructor
        public DownloadImageTaskTask(ImageView bmImage, String url, Context f) {
            this.url = url;
            this.bmImage = bmImage;
            this.c = f;
            imageViewReference = new WeakReference<ImageView>(bmImage);
        }

            // laoding picture and put it into bitmap

        protected Bitmap doInBackground(String... urls) {

            String urldisplay = urls[0];
            Bitmap mIcon11 = null;

            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                in.close();
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    // Get the directory for the app's private pictures directory.
                    File path = new File(c.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "iconDex");
                    File image = new File(path.getPath() + "/a" + urldisplay.substring(urldisplay.lastIndexOf("/") + 1));
                    if (!path.mkdirs()) {
                    }
                    else {
                        FileOutputStream out = null;
                        try {
                            out = new FileOutputStream(image);

                            System.out.println(path.getPath() + "/a" + urldisplay.substring(urldisplay.lastIndexOf("/") + 1));

                            mIcon11.compress(Bitmap.CompressFormat.PNG, 100, out);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                out.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }

        //after downloading
        protected void onPostExecute(Bitmap result) {

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                DownloadImageTaskTask downloadImageTask = getBitmapDownloaderTask(imageView);
                // Change bitmap only if this process is still associated with it
                if (this == downloadImageTask) {
                    imageView.setImageBitmap(BitmapFactory.decodeFile(this.imagePath));
                }
            }
        }
    }
}