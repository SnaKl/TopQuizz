package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;
import com.neves.topquiz.model.Question;
import com.neves.topquiz.model.QuestionBank;
import com.neves.topquiz.model.Score;
import com.neves.topquiz.model.User;

import java.io.IOException;
import java.util.Arrays;

public class ValidateQuestion extends AppCompatActivity {
    public static final String USER = "USER";

    private User mUser;
    private Button mApprove;
    private Button mReject;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private TextView mQuestionTitle;
    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;
    private ShapeableImageView mQuestionImageView;

    private int mRemainingQuestionCount;
    private boolean mEnableTouchEvents;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_question);

        Intent intent = getIntent();

        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }

        mUser.initLastQuestionRecap();

        try {
            mQuestionBank = this.generateQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mRemainingQuestionCount = mQuestionBank.getSize();

        mEnableTouchEvents = true;

        mApprove = findViewById(R.id.validate_question_accept_btn);
        mReject = findViewById(R.id.validate_question_reject_btn);
        mQuestionImageView = findViewById(R.id.validate_question_image_siv);
        mQuestionTitle = findViewById(R.id.validate_question_title_tv);
        mQuestionTextView = findViewById(R.id.validate_question_question_tv);
        mAnswerButton1 = findViewById(R.id.validate_question_answer1_btn);
        mAnswerButton2 = findViewById(R.id.validate_question_answer2_btn);
        mAnswerButton3 = findViewById(R.id.validate_question_answer3_btn);
        mAnswerButton4 = findViewById(R.id.validate_question_answer4_btn);

        //utiliser le principe de game activity pour faire défiler les questions
        mApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mettre à jour bdd questions bank + supprimer des questions à valider

                mUser.addQuestionToQuestionRecap(mCurrentQuestion,true);

                mEnableTouchEvents = false;
                mRemainingQuestionCount--;

                new Handler().postDelayed(() -> {
                    mEnableTouchEvents = true;
                    System.out.println(mRemainingQuestionCount);
                    if (mRemainingQuestionCount > 0) {
                        // If this is the last question, ends the game.
                        // Else, display the next question.
                        mCurrentQuestion = mQuestionBank.getNextQuestion();
                        displayQuestion(mCurrentQuestion);
                    } else {
                        // No questions left, end the game
                        //DELETE THIS THEME FROM THE ARRAY
                        finish();
                    }
                }, 2000);
            }
        });

        mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //supprimer des questions à valider
            }
        });

        mCurrentQuestion = mQuestionBank.getNextQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    //à enlever
    private QuestionBank generateQuestions() throws IOException {

        Question question1 = new Question(null, null, "https://i.ibb.co/kSLD4RJ/iv-office.png", "Question historique",(getString(R.string.question1)),
                Arrays.asList(getString(R.string.response11), getString(R.string.response12), getString(R.string.response13), getString(R.string.response14)),
                2);

        Question question2 = new Question(null, null, null, "Question layout",(getString(R.string.question2)),
                Arrays.asList(getString(R.string.response21), getString(R.string.response22), getString(R.string.response23), getString(R.string.response24)),
                2);

        Question question3 = new Question(null, null, "https://i.ibb.co/ys7B1MW/iv-bigbangtheory.jpg", "Question graphique",(getString(R.string.question3)),
                Arrays.asList(getString(R.string.response31), getString(R.string.response32), getString(R.string.response33), getString(R.string.response34)),
                1);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3));
        //return loadQuestions();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayQuestion(final Question question) {
        mQuestionTitle.setText(question.getQuestionTitle());
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getAnswerList().get(0));
        mAnswerButton2.setText(question.getAnswerList().get(1));
        mAnswerButton3.setText(question.getAnswerList().get(2));
        mAnswerButton4.setText(question.getAnswerList().get(3));
        new DownLoadImageTask(mQuestionImageView).execute(question.getImageUrl());
    }
}

