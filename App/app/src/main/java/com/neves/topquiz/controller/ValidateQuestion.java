package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;

public class ValidateQuestion extends AppCompatActivity {
    private Button mApprove;
    private Button mReject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_theme);
        mApprove = findViewById(R.id.validate_question_accept_btn);
        mReject = findViewById(R.id.validate_question_accept_btn);

        //utiliser le principe de game activity pour faire défiler les questions
        mApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mettre à jour bdd questions bank + supprimer des questions à valider
            }
        });

        mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //supprimer des questions à valider
            }
        });
    }
}

