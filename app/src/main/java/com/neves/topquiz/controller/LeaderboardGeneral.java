package com.neves.topquiz.controller;
import com.neves.topquiz.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardGeneral extends AppCompatActivity {
    private Button generalBtn;
    private Button themeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_general);

        generalBtn = (Button) findViewById(R.id.leaderboard_general_btn);
        themeBtn = (Button) findViewById(R.id.leaderboard_themes_btn);

//        generalBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent ChooseThemeIntent = ;
//                startActivity(new Intent(LeaderboardGeneral.this, LeaderboardGeneral.class));
//            }
//        });

        themeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent ChooseThemeIntent = ;
                startActivity(new Intent(LeaderboardGeneral.this, LeaderboardTheme.class));
                finish();
            }
        });
    }
}
