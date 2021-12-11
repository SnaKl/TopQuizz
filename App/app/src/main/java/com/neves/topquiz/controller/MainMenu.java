package com.neves.topquiz.controller;
import com.neves.topquiz.R;
import com.neves.topquiz.model.User;

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
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    private LinearLayout mChooseTheme;
    private LinearLayout mMyAccount;
    private LinearLayout mLeaderboard;
    private LinearLayout mCreateQuiz;
    private LinearLayout mValidateQuiz;
    public static final String USER = "USER";
    private static final String MODE = "MODE";
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }

        mChooseTheme= findViewById(R.id.main_menu_chooseTheme_btn);
        mMyAccount = findViewById(R.id.main_menu_account_btn);
        mLeaderboard = findViewById(R.id.main_menu_leaderboard_btn);
        mCreateQuiz = findViewById(R.id.main_menu_createQuiz_btn);
        mValidateQuiz = findViewById(R.id.main_menu_validateQuiz_btn);

        mChooseTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChooseTheme = new Intent(MainMenu.this, Themes.class);
                ChooseTheme.putExtra(USER, mUser);
                ChooseTheme.putExtra(MODE,"GAME");
                startActivity(ChooseTheme);
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
                Intent Leaderboard = new Intent(MainMenu.this, LeaderboardGeneral.class);
                startActivity(Leaderboard);
            }
        });

        mCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyAccount = new Intent(MainMenu.this, CreateTheme.class);
                startActivity(MyAccount);
            }
        });

        mValidateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ValidateQuiz = new Intent(MainMenu.this, Themes.class);
                ValidateQuiz.putExtra(USER, mUser);
                ValidateQuiz.putExtra(MODE,"VALIDATE_THEME");
                startActivity(ValidateQuiz);
            }
        });
    }
}
