package com.neves.topquiz.controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.neves.topquiz.R;

public class Profile extends AppCompatActivity {
    private EditText mUsername;
    private EditText mMail;
    private EditText mPassword;
    private ImageButton mEditBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mUsername = (EditText) findViewById(R.id.profile_username_input);
        mMail = (EditText) findViewById(R.id.profile_mail_input);
        mPassword = (EditText) findViewById(R.id.profile_password_input);
        mEditBtn = (ImageButton) findViewById(R.id.profile_edit_btn);

        //UserDB userDB = new UserDB(Parametres.this.getContext());
        //mUsername.setText(userDB.getAll().getString(0));

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChooseThemeIntent = new Intent(Profile.this, Profile.class);
                startActivity(ChooseThemeIntent);
            }
        });

    }
}

