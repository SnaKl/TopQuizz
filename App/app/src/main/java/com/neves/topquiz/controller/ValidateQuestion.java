package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.GlobalVariable;
import com.neves.topquiz.R;
import com.neves.topquiz.model.Question;
import com.neves.topquiz.model.QuestionBank;
import com.neves.topquiz.model.Theme;
import com.neves.topquiz.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValidateQuestion extends AppCompatActivity {
    public static final String USER = "USER";
    public static final String THEME = "THEME";

    private User mUser;
    private Theme mTheme;
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

    private String mCurrentQuestionId;

    private boolean mEnableTouchEvents;

    private final ValidateQuestion mValidateQuestion = this;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_question);

        Intent intent = getIntent();

        if (!intent.hasExtra(USER)) {
            this.finish();
        }
        mUser = intent.getParcelableExtra(USER);
        //mUser.initLastQuestionRecap();

        if (!intent.hasExtra(THEME)) {
            this.finish();
        }
        mTheme = intent.getParcelableExtra(THEME);

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

        generateQuestion();

        //utiliser le principe de game activity pour faire défiler les questions
        mApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote("upVote");
            }
        });

        mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote("downVote");
            }
        });

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

    private void vote(String choice){
        AndroidNetworking.put(GlobalVariable.API_URL + "/api/question/vote/" + mCurrentQuestionId)
                .addHeaders("Authorization", "Bearer " + mUser.getJwtToken())
                .addBodyParameter("vote", choice)
                .setTag("putQuestionToValidate")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        generateQuestion();
                    }

                    @Override
                    public void onError(ANError error) {
                        mValidateQuestion.finish();
                    }
                });
    }

    private void generateQuestion(){
        AndroidNetworking.get(GlobalVariable.API_URL + "/api/question/vote/" + mTheme.getTitle())
                .addHeaders("Authorization", "Bearer " + mUser.getJwtToken())
                .setTag("getQuestionToValidate")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(!response.has("question")){
                                mValidateQuestion.finish();
                                return;
                            }

                            JSONObject questionJSONObject = response.getJSONObject("question");
                            JSONArray answerListJSONArray = questionJSONObject.getJSONArray("answerList");
                            List<String> answerList = Arrays.asList(answerListJSONArray.getString(0), answerListJSONArray.getString(1), answerListJSONArray.getString(2), answerListJSONArray.getString(3));
                            Collections.shuffle(answerList);
                            Question question = new Question(
                                    mTheme,
                                    new User("UNKNOWN", "", "", ""),
                                    questionJSONObject.getString("_id"),
                                    questionJSONObject.getString("imageUrl"),
                                    questionJSONObject.getString("questionTitle"),
                                    questionJSONObject.getString("description"),
                                    answerList,
                                    questionJSONObject.getInt("correctAnswerIndex"));

                            mCurrentQuestionId = question.getQuestionId();
                            displayQuestion(question);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("validateQuestion", error.getErrorBody().toString());
                    }
                });
    }
}

