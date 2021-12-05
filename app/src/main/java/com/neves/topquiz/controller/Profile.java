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

public class Profile extends AppCompatActivity {
    private ShapeableImageView mProfPic;
    private TextView mPointsNumber;
    private TextView mUsername;
    private TextView mMail;
    private TextView mPassword;
    private ImageButton mEditBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mProfPic  = (ShapeableImageView) findViewById(R.id.profile_picture_siv);
        mPointsNumber = (TextView) findViewById(R.id.profile_nbPoints_tv);
        mUsername = (TextView) findViewById(R.id.profile_username_input);
        mMail = (TextView) findViewById(R.id.profile_mail_input);
        mPassword = (TextView) findViewById(R.id.profile_password_input);
        mEditBtn = (ImageButton) findViewById(R.id.profile_edit_btn);

        //UserDB userDB = new UserDB(Parametres.this.getContext());
        //mUsername.setText(userDB.getAll().getString(0));
        //mMail.setText(userDB.getAll().getString(0));
        //mPassword.setText(userDB.getAll().getString(0));

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChooseThemeIntent = new Intent(Profile.this, EditProfile.class);
                startActivity(ChooseThemeIntent);
                finish();
            }
        });

    }
}

