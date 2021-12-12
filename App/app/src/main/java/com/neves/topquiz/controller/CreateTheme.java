package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_theme);
        findViewById(R.id.create_theme_question1_btn).setVisibility(View.GONE);
        mThemeInput = findViewById(R.id.autoCompleteTextView);
        mAddQuestionBtn = findViewById(R.id.create_theme_addQuestion_btn);
        mSubmitQuiz = findViewById(R.id.create_theme_submitQuiz_btn);
        mEnterBtn = findViewById(R.id.create_theme_submitTheme_btn);
        //themeList = ThemeDB.getTheme();
        btnParams = findViewById(R.id.create_theme_question1_btn).getLayoutParams();
        spinnerArray =  new ArrayList<>();
        spinnerArray.add(new Theme("", "hello", "TropLol", 5));
        spinnerArray.add(new Theme("", "FRIENDS", "TropLol", 5));
        spinnerArray.add(new Theme("", "COMMUNITY", "TropLol", 5));

        CustomAdapter adapter = new CustomAdapter(this,
                R.layout.spinner_item_layout_resource,
                R.id.textView_item_name,
                spinnerArray);
        mThemeInput.setThreshold(1);
        mThemeInput.setAdapter(adapter);


        mThemeQuestionsListContainer = (LinearLayout) findViewById(R.id.create_theme_questionsList_lt);
        if (savedInstanceState != null) {
            mThemeName = savedInstanceState.getString(BUNDLE_STATE_THEME_NAME);
            System.out.println(mThemeName+"!!!!!!!!!!!!!");
            questionsDisplay(mTheme);
        } else {
            mThemeName = spinnerArray.get(0).getTitle();
        }
        Intent intent = getIntent();
        if (intent.hasExtra(CHOSEN_THEME)) {
            mTheme = intent.getParcelableExtra(CHOSEN_THEME);
            /*mThemeInput.setSelection(getSpinnerIndex(mThemeInput,mTheme));
            System.out.println(getSpinnerIndex(mThemeInput,mTheme));*/
            mThemeInput.setText(mTheme.getTitle());
            questionsDisplay(mTheme);
        }

        mThemeInput.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            @Override
            public void onItemClick(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //mThemeName = mThemeInput.getSelectedItem().toString();
                mTheme =(Theme) spinnerArray.get(position);
                System.out.println(mTheme.getTitle());
                System.out.println("mTheme.getTitle()!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                mEnterBtn.setEnabled(mTheme.getTitle().length() != 0);
            }


        });
        mThemeInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
                    spinnerArray.add(newTheme);
                    for (Theme t : spinnerArray){
                        System.out.println(t.getTitle());
                    }
                    adapter.updateList(spinnerArray);
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
    private void questionsDisplay(Theme theme){

        // a = mThemeInput.getSelectedItem().toString();
        //replace 2 with a.size()
        mThemeQuestionsListContainer.removeAllViews();
        //ThemeDB mThemeDB = new ThemeDB(name);
        //mThemeDB.getQstNb() and mThemeDB.getQstBank()
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
        for (Theme t : spinnerArray){
            if(s.equals(t.getTitle())){
                return true;
            }
        }
        return false;
    }

}


