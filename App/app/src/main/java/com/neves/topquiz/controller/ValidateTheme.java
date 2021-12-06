package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;

public class ValidateTheme extends AppCompatActivity {
    private ShapeableImageView mValTheme1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_theme);
        mValTheme1 = findViewById(R.id.validate_themes_theme1_btn);

        mValTheme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChooseTheme = new Intent(ValidateTheme.this, ValidateQuestion.class);
                startActivity(ChooseTheme);
            }
        });
    }
}
