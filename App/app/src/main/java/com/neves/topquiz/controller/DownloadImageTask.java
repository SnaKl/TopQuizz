package com.neves.topquiz.controller;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;

import java.io.InputStream;
import java.net.URL;

// Class that allows to download an image from an URL and convert it to bitmap format
class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {

    ShapeableImageView imageView;
    String urlOfImage;
    Bitmap finalImage;

    public DownLoadImageTask(ShapeableImageView imageView) {
        this.imageView = imageView;
    }

    /*
     * doInBackground(Params... params) : override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String... urls) {
        urlOfImage = urls[0];
        finalImage = null;
        try {
            InputStream is = new URL(urlOfImage).openStream();
            /*
             * decodeStream(InputStream is) : decode an input stream into a bitmap.
             */
            finalImage = BitmapFactory.decodeStream(is);
        } catch (Exception e) { // Catch the download exception
            e.printStackTrace();
        }
        return finalImage;
    }

    /*
     * onPostExecute(Result result) : runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result) {
        if (finalImage == null) {
            imageView.setImageDrawable(getDrawable(imageView.getContext(), R.drawable.iv_image_not_found));
        } else {
            imageView.setImageBitmap(result);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}