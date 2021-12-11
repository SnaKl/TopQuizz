package com.neves.topquiz.controller;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;
import com.neves.topquiz.model.Theme;
import com.neves.topquiz.model.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Themes extends AppCompatActivity {
    public static final String USER = "USER";
    public static final String THEME = "THEME";
    private User mUser;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }

        List<Theme> themeList=new ArrayList<>();

        themeList.add(new Theme("https://i.ibb.co/kSLD4RJ/iv-office.png","THE OFFICE","TESTLOL",5));
        themeList.add(new Theme("https://i.ibb.co/kSLD4RJ/iv-office.png","THE OFFICE","TESTLOL",5));
        themeList.add(new Theme("https://i.ibb.co/ys7B1MW/iv-bigbangtheory.jpg","BIG BANG THEORY","TESTLOL",5));
        themeList.add(new Theme("https://i.ibb.co/kSLD4RJ/iv-office.png","THE OFFICE","TESTLOL",5));
        themeList.add(new Theme("https://i.ibb.co/ys7B1MW/iv-bigbangtheory.jpg","BIG BANG THEORY","TESTLOL",5));
        themeList.add(new Theme("https://i.ibb.co/kSLD4RJ/iv-office.png","THE OFFICE","TESTLOL",5));

        try {
            createLayout(themeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createLayout(List<Theme> themeList) throws IOException {
        LinearLayout listLayout = findViewById(R.id.themes_themesList_lt);

        ShapeableImageView left_btn_mockup= findViewById(R.id.themes_left_btn);
        ShapeableImageView right_btn_mockup= findViewById(R.id.themes_right_btn);
        ConstraintLayout row_mockup = findViewById(R.id.themes_mockupThemeRow_lt);
        listLayout.removeAllViews();
        boolean left=true;
        ConstraintLayout row = createRow(row_mockup);
        for(Theme theme : themeList){

            ShapeableImageView themeButton = new ShapeableImageView(this);
            themeButton.setLayoutParams(left_btn_mockup.getLayoutParams());
            new DownLoadImageTask(themeButton).execute(theme.getImage());
            manageButtonClickListener(themeButton, theme);
            roundButtonCorners(themeButton);
            themeButton.setScaleType(ShapeableImageView.ScaleType.CENTER_CROP);
            themeButton.setElevation(8);

            if(left) {
                themeButton.setLayoutParams(left_btn_mockup.getLayoutParams());
                row.addView(themeButton);
                left=false;
            }else{
                themeButton.setLayoutParams(right_btn_mockup.getLayoutParams());
                left=true;
                row.addView(themeButton);
                listLayout.addView(row);
                row=createRow(row_mockup);
            }

        }
    }
    private ConstraintLayout createRow(ConstraintLayout row_mockup){
        ConstraintLayout caseConstraint = new ConstraintLayout(this);
        caseConstraint.setLayoutParams(row_mockup.getLayoutParams());
        return caseConstraint;
    }

    private void manageButtonClickListener(ShapeableImageView button, Theme theme){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GameActivity = new Intent(Themes.this, GameActivity.class);
                GameActivity.putExtra(USER, mUser);
                GameActivity.putExtra(THEME, theme);
                startActivity(GameActivity);
            }
        });
    }

    private void roundButtonCorners(ShapeableImageView button){
        button.setShapeAppearanceModel(button.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCornerSize(25)
                .setTopLeftCornerSize(25)
                .setBottomRightCornerSize(25)
                .setBottomLeftCornerSize(25)
                .build());
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{
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
        }
    }
}