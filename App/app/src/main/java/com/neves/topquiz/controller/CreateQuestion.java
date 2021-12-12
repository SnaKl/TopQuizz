package com.neves.topquiz.controller;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.neves.topquiz.GlobalVariable;
import com.neves.topquiz.model.Question;
import com.neves.topquiz.model.Theme;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.neves.topquiz.R;
import com.neves.topquiz.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateQuestion extends AppCompatActivity {
    private EditText mQuestionInput;
    private EditText mAnswer1Input;
    private EditText mAnswer2Input;
    private EditText mAnswer3Input;
    private EditText mAnswer4Input;
    private EditText mQstTitle;
    private ImageButton mEditImgBtn;
    private Button mSubmitQuestion;
    private LinearLayout mThemeNewQuestionContainer;
    private LayoutInflater flater;
    private User mUser;
    private static final String CHOSEN_THEME = "CHOSEN_THEME";
    public static final String USER = "USER";
    //private static final String ADDQST = "ADDQST";
    private Theme mTheme;
    //private int mNewQstNb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }
        if (intent.hasExtra(CHOSEN_THEME)) {
            mTheme = intent.getParcelableExtra(CHOSEN_THEME);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        mQstTitle = findViewById(R.id.create_question_title);
        mQuestionInput = findViewById(R.id.create_question_question_tv);
        mAnswer1Input = findViewById(R.id.create_question_answer1_input);
        mAnswer2Input = findViewById(R.id.create_question_answer2_input);
        mAnswer3Input = findViewById(R.id.create_question_answer3_input);
        mAnswer4Input = findViewById(R.id.create_question_answer4_input);
        mEditImgBtn = findViewById(R.id.create_question_editImage_btn);
        mSubmitQuestion = findViewById(R.id.create_questions_submitQuestion_btn);

        mThemeNewQuestionContainer = (LinearLayout) findViewById(R.id.create_theme_questionsList_lt);
        mSubmitQuestion.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                System.out.println(mTheme.getTitle());
                updateDB();

            }
        });

        mEditImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //afficher gallerie to put an image
            }
        });
    }

    void updateDB(){
        Theme theme = mTheme;
        User userCreatedBy = new User(" ", " ", " ", " ");
        String imgUrl = "";
        String question = mQuestionInput.getText().toString();
        String answer1 = mAnswer1Input.getText().toString();
        String answer2 = mAnswer2Input.getText().toString();
        String answer3 = mAnswer3Input.getText().toString();
        String answer4 = mAnswer4Input.getText().toString();
        String questionTitle = mQstTitle.getText().toString();

        JSONObject answers = new JSONObject();
        JSONArray answerList = new JSONArray();
        answerList.put(answer1);
        answerList.put(answer2);
        answerList.put(answer3);
        answerList.put(answer4);
        AndroidNetworking.post(GlobalVariable.API_URL + "/api/question/")
                        .addHeaders("Authorization", "Bearer " + mUser.getJwtToken())
                        .addBodyParameter("theme",mTheme.getTitle())
                        .addBodyParameter("description",question)
                        .addBodyParameter("questionTitle",questionTitle)
                        .addBodyParameter("correctAnswerIndex","0")
                        .addBodyParameter("answerList", answerList.toString())
                        //.setTag("setQuestions")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onResponse(JSONObject response) {
                                goBackToThemeCreation();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("getQuestions", anError.toString());
                            }
                        });
    }

    private void goBackToThemeCreation(){
        Intent CreateThemeActivity = new Intent(CreateQuestion.this, CreateTheme.class);
        CreateThemeActivity.putExtra(CHOSEN_THEME,mTheme);
        startActivity(CreateThemeActivity);
        finish();
    }

}
