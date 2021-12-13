package com.neves.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.neves.topquiz.GlobalVariable;
import com.neves.topquiz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mMail;
    private EditText mConfirmMail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mCreateAccountBtn;
    private SharedPreferences mPreferences;
    private static final String ACCOUNT_CREATED = "ACCOUNT_CREATED";
    private final CreateAccountActivity mCreateAccountActivity = this;

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
            String username = mUsername.getText().toString();
            String mail = mMail.getText().toString();
            String confirmMail = mMail.getText().toString();
            String password = mPassword.getText().toString(); // Length of 4, one number, one upper, one special character
            String confirmPassword = mConfirmPassword.getText().toString();

            if (username.length() == 0 || mail.length() == 0 || confirmMail.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
                Toast.makeText(this, getString(R.string.ensureInput), Toast.LENGTH_SHORT).show();
            } else if (!mail.equals(confirmMail)) {
                Toast.makeText(this, getString(R.string.ensureMail), Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, getString(R.string.ensurePassword), Toast.LENGTH_SHORT).show();
            } else if (!checkMailRequirements(mail)) {
                Toast.makeText(this, getString(R.string.ensureMailRequirements), Toast.LENGTH_SHORT).show();
            } else if (password.length() < 4 || password.length() > 30) {
                Toast.makeText(this, getString(R.string.ensurePasswordLength), Toast.LENGTH_SHORT).show();
            } else if (!checkPassWordRequirements(password)) {
                Toast.makeText(this, getString(R.string.ensurePasswordRequirements), Toast.LENGTH_SHORT).show();
            } else {
                // Create user account with infos
                AndroidNetworking.post(GlobalVariable.API_URL + "/api/user")
                        .addBodyParameter("nickname", username)
                        .addBodyParameter("email", mail)
                        .addBodyParameter("password", password)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                goToMainActivity();
                            }

                            @Override
                            public void onError(ANError error) {
                                try {
                                    if (error.getErrorBody() != null) {
                                        JSONObject errorJsonObject = new JSONObject(error.getErrorBody());
                                        if (errorJsonObject.has("error")) {
                                            Toast.makeText(mCreateAccountActivity, getString(R.string.invalidCredentials), Toast.LENGTH_SHORT).show();
                                        } else if (errorJsonObject.has("errors")) {
                                            JSONObject errors = errorJsonObject.getJSONObject("errors");

                                            if (errors.has("password")) {
                                                Toast.makeText(mCreateAccountActivity, getString(R.string.ensurePasswordRequirements), Toast.LENGTH_SHORT).show();
                                            } else if (errors.has("nickname")) {
                                                Toast.makeText(mCreateAccountActivity, getString(R.string.ensureUsername), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        goToMainActivity();
                                    }
                                } catch (JSONException err) {
                                    Log.d("Error", err.toString());
                                }
                            }
                        });
            }
        });
    }

    private void goToMainActivity() {
        Intent MainActivity = new Intent(CreateAccountActivity.this, MainActivity.class);
        MainActivity.putExtra(ACCOUNT_CREATED, true);
        startActivity(MainActivity);
        finish();
    }

    private Boolean checkPassWordRequirements(String password) {
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,20}$");
        Matcher m = p.matcher(password);
        return m.find();
    }

    private Boolean checkMailRequirements(String mail) {
        Pattern p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mail);
        return m.find();
    }
}

