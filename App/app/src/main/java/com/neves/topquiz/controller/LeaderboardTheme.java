package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;
import com.neves.topquiz.model.Theme;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardTheme extends AppCompatActivity {

    private Button mGeneralBtn;
    private Button mThemeBtn;
    private Spinner mThemeLeaderboard;
    private ShapeableImageView mSilver;
    private ShapeableImageView mGold;
    private ShapeableImageView mBronze;
    private ShapeableImageView mProfPic1;
    private TextView mPlayer1;
    private Theme mTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_theme);
        mThemeLeaderboard = findViewById(R.id.leaderboard_themes_spnr);
        mGeneralBtn = (Button) findViewById(R.id.leaderboard_general_btn);
        mThemeBtn = (Button) findViewById(R.id.leaderboard_themes_btn);
        mSilver = (ShapeableImageView) findViewById(R.id.leaderboard_secondWinner_profilePic_siv);
        mGold = (ShapeableImageView) findViewById(R.id.leaderboard_firstWinner_profilePic_siv);
        mBronze = (ShapeableImageView) findViewById(R.id.leaderboard_thirdWinner_profilePic_siv);
        mProfPic1 = (ShapeableImageView) findViewById(R.id.leaderboard_profilePicture1_siv);
        mPlayer1 = (TextView) findViewById(R.id.leaderboard_name1_tv);

        List<Theme> spinnerArray =  new ArrayList<>();
        spinnerArray.add(new Theme("", "THE OFFICE", "TropLol", 5));
        spinnerArray.add(new Theme("", "FRIENDS", "TropLol", 5));
        spinnerArray.add(new Theme("", "COMMUNITY", "TropLol", 5));

        CustomAdapter adapter = new CustomAdapter(LeaderboardTheme.this,
                R.layout.spinner_item_layout_resource,
                R.id.textView_item_name,
                spinnerArray);
        mThemeLeaderboard.setAdapter(adapter);

        mThemeLeaderboard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTheme = (Theme) spinnerArray.get(position);
                System.out.println(mTheme.getTitle());
                System.out.println("mTheme.getTitle()!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mGeneralBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent ChooseThemeIntent = ;
                startActivity(new Intent(LeaderboardTheme.this, LeaderboardGeneral.class));
                finish();
            }
        });

    }
}

