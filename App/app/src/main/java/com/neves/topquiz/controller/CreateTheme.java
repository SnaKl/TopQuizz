package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;

import java.util.ArrayList;
import java.util.List;

public class CreateTheme extends AppCompatActivity {
    private Spinner mThemeInput;
    //private dropdown mThemeInput;
    private Button mAddQuestionBtn;
    private Button mSubmitQuiz;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_theme);
        mThemeInput = findViewById(R.id.create_theme_themeTitle_input);
        mAddQuestionBtn = findViewById(R.id.create_theme_addQuestion_btn);
        mSubmitQuiz = findViewById(R.id.create_theme_submitQuiz_btn);

        /*List<String> spinnerArray =  new ArrayList<String>();
        //for (int i=0; i<nbThemes; i++){
        //
        // }

        spinnerArray.add("item1");
        spinnerArray.add("item2");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mThemeInput.setAdapter(adapter);*/

        //mettre liste des thèmes existant dans le dropdown + champ vide qui permet d'en créer un
        //faire pleins de qst et submit OU submit après chaque qst??????????
        mAddQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChooseTheme = new Intent(CreateTheme.this, CreateQuestion.class);
                startActivity(ChooseTheme);
                //+faut que ça modifie le xml
            }
        });

        mSubmitQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mettre à jour la bdd
                finish();
            }
        });
    }

}
