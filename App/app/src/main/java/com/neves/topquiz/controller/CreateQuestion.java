package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.neves.topquiz.R;

public class CreateQuestion extends AppCompatActivity {
    private EditText mQuestionInput;
    private EditText mAnswer1Input;
    private EditText mAnswer2Input;
    private EditText mAnswer3Input;
    private EditText mAnswer4Input;
    private ImageButton mEditImgBtn;
    //private Button mSubmitQuestion;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        mQuestionInput = findViewById(R.id.create_question_question_tv);
        mAnswer1Input = findViewById(R.id.create_question_answer1_input);
        mAnswer2Input = findViewById(R.id.create_question_answer2_input);
        mAnswer3Input = findViewById(R.id.create_question_answer3_input);
        mAnswer4Input = findViewById(R.id.create_question_answer4_input);
        mEditImgBtn = findViewById(R.id.create_question_editImage_btn);
        //mSubmitQuestion = findViewById(R.id.create_question_editImage_btn);

        /*mSubmitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mettre à jour la bdd, ajouter dans les questions à valider
                finish();
            }
        });*/

        mEditImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //afficher gallerie to put an image
            }
        });
    }

}
