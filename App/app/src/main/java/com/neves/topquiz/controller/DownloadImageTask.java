package com.neves.topquiz.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.InputStream;
import java.net.URL;

///Classe permerttant de télécharger une image via une URL et de la convertir au format bitmap
class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
    ShapeableImageView imageView;

    public DownLoadImageTask(ShapeableImageView imageView){
        this.imageView = imageView;
    }
    /*
        doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{
            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }
    /*
        onPostExecute(Result result)
            Runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}