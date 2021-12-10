package com.neves.topquiz.controller;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;
import com.neves.topquiz.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Themes extends AppCompatActivity {
    private ShapeableImageView mTheme1;
    private String mName;
    public static final String USER = "USER";
    private User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }

        mTheme1 = findViewById(R.id.themes_theme1_btn);

        mTheme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GameActivity = new Intent(Themes.this, GameActivity.class);
                GameActivity.putExtra(USER, mUser);
                startActivity(GameActivity);
            }
        });
    }

}