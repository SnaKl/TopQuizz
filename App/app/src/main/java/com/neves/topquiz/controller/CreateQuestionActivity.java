package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.neves.topquiz.GlobalVariable;
import com.neves.topquiz.R;
import com.neves.topquiz.model.Theme;
import com.neves.topquiz.model.User;

import org.json.JSONObject;

public class CreateQuestionActivity extends AppCompatActivity {
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
    private Theme mTheme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }
        if (intent.hasExtra(CHOSEN_THEME)) {
            mTheme = intent.getParcelableExtra(CHOSEN_THEME);
        }

        mQstTitle = findViewById(R.id.create_question_title);
        mQuestionInput = findViewById(R.id.create_question_question_tv);
        mAnswer1Input = findViewById(R.id.create_question_answer1_input);
        mAnswer2Input = findViewById(R.id.create_question_answer2_input);
        mAnswer3Input = findViewById(R.id.create_question_answer3_input);
        mAnswer4Input = findViewById(R.id.create_question_answer4_input);
        mEditImgBtn = findViewById(R.id.create_question_editImage_btn);
        mSubmitQuestion = findViewById(R.id.create_questions_submitQuestion_btn);

        mThemeNewQuestionContainer = findViewById(R.id.create_theme_questionsList_lt);
        mSubmitQuestion.setOnClickListener(v -> {
            updateDB();
        });

        mEditImgBtn.setOnClickListener(v -> {
            // Display gallery to put an image
        });
    }

    void updateDB() {
        String question = mQuestionInput.getText().toString();
        String answer1 = mAnswer1Input.getText().toString();
        String answer2 = mAnswer2Input.getText().toString();
        String answer3 = mAnswer3Input.getText().toString();
        String answer4 = mAnswer4Input.getText().toString();
        String questionTitle = mQstTitle.getText().toString();
        String answers = answer1 + "///" + answer2 + "///" + answer3 + "///" + answer4;

        AndroidNetworking.post(GlobalVariable.API_URL + "/api/question/")
                .addHeaders("Authorization", "Bearer " + mUser.getJwtToken())
                .addBodyParameter("themeTitle", mTheme.getTitle())
                .addBodyParameter("description", question)
                .addBodyParameter("questionTitle", questionTitle)
                .addBodyParameter("correctAnswerIndex", "0")
                .addBodyParameter("answerList", answers)
                .setTag("setQuestions")
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
                        if (anError.getErrorBody() == null) {
                            goBackToThemeCreation();
                        } else {
                            Log.d("getQuestions", anError.getErrorBody());
                        }
                    }
                });
    }

    private void goBackToThemeCreation() {
        Intent CreateThemeActivity = new Intent(CreateQuestionActivity.this, CreateThemeActivity.class);
        CreateThemeActivity.putExtra(USER, mUser);
        CreateThemeActivity.putExtra(CHOSEN_THEME, mTheme);
        startActivity(CreateThemeActivity);
        finish();
    }

}
