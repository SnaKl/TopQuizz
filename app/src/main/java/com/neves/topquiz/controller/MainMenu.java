package com.neves.topquiz.controller;
import com.neves.topquiz.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    private ImageButton chooseTheme;
    private ImageButton mMyAccount;
    private ImageButton mLeaderboard;
    private ImageButton mCreateQuiz;
    private ImageButton mValidateQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        chooseTheme= (ImageButton) findViewById(R.id.main_menu_chooseTheme_btn);
        mMyAccount = (ImageButton) findViewById(R.id.main_menu_account_btn1);
        mLeaderboard = (ImageButton) findViewById(R.id.main_menu_leaderboard_btn);
        mCreateQuiz = (ImageButton) findViewById(R.id.main_menu_createQuiz_btn);
        mValidateQuiz = (ImageButton) findViewById(R.id.main_menu_validateQuiz_btn);

        chooseTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent ChooseThemeIntent = ;
                startActivity(new Intent(MainMenu.this, Profile.class));
            }
        });

        mMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyAccount = new Intent(MainMenu.this, Profile.class);
                startActivity(MyAccount);
            }
        });

        mLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Leaderboard = new Intent(MainMenu.this, Profile.class);
                startActivity(Leaderboard);
            }
        });

        mCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CreateQuiz = new Intent(MainMenu.this, Profile.class);
                startActivity(CreateQuiz);
            }
        });

        mValidateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ValidateQuiz = new Intent(MainMenu.this, Profile.class);
                startActivity(ValidateQuiz);
            }
        });
    }
}
