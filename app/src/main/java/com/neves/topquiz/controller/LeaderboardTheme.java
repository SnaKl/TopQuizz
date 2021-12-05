package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;

public class LeaderboardTheme extends AppCompatActivity {

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
        setContentView(R.layout.activity_leaderboard_theme);

        mGeneralBtn = (Button) findViewById(R.id.leaderboard_general_btn);
        mThemeBtn = (Button) findViewById(R.id.leaderboard_themes_btn);
        mSilver = (ShapeableImageView) findViewById(R.id.leaderboard_secondWinner_profilePic_siv);
        mGold = (ShapeableImageView) findViewById(R.id.leaderboard_firstWinner_profilePic_siv);
        mBronze = (ShapeableImageView) findViewById(R.id.leaderboard_thirdWinner_profilePic_siv);
        mProfPic1 = (ShapeableImageView) findViewById(R.id.leaderboard_profilePicture1_siv);
        mProfPic2 = (ShapeableImageView) findViewById(R.id.leaderboard_profilePicture2_siv);
        mProfPic3 = (ShapeableImageView) findViewById(R.id.leaderboard_profilePicture3_siv);
        mProfPic4 = (ShapeableImageView) findViewById(R.id.leaderboard_profilePicture4_siv);
        mProfPic5 = (ShapeableImageView) findViewById(R.id.leaderboard_profilePicture5_siv);
        mPlayer1 = (TextView) findViewById(R.id.leaderboard_name1_tv);
        mPlayer2 = (TextView) findViewById(R.id.leaderboard_name2_tv);
        mPlayer3 = (TextView) findViewById(R.id.leaderboard_name3_tv);
        mPlayer4 = (TextView) findViewById(R.id.leaderboard_name4_tv);
        mPlayer5 = (TextView) findViewById(R.id.leaderboard_name5_tv);

        mGeneralBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent ChooseThemeIntent = ;
                startActivity(new Intent(LeaderboardTheme.this, LeaderboardGeneral.class));
                finish();
            }
        });

//        mThemeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent ChooseThemeIntent = ;
//                startActivity(new Intent(LeaderboardGeneral.this, LeaderboardTheme.class));
//                finish();
//            }
//        });
    }
}

