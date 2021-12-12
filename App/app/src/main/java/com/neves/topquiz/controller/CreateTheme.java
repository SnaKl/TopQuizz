package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.neves.topquiz.model.Score;
import com.neves.topquiz.model.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.neves.topquiz.model.Question;
import com.neves.topquiz.model.QuestionBank;
import com.neves.topquiz.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateTheme extends AppCompatActivity {
    private static final String BUNDLE_STATE_QUESTION_BANK = "BUNDLE_STATE_QUESTION_BANK";
    private static final String BUNDLE_STATE_THEME_NAME = "BUNDLE_STATE_THEME_NAME" ;
    private static final String CHOSEN_THEME = "CHOSEN_THEME";
    private AutoCompleteTextView mThemeInput;
    //private List<Theme> themeList;
    private Button mAddQuestionBtn;
    private Button mSubmitQuiz;
    private Button mEnterBtn;
    private LinearLayout mThemeQuestionsListContainer;
    private QuestionBank mQuestionBank;
    private String mThemeName;
    private ViewGroup.LayoutParams btnParams;
    private Theme mTheme;
    List<Theme> spinnerArray;
    List<Theme> themeList= new ArrayList<>();

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
        /*spinnerArray =  new ArrayList<>();
        spinnerArray.add(new Theme("", "hello", "TropLol", 5));
        spinnerArray.add(new Theme("", "FRIENDS", "TropLol", 5));
        spinnerArray.add(new Theme("", "COMMUNITY", "TropLol", 5));*/
        updateListSpinner();
        CustomAdapter adapter = new CustomAdapter(this,
                R.layout.spinner_item_layout_resource,
                R.id.textView_item_name,
                themeList);
        mThemeInput.setThreshold(1);
        mThemeInput.setAdapter(adapter);
        //adapter.getFilter().filter(null);


        Intent intent = getIntent();
        if (intent.hasExtra(CHOSEN_THEME)) {
            mTheme = intent.getParcelableExtra(CHOSEN_THEME);
            /*mThemeInput.setSelection(getSpinnerIndex(mThemeInput,mTheme));
            System.out.println(getSpinnerIndex(mThemeInput,mTheme));*/
            mThemeInput.setText(mTheme.getTitle());
            questionsDisplay(mTheme);
        }

        mThemeInput.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                mThemeInput.showDropDown();
                return false;
            }
        });

        mThemeInput.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            @Override
            public void onItemClick(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //mThemeName = mThemeInput.getSelectedItem().toString();
                mTheme =(Theme) themeList.get(position);
                mEnterBtn.setEnabled(mTheme.getTitle().length() != 0);
            }


        });
        mThemeInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateListSpinner();
                for(Theme i : themeList){
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
                if(isInArray(mThemeInput.getText().toString())){
                    //adapter.notifyDataSetChanged();
                    questionsDisplay(mTheme);
                    mEnterBtn.setEnabled(false);
                }
                else {
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

    private void updateListSpinner(){
        AndroidNetworking.get(GlobalVariable.API_URL+"/api/theme")
                .setTag("getAllThemes")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("themes")) {
                            try {
                                JSONArray themes = response.getJSONArray("themes");
                                themeList.clear();
                                for (int i = 0 ; i < themes.length(); i++) {
                                    JSONObject theme = themes.getJSONObject(i);
                                    themeList.add(new Theme(GlobalVariable.API_URL + theme.getString("imageUrl"), theme.getString("title"), theme.getString("description"),theme.getInt("nbQuestion")));

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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void questionsDisplay(Theme theme){
        mThemeQuestionsListContainer.removeAllViews();
        for(int i=1;i<=theme.getQuestionNB();i++){
            Button myButton = new Button(CreateTheme.this);
            myButton.setLayoutParams(btnParams);
            myButton.setText("test");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                myButton.setTextColor(getColor(R.color.colorBlack));
            }
            myButton.setBackground(getDrawable(R.drawable.container_answer_button_correct));

            mThemeQuestionsListContainer.addView(myButton);
        }
    }

    public boolean isInArray(String s){
        for (Theme t : themeList){
            if(s.equals(t.getTitle())){
                return true;
            }
        }
        return false;
    }

}


