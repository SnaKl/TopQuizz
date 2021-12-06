package com.neves.topquiz.controller;

import com.neves.topquiz.R;
import com.neves.topquiz.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnswerRecap extends AppCompatActivity {
    public static final String USER = "USER";
    private User mUser;
    private TextView mNumberOfPoints;
    private TextView mNumberOfCorrectAnswers;
    private TextView mNumberOfQuestions;
    private Button mReturnMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_recap);

        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }

        mReturnMenu = findViewById(R.id.answer_recap_returnMenu_btn);
        mNumberOfPoints = findViewById(R.id.answer_recap_numberOfPoints_tv);
        mNumberOfCorrectAnswers = findViewById(R.id.answer_recap_numberOfCorrectAnswers_tv);
        mNumberOfQuestions = findViewById(R.id.answer_recap_numberOfQuestions_tv);

        mNumberOfCorrectAnswers.setText(mUser.getScore().getPoints()+"");
        mNumberOfPoints.setText(mUser.getScore().getPoints()*10+"");

        mReturnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GameActivity = new Intent(AnswerRecap.this, MainMenu.class);
                GameActivity.putExtra(USER, mUser);
                startActivity(GameActivity);
            }
        });
    }

}