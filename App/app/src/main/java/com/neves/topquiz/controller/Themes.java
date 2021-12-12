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
import android.widget.ImageView;
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
    private static final String USER = "USER";
    private static final String THEME = "THEME";
    private static final String MODE = "MODE";
    private User mUser;
    private String mMode;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
            mMode = intent.getStringExtra(MODE);
        }

        List<Theme> themeList=new ArrayList<>();

        if(mMode.equals("GAME")){
            // Tous les thèmes jouables
            themeList.add(new Theme("https://i.ibb.co/kSLD4RJ/iv-office.png","THE OFFICE","TESTLOL",5));
            themeList.add(new Theme("https://media.senscritique.com/media/000018827756/source_big/Community.jpg","COMMUNITY","TESTLOL",5));
            themeList.add(new Theme("https://i.ibb.co/ys7B1MW/iv-bigbangtheory.jpg","BIG BANG THEORY","TESTLOL",5));
            themeList.add(new Theme("https://www.rts.ch/2021/03/19/17/16/12059275.image?w=640&h=360","FRIENDS","TESTLOL",5));
            themeList.add(new Theme("https://imgsrc.cineserie.com/2016/06/1884786.jpg?ver=1","BROOKLYN99","TESTLOL",5));
            themeList.add(new Theme("https://fr.web.img6.acsta.net/pictures/19/01/31/09/49/3574048.jpg","SUITS","TESTLOL",5));
            themeList.add(new Theme("https://i.ibb.co/kSLD4RJ/iv-office.png","THE OFFICE","TESTLOL",5));
            themeList.add(new Theme("https://media.senscritique.com/media/000018827756/source_big/Community.jpg","COMMUNITY","TESTLOL",5));
            themeList.add(new Theme("https://i.ibb.co/ys7B1MW/iv-bigbangtheory.jpg","BIG BANG THEORY","TESTLOL",5));
            themeList.add(new Theme("https://www.rts.ch/2021/03/19/17/16/12059275.image?w=640&h=360","FRIENDS","TESTLOL",5));
            themeList.add(new Theme("https://imgsrc.cineserie.com/2016/06/1884786.jpg?ver=1","BROOKLYN99","TESTLOL",5));
            themeList.add(new Theme("https://fr.web.img6.acsta.net/pictures/19/01/31/09/49/3574048.jpg","SUITS","TESTLOL",5));
        }else if(mMode.equals("VALIDATE_THEME")){
            // Thèmes / questions à valider
            themeList.add(new Theme("https://i.ibb.co/kSLD4RJ/iv-office.png","THE OFFICE","TESTLOL",5));
            themeList.add(new Theme("https://media.senscritique.com/media/000018827756/source_big/Community.jpg","COMMUNITY","TESTLOL",5));
            themeList.add(new Theme("https://i.ibb.co/ys7B1MW/iv-bigbangtheory.jpg","BIG BANG THEORY","TESTLOL",5));
            themeList.add(new Theme("https://www.rts.ch/2021/03/19/17/16/12059275.image?w=640&h=360","FRIENDS","TESTLOL",5));
        }


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
        List<ShapeableImageView> buttonList=new ArrayList<>();
        for(Theme theme : themeList){
            ShapeableImageView themeButton = new ShapeableImageView(this);
            new DownLoadImageTask(themeButton).execute(theme.getImage());
            manageButtonClickListener(themeButton, theme);
            roundButtonCorners(themeButton);

            themeButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            themeButton.setElevation(8);
            if(left) {
                themeButton.setLayoutParams(left_btn_mockup.getLayoutParams());
                themeButton.setId(R.id.themes_left_btn);
                themeButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                row.addView(themeButton);
                left=false;
            }else{
                themeButton.setLayoutParams(right_btn_mockup.getLayoutParams());
                themeButton.setId(R.id.themes_right_btn);
                themeButton.setScaleType(ImageView.ScaleType.CENTER_CROP);

                left=true;
                row.addView(themeButton);
                listLayout.addView(row);
                row=createRow(row_mockup);
                buttonList.add(themeButton);
            }
        }
        if(!left){
            listLayout.addView(row);
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
                Intent GameActivity;
                if(mMode.equals("GAME")){
                    GameActivity = new Intent(Themes.this, GameActivity.class);
                }else{
                    GameActivity = new Intent(Themes.this, ValidateQuestion.class);
                }
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

    /*private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{
        ShapeableImageView imageView;

        public DownLoadImageTask(ShapeableImageView imageView){
            this.imageView = imageView;
        }
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }*/
}