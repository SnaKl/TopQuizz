package com.neves.topquiz.controller;

import com.neves.topquiz.R;
import com.neves.topquiz.model.Question;
import com.neves.topquiz.model.Score;
import com.neves.topquiz.model.User;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AnswerRecap extends AppCompatActivity {
    public static final String USER = "USER";
    private User mUser;
    private Score mScore;
    private TextView mNumberOfPoints;
    private TextView mNumberOfCorrectAnswers;
    private TextView mNumberOfQuestions;
    private LinearLayout mQuestionsListContainer;
    private Button mReturnMenu;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_recap);

        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }

        mScore = mUser.getScore();
        mQuestionsListContainer = (LinearLayout) findViewById(R.id.answer_recap_questionsListContainer_lt);
        mReturnMenu = findViewById(R.id.answer_recap_returnMenu_btn);
        mNumberOfPoints = findViewById(R.id.answer_recap_numberOfPoints_tv);
        mNumberOfCorrectAnswers = findViewById(R.id.answer_recap_numberOfCorrectAnswers_tv);
        mNumberOfQuestions = findViewById(R.id.answer_recap_numberOfQuestions_tv);

        mNumberOfCorrectAnswers.setText(mScore.getPoints()+"");
        mNumberOfPoints.setText(mScore.getPoints()*100+"");

        findViewById(R.id.answer_recap_question1Result_btn).setVisibility(View.GONE);
        for(int i=0;i<mUser.getQuestionRecapSize();i++){
            Button myButton = new Button(this);
            myButton.setLayoutParams(findViewById(R.id.answer_recap_question1Result_btn).getLayoutParams());
            myButton.setText(mUser.getQuestionRecapQuestionTitle(i));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                myButton.setTextColor(getColor(R.color.colorBlack));
            }
            if(mUser.getQuestionRecapResult(i)) {
                myButton.setBackground(getDrawable(R.drawable.container_answer_button_correct));
            }else{
                myButton.setBackground(getDrawable(R.drawable.container_answer_button_wrong));
            }
            mQuestionsListContainer.addView(myButton);
        }

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