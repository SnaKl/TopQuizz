package com.neves.topquiz.controller;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neves.topquiz.model.Theme;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.neves.topquiz.R;

public class CreateQuestion extends AppCompatActivity {
    private EditText mQuestionInput;
    private EditText mAnswer1Input;
    private EditText mAnswer2Input;
    private EditText mAnswer3Input;
    private EditText mAnswer4Input;
    private ImageButton mEditImgBtn;
    private Button mSubmitQuestion;
    private LinearLayout mThemeNewQuestionContainer;
    private LayoutInflater flater;
    private static final String CHOSEN_THEME = "CHOSEN_THEME";
    private Theme mTheme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent.hasExtra(CHOSEN_THEME)) {
            mTheme = intent.getParcelableExtra(CHOSEN_THEME);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
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
                Intent CreateThemeActivity = new Intent(CreateQuestion.this, CreateTheme.class);
                CreateThemeActivity.putExtra("ADDQST", "hello");
                //addQst to ThemeDB
                CreateThemeActivity.putExtra(CHOSEN_THEME,mTheme);
                startActivity(CreateThemeActivity);
                //onCreateView();
                finish();

                
            }
        });

        mEditImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //afficher gallerie to put an image
            }
        });
    }

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private View onCreateView() {
        flater = CreateQuestion.this.getLayoutInflater();
        View rowView = flater.inflate(R.layout.activity_create_theme, null,true);

        Button btn = (Button) rowView.findViewById(R.id.create_theme_question1_btn);
        btn.setText("new");

                *//*return rowView;
                LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(CreateQuestion.LAYOUT_INFLATER_SERVICE);
                View vi = inflater.inflate(R.layout.activity_create_theme, null); //log.xml is your file.
                Button btn = (Button)vi.findViewById(R.id.create_theme_question1_btn);
                btn.setText("new");
                //findViewById(R.id.create_theme_question1_btn).setVisibility(View.GONE);
                //Button myButton = new Button(CreateTheme.class);*//*
        btn.setLayoutParams(findViewById(R.id.create_theme_question1_btn).getLayoutParams());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btn.setTextColor(getColor(R.color.colorBlack));
        }
        btn.setBackground(getDrawable(R.drawable.container_answer_button_correct));

        mThemeNewQuestionContainer.addView(btn);
        return rowView;
    }*/

}
