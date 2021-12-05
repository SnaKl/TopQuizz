package com.neves.topquiz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.neves.topquiz.R;

public class EditProfile extends AppCompatActivity {
    private EditText mUsername;
    private EditText mMail;
    private EditText mConfirmMail;
    private EditText mConfirmPassword;
    private EditText mPassword;
    private Button mValidateBtn;
    private ImageButton mEditBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        mUsername = (EditText) findViewById(R.id.profile_edit_username_input);
        mMail = (EditText) findViewById(R.id.profile_edit_mail_input);
        mConfirmMail = (EditText) findViewById(R.id.profile_edit_confirmMail_input);
        mPassword = (EditText) findViewById(R.id.profile_edit_password_input);
        mConfirmPassword = (EditText) findViewById(R.id.profile_edit_confirmPassword_input);
        mValidateBtn = (Button) findViewById(R.id.edit_profile_validate);
        mEditBtn = (ImageButton) findViewById(R.id.profile_edit_pictureEdit_btn);

        //UserDB userDB = new UserDB(Parametres.this.getContext());
        //mUsername.setText(userDB.getAll().getString(0));
        //mMail.setText(userDB.getAll().getString(0));
        //mConfirmMail.setText(userDB.getAll().getString(0));
        //mPassword.setText(userDB.getAll().getString(0));
        //mConfirmPassword.setText(userDB.getAll().getString(0));

        mValidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update bdd mango
                Intent ChooseThemeIntent = new Intent(EditProfile.this, Profile.class);
                startActivity(ChooseThemeIntent);
                finish();
            }
        });

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectionne image gallerie
                //mettre à jour bdd
            }
        });

    }
}
