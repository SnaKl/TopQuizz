package com.neves.topquiz.controller;
import com.neves.topquiz.R;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import static java.lang.System.out;

public class CreateAccount extends AppCompatActivity{

    private EditText mUsername;
    private EditText mMail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mCreateAccountBtn;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mPreferences = getPreferences(MODE_PRIVATE);

        mUsername = findViewById(R.id.create_account_username_input);
        mMail = findViewById(R.id.create_account_mail_input);
        mPassword = findViewById(R.id.create_account_password_input);
        mConfirmPassword = findViewById(R.id.create_account_confirmPassword_input);
        mCreateAccountBtn = findViewById(R.id.create_account_validate_btn);

        String username = mUsername.getText().toString();
        String mail = mMail.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();

        mConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.equals(confirmPassword)){
                    mCreateAccountBtn.setEnabled(s.toString().length() != 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCreateAccountBtn.setOnClickListener(v -> {
            //créer un User et lui rentrer les infos

            /*User mUser = new User(pseudoS, DDNS, genreS, permisS, poidsS, tailleS, numS);
            UserDB userDB = new UserDB(Formulaire.this);
            boolean success = userDB.addOne(mUser);*/
            Intent Suite = new Intent(CreateAccount.this, MainActivity.class);
            startActivity(Suite);
            finish();
        });
    }
}

