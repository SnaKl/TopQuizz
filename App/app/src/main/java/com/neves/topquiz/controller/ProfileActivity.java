package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;
import com.neves.topquiz.model.User;

public class ProfileActivity extends AppCompatActivity {

    private User mUser;
    private ShapeableImageView mProfPic;
    private TextView mPointsNumber;
    private TextView mUsername;
    private TextView mMail;
    private TextView mPassword;
    private ImageButton mEditBtn;
    private static final String USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }

        mProfPic = findViewById(R.id.profile_picture_siv);
        mPointsNumber = findViewById(R.id.profile_nbPoints_tv);
        mUsername = findViewById(R.id.profile_username_input);
        mMail = findViewById(R.id.profile_mail_input);
        mPassword = findViewById(R.id.profile_password_input);
        mEditBtn = findViewById(R.id.profile_edit_btn);


        mPointsNumber.setText(mUser.getScore().getPoints() * 100 + "");
        mUsername.setText(mUser.getNickname());
        mMail.setText(mUser.getEmail());
        mPassword.setText("●●●●●●●●●●●");

        new DownLoadImageTask(mProfPic).execute(mUser.getAvatar());


        mEditBtn.setOnClickListener(v -> {
            Intent EditAccountIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            EditAccountIntent.putExtra(USER, mUser);
            startActivity(EditAccountIntent);
            finish();
        });

    }
}

