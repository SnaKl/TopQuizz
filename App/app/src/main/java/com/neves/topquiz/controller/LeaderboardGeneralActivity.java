package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;

public class LeaderboardGeneralActivity extends AppCompatActivity {
    private Button mGeneralBtn;
    private Button mThemeBtn;
    private ShapeableImageView mSilver;
    private ShapeableImageView mGold;
    private ShapeableImageView mBronze;
    private ShapeableImageView mProfPic1;
    private ShapeableImageView mProfPic2;
    private ShapeableImageView mProfPic3;
    private ShapeableImageView mProfPic4;
    private ShapeableImageView mProfPic5;
    private TextView mPlayer1;
    private TextView mPlayer2;
    private TextView mPlayer3;
    private TextView mPlayer4;
    private TextView mPlayer5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_general);

        mGeneralBtn = findViewById(R.id.leaderboard_general_btn);
        mThemeBtn = findViewById(R.id.leaderboard_themes_btn);
        mSilver = findViewById(R.id.leaderboard_secondWinner_profilePic_siv);
        mGold = findViewById(R.id.leaderboard_firstWinner_profilePic_siv);
        mBronze = findViewById(R.id.leaderboard_thirdWinner_profilePic_siv);
        mProfPic1 = findViewById(R.id.leaderboard_profilePicture1_siv);
        mProfPic2 = findViewById(R.id.leaderboard_profilePicture2_siv);
        mProfPic3 = findViewById(R.id.leaderboard_profilePicture3_siv);
        mProfPic4 = findViewById(R.id.leaderboard_profilePicture4_siv);
        mProfPic5 = findViewById(R.id.leaderboard_profilePicture5_siv);
        mPlayer1 = findViewById(R.id.leaderboard_name1_tv);
        mPlayer2 = findViewById(R.id.leaderboard_name2_tv);
        mPlayer3 = findViewById(R.id.leaderboard_name3_tv);
        mPlayer4 = findViewById(R.id.leaderboard_name4_tv);
        mPlayer5 = findViewById(R.id.leaderboard_name5_tv);

        mThemeBtn.setOnClickListener(v -> {
            startActivity(new Intent(LeaderboardGeneralActivity.this, LeaderboardThemeActivity.class));
            finish();
        });
    }
}
