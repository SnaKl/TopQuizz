package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
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

public class CreateTheme extends AppCompatActivity {
    private static final String BUNDLE_STATE_QUESTION_BANK = "BUNDLE_STATE_QUESTION_BANK";
    private static final String BUNDLE_STATE_THEME_NAME = "BUNDLE_STATE_THEME_NAME" ;
    private static final String CHOSEN_THEME = "CHOSEN_THEME";
    private Spinner mThemeInput;
    //private List<Theme> themeList;
    private Button mAddQuestionBtn;
    private Button mSubmitQuiz;
    private Button mEnterBtn;
    private LinearLayout mThemeQuestionsListContainer;
    private QuestionBank mQuestionBank;
    private String mThemeName;
    private ViewGroup.LayoutParams btnParams;
    private Theme mTheme;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_theme);
        findViewById(R.id.create_theme_question1_btn).setVisibility(View.GONE);
        mThemeInput = findViewById(R.id.create_theme_themeTitle_input);
        mAddQuestionBtn = findViewById(R.id.create_theme_addQuestion_btn);
        mSubmitQuiz = findViewById(R.id.create_theme_submitQuiz_btn);
        mEnterBtn = findViewById(R.id.create_theme_submitTheme_btn);
        //themeList = ThemeDB.getTheme();
        btnParams = findViewById(R.id.create_theme_question1_btn).getLayoutParams();
        List<Theme> spinnerArray =  new ArrayList<>();

        spinnerArray.add(new Theme("", "THE OFFICE", "TropLol", 5));
        spinnerArray.add(new Theme("", "FRIENDS", "TropLol", 5));
        spinnerArray.add(new Theme("", "COMMUNITY", "TropLol", 5));
        /*spinnerArray.add("The Big Bang Theory");
        spinnerArray.add("Community");
        spinnerArray.add("Abarna");*/

        CustomAdapter adapter = new CustomAdapter(CreateTheme.this,
                R.layout.spinner_item_layout_resource,
                R.id.textView_item_name,
                spinnerArray);
        mThemeInput.setAdapter(adapter);

        mThemeQuestionsListContainer = (LinearLayout) findViewById(R.id.create_theme_questionsList_lt);

        Intent intent = getIntent();
        if (intent.hasExtra(CHOSEN_THEME)) {
            mTheme = intent.getParcelableExtra(CHOSEN_THEME);
            mThemeInput.setSelection(getSpinnerIndex(mThemeInput,mTheme));
            System.out.println(getSpinnerIndex(mThemeInput,mTheme));
        }
        if (intent.hasExtra("ADDQST")) {
            findViewById(R.id.create_theme_question1_btn).setVisibility(View.GONE);
            Button myButton = new Button(CreateTheme.this);
            myButton.setLayoutParams(findViewById(R.id.create_theme_question1_btn).getLayoutParams());
            myButton.setText("new");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                myButton.setTextColor(getColor(R.color.colorBlack));
            }
            myButton.setBackground(getDrawable(R.drawable.container_answer_button_correct));

            mThemeQuestionsListContainer.addView(myButton);
        }
        mThemeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //mThemeName = mThemeInput.getSelectedItem().toString();
                mTheme =(Theme) mThemeInput.getSelectedItem();
                mEnterBtn.setEnabled(mThemeName.length() != 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        mEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionsDisplay(mThemeName);
                mEnterBtn.setEnabled(false);

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

        if (savedInstanceState != null) {
            mThemeName = savedInstanceState.getString(BUNDLE_STATE_THEME_NAME);
            System.out.println(mThemeName+"!!!!!!!!!!!!!");
            questionsDisplay(mThemeName);
        } else {
            mThemeName = spinnerArray.get(0).getTitle();
        }


    }

    private int getSpinnerIndex(Spinner spinner, Theme myTheme){
         for (int i=0;i<spinner.getCount();i++){
             Theme testedTheme = (Theme) spinner.getItemAtPosition(i);
             if (testedTheme.getTitle().equalsIgnoreCase(myTheme.getTitle())){
                 return i;
             }
         }
         return 0;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_STATE_THEME_NAME, mThemeName);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void questionsDisplay(String name){

        // a = mThemeInput.getSelectedItem().toString();
        //replace 2 with a.size()
        if(name.equals("item2")){
            mThemeQuestionsListContainer.removeAllViews();
            //ThemeDB mThemeDB = new ThemeDB(name);
            //mThemeDB.getQstNb() and mThemeDB.getQstBank()
            for(int i=0;i<2;i++){
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
        if(name.equals("item1")){

            mThemeQuestionsListContainer.removeAllViews();

            //ThemeDB mThemeDB = new ThemeDB(name);
            //mThemeDB.getQstNb() and mThemeDB.getQstBank()
            for(int i=0;i<3;i++){
                Button myButton = new Button(CreateTheme.this);
                myButton.setLayoutParams(btnParams);
                myButton.setText("you");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    myButton.setTextColor(getColor(R.color.colorBlack));
                }
                myButton.setBackground(getDrawable(R.drawable.container_answer_button_correct));
                mThemeQuestionsListContainer.addView(myButton);
            }
        }
    }
}


