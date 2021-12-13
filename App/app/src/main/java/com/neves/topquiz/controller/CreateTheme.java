package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import com.neves.topquiz.model.Question;
import com.neves.topquiz.model.QuestionBank;
import com.neves.topquiz.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateTheme extends AppCompatActivity {
    private static final String USER = "USER";
    private static final String BUNDLE_STATE_QUESTION_BANK = "BUNDLE_STATE_QUESTION_BANK";
    private static final String BUNDLE_STATE_THEME_NAME = "BUNDLE_STATE_THEME_NAME";
    private static final String CHOSEN_THEME = "CHOSEN_THEME";
    private AutoCompleteTextView mThemeInput;
    private Button mAddQuestionBtn;
    private Button mSubmitQuiz;
    private Button mEnterBtn;
    private LinearLayout mThemeQuestionsListContainer;
    private QuestionBank mQuestionBank;
    private String mThemeName;
    private ViewGroup.LayoutParams btnParams;
    private Theme mTheme;
    List<Theme> spinnerArray;
    List<Theme> themeList = new ArrayList<>();
    User mUser;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_theme);
        findViewById(R.id.create_theme_question1_btn).setVisibility(View.GONE);
        mThemeQuestionsListContainer = findViewById(R.id.create_theme_questionsList_lt);

        mThemeInput = findViewById(R.id.autoCompleteTextView);
        mAddQuestionBtn = findViewById(R.id.create_theme_addQuestion_btn);
        mSubmitQuiz = findViewById(R.id.create_theme_submitQuiz_btn);
        mEnterBtn = findViewById(R.id.create_theme_submitTheme_btn);
        btnParams = findViewById(R.id.create_theme_question1_btn).getLayoutParams();

        spinnerArray = new ArrayList<>();

        updateListSpinner();
        CustomAdapter adapter = new CustomAdapter(this,
                R.layout.spinner_item_layout_resource,
                R.id.textView_item_name,
                themeList);
        mThemeInput.setThreshold(1);
        mThemeInput.setAdapter(adapter);
        //adapter.getFilter().filter(null);


        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }
        if (intent.hasExtra(CHOSEN_THEME)) {
            mTheme = intent.getParcelableExtra(CHOSEN_THEME);
            mThemeInput.setText(mTheme.getTitle());
            questionsDisplay(mTheme);
            Toast.makeText(this, getString(R.string.questionAdded), Toast.LENGTH_SHORT).show();
        }


        mThemeInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mThemeInput.showDropDown();
                return false;
            }
        });

        mThemeInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mThemeInput.showDropDown();
                return false;
            }
        });

        mThemeInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mTheme = (Theme) themeList.get(position);
                questionsDisplay(mTheme);
                mEnterBtn.setEnabled(mTheme.getTitle().length() != 0);
            }
        });
        mThemeInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateListSpinner();
                for (Theme i : themeList) {
                    System.out.println(i.getTitle());
                }
                mEnterBtn.setEnabled(mThemeInput.getText().toString().length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInArray(mThemeInput.getText().toString())) {
                    //adapter.notifyDataSetChanged();
                    questionsDisplay(mTheme);
                    mEnterBtn.setEnabled(false);
                } else {
                    Theme newTheme = new Theme("", mThemeInput.getText().toString(), "TropLol", 0);
                    themeList.add(newTheme);
                    //modif api
                    adapter.updateList(themeList);
                    updateListSpinner();
                    questionsDisplay(newTheme);
                    Toast.makeText(CreateTheme.this, R.string.newThemeCreation, Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAddQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddQuestion = new Intent(CreateTheme.this, CreateQuestion.class);
                AddQuestion.putExtra(CHOSEN_THEME, mTheme);
                AddQuestion.putExtra(USER, mUser);
                startActivity(AddQuestion);
                finish();
            }
        });
        mSubmitQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mettre à jour la bdd
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_STATE_THEME_NAME, mThemeName);
    }

    private void updateListSpinner() {
        AndroidNetworking.get(GlobalVariable.API_URL + "/api/theme")
                .setTag("getAllThemes")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("themes")) {
                            try {
                                JSONArray themes = response.getJSONArray("themes");
                                themeList.clear();
                                for (int i = 0; i < themes.length(); i++) {
                                    JSONObject theme = themes.getJSONObject(i);
                                    themeList.add(new Theme(GlobalVariable.API_URL + theme.getString("imageUrl"), theme.getString("title"), theme.getString("description"), theme.getInt("nbQuestion")));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("themesError", error.toString());
                    }
                });
    }

    public boolean isInArray(String s) {

        for (Theme t : themeList) {
            if (s.equals(t.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public void questionsDisplay(Theme theme) {
        mThemeQuestionsListContainer.removeAllViews();
        AndroidNetworking.get(GlobalVariable.API_URL + "/api/question/randomQuestion/" + theme.getTitle() + "/" + 99999)
                .setTag("getQuestions")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray questionsJSONArray = response.getJSONArray("questions");
                            for (int i = 0; i < questionsJSONArray.length(); i++) {
                                JSONObject questionJSONObject = questionsJSONArray.getJSONObject(i);
                                Button myButton = new Button(CreateTheme.this);
                                myButton.setLayoutParams(btnParams);
                                myButton.setText(questionJSONObject.getString("questionTitle"));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    myButton.setTextColor(getColor(R.color.colorBlack));
                                }
                                myButton.setBackground(getDrawable(R.drawable.container_answer_button_correct));
                                mThemeQuestionsListContainer.addView(myButton);
                            }
                        } catch (JSONException jsonException) {
                            Log.d("getQuestions", jsonException.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("getQuestions", anError.toString());
                    }
                });

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
                            JSONObject questionJSONObject = response.getJSONObject("question");
                            Button myButton = new Button(CreateTheme.this);
                            myButton.setLayoutParams(btnParams);
                            myButton.setText(questionJSONObject.getString("questionTitle"));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                myButton.setTextColor(getColor(R.color.colorBlack));
                            }
                            myButton.setBackground(getDrawable(R.drawable.container_answer_button_wait));
                            mThemeQuestionsListContainer.addView(myButton);

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
