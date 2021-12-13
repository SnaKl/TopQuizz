package com.neves.topquiz.controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;
import com.neves.topquiz.model.User;

public class Profile extends AppCompatActivity {
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

        mProfPic  = (ShapeableImageView) findViewById(R.id.profile_picture_siv);
        mPointsNumber = (TextView) findViewById(R.id.profile_nbPoints_tv);
        mUsername = (TextView) findViewById(R.id.profile_username_input);
        mMail = (TextView) findViewById(R.id.profile_mail_input);
        mPassword = (TextView) findViewById(R.id.profile_password_input);
        mEditBtn = (ImageButton) findViewById(R.id.profile_edit_btn);


        mPointsNumber.setText(mUser.getScore().getPoints()*100+"");
        mUsername.setText(mUser.getNickname());
        mMail.setText(mUser.getEmail());
        mPassword.setText("●●●●●●●●●●●");

        new DownLoadImageTask(mProfPic).execute(mUser.getAvatar());


        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditAccountIntent = new Intent(Profile.this, EditProfile.class);
                EditAccountIntent.putExtra(USER, mUser);
                startActivity(EditAccountIntent);
                finish();
            }
        });

    }
}

