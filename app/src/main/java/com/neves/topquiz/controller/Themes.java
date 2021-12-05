package com.neves.topquiz.controller;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Themes extends AppCompatActivity {
    private ShapeableImageView mTheme1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        mTheme1 = findViewById(R.id.themes_theme1_btn);

        mTheme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChooseTheme = new Intent(Themes.this, GameActivity.class);
                startActivity(ChooseTheme);
            }
        });
    }

}