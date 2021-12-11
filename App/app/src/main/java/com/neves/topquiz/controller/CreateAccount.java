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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import static java.lang.System.out;

public class CreateAccount extends AppCompatActivity{

    private EditText mUsername;
    private EditText mMail;
    private EditText mConfirmMail;
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
        mConfirmMail = findViewById(R.id.create_account_confirmMail_input);
        mPassword = findViewById(R.id.create_account_password_input);
        mConfirmPassword = findViewById(R.id.create_account_confirmPassword_input);
        mCreateAccountBtn = findViewById(R.id.create_account_validate_btn);

        mCreateAccountBtn.setOnClickListener(v -> {
            //créer un User et lui rentrer les infos

            /*User mUser = new User(pseudoS, DDNS, genreS, permisS, poidsS, tailleS, numS);
            UserDB userDB = new UserDB(Formulaire.this);
            boolean success = userDB.addOne(mUser);*/
            String username = mUsername.getText().toString();
            String mail = mMail.getText().toString();
            String confirmMail = mMail.getText().toString();
            String password = mPassword.getText().toString(); //taille 4, un chiffre, une majusucule, un caractère spécial
            String confirmPassword = mConfirmPassword.getText().toString();

            if(username.length()==0 || mail.length()==0 || confirmMail.length()==0 || password.length()==0 || confirmPassword.length() == 0){
                Toast.makeText(this, getString(R.string.ensureInput), Toast.LENGTH_SHORT).show();
            }else if(!mail.equals(confirmMail)){
                Toast.makeText(this, getString(R.string.ensureMail), Toast.LENGTH_SHORT).show();
            }else if(!password.equals(confirmPassword)){
                Toast.makeText(this, getString(R.string.ensurePassword), Toast.LENGTH_SHORT).show();
            }else if(password.length()<4){
                Toast.makeText(this, getString(R.string.ensurePasswordLength), Toast.LENGTH_SHORT).show();
            }else{
                //CREER UN COMPTE USER AVEC LES INFOS
                Intent Suite = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(Suite);
                finish();
            }
        });
    }
}

