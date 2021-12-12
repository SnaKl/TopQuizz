package com.neves.topquiz.controller;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.GlobalVariable;
import com.neves.topquiz.R;
import com.neves.topquiz.model.Theme;
import com.neves.topquiz.model.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Themes extends AppCompatActivity {
    public static final String USER = "USER";
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
        
        AndroidNetworking.get(GlobalVariable.API_URL+"/api/theme")
                .setTag("getAllThemes")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("themes")) {
                            try {
                                JSONArray themes = response.getJSONArray("themes");
                                for (int i = 0 ; i < themes.length(); i++) {
                                    JSONObject theme = themes.getJSONObject(i);
                                    themeList.add(new Theme(GlobalVariable.API_URL + theme.getString("imageUrl"), theme.getString("title"), theme.getString("description"),theme.getInt("nbQuestion")));
                                }
                                createLayout(themeList);
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("themesError", error.toString());
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createLayout(List<Theme> themeList) throws IOException {
        LinearLayout listLayout = findViewById(R.id.themes_themesList_lt);

        ShapeableImageView left_btn_mockup= findViewById(R.id.themes_left_btn);
        ShapeableImageView right_btn_mockup= findViewById(R.id.themes_right_btn);
        TextView left_title_mockup = findViewById(R.id.themes_leftThemeTitle_tv);
        TextView right_title_mockup = findViewById(R.id.themes_rightThemeTitle_tv);
        ConstraintLayout row_mockup = findViewById(R.id.themes_mockupThemeRow_lt);
        listLayout.removeAllViews();
        boolean left=true;
        ConstraintLayout row = createRow(row_mockup);
        List<ShapeableImageView> buttonList=new ArrayList<>();
        for(Theme theme : themeList){
            ShapeableImageView themeButton = new ShapeableImageView(this);
            TextView themeTitle = new TextView(this);
            themeTitle.setText(theme.getTitle());
            new DownLoadImageTask(themeButton).execute(theme.getImage());
            manageButtonClickListener(themeButton, theme);
            roundButtonCorners(themeButton);
            themeButton.setElevation(8);
            themeTitle.setTextColor(getResources().getColor(R.color.colorWhite));
            themeTitle.setTypeface(themeTitle.getTypeface(), Typeface.BOLD);
            themeTitle.setElevation(16);
            //

            if(left) {
                themeButton.setLayoutParams(left_btn_mockup.getLayoutParams());
                themeButton.setId(R.id.themes_left_btn);
                themeButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                themeTitle.setLayoutParams(left_title_mockup.getLayoutParams());
                themeTitle.setId(R.id.themes_leftThemeTitle_tv);
                themeTitle.setShadowLayer(0.7f,1.5f,1.5f,R.color.colorBlack);
                row.addView(themeButton);
                row.addView(themeTitle);
                left=false;
            }else{
                themeButton.setLayoutParams(right_btn_mockup.getLayoutParams());
                themeButton.setId(R.id.themes_right_btn);
                themeButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                themeTitle.setLayoutParams(right_title_mockup.getLayoutParams());
                themeTitle.setId(R.id.themes_rightThemeTitle_tv);
                themeTitle.setShadowLayer(0.7f,1.5f,1.5f,R.color.colorBlack);
                left=true;
                row.addView(themeButton);
                row.addView(themeTitle);
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