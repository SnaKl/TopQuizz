package com.neves.topquiz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.neves.topquiz.GlobalVariable;
import com.neves.topquiz.R;
import com.neves.topquiz.model.Score;
import com.neves.topquiz.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    private TextView mGreetingText;
    private EditText mUsername;
    private EditText mPassword;
    private Button mConnectionBtn;
    private Button mAccountCreationBtn;
    private User mUser;
    private List<User> users;
    private SharedPreferences mPreferences;

    public static final String USER = "USER";
    public static final String USERS = "USERS";

    private MainActivity mainActivity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidNetworking.initialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        mPreferences = getPreferences(MODE_PRIVATE);
        mGreetingText = findViewById(R.id.login_greetingTxt_tv);

        mUsername = findViewById(R.id.login_username_input);
        mPassword = findViewById(R.id.login_password_input);

        mConnectionBtn = findViewById(R.id.login_connection_btn);

        mAccountCreationBtn = findViewById(R.id.login_createAccount_btn);



        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mConnectionBtn.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        mConnectionBtn.setOnClickListener(v -> {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.ensureInput), Toast.LENGTH_SHORT).show();
                return;
            }

            if(!checkPassWordRequirements(password)) {
                Toast.makeText(this, getString(R.string.ensurePasswordRequirements), Toast.LENGTH_SHORT).show();
                return;
            }

            AndroidNetworking.get("http://"+ GlobalVariable.API_URL +":"+GlobalVariable.API_PORT+"/api/auth/login")
                    .addQueryParameter("nickname", username)
                    .addQueryParameter("password", password)
                    .setTag("connect")
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                mUser = new User(username, response.getString("token"), "", "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mUser.setScore(new Score(null, 0));
                            Intent mainMenuActivity = new Intent(MainActivity.this, MainMenu.class);
                            mainMenuActivity.putExtra(USER, mUser);
                            startActivity(mainMenuActivity);
                            finish();
                        }

                        @Override
                        public void onError(ANError error) {
                            try {
                                JSONObject errorJsonObject = new JSONObject(error.getErrorBody());
                                if(errorJsonObject.has("error")){
                                    Toast.makeText(mainActivity, getString(R.string.invalidCredentials), Toast.LENGTH_SHORT).show();
                                }
                                else if(errorJsonObject.has("errors")){
                                    JSONObject errors =  errorJsonObject.getJSONObject("errors");

                                    if(errors.has("password")){
                                        Toast.makeText(mainActivity, getString(R.string.ensurePasswordRequirements), Toast.LENGTH_SHORT).show();
                                    }
                                    else if(errors.has("nickname")){
                                        Toast.makeText(mainActivity, getString(R.string.ensureUsername), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }catch (JSONException err){
                                Log.d("Error", err.toString());
                            }
                        }
                    });
        });

        mAccountCreationBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreateAccount.class));
            finish();
        });
    }

    /**
     * Instancie notre menu personnalisé (menu_general.xml) et remplace celui contenu par
     * activity_main.xml
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    /**
     * Défini l'action des différents "boutons" de la toolBar lors d'un clique
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_activity_main_drapeau:
                Toast.makeText(this, "Changement de langue", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_parametres:
                Toast.makeText(this, "Afficher vue paramètre", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            mUser = data.getParcelableExtra(GameActivity.USER);
            Gson gson = new Gson();
            mPreferences.edit().putString(USER, gson.toJson(mUser)).apply();
            mPreferences.edit().putString(USERS, gson.toJson(users)).apply();
            //greetUser();
        }
    }

    /**
     * Check requirment for password
     * @param password
     * @return
     */
    private Boolean checkPassWordRequirements(String password){
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,20}$");
        Matcher m = p.matcher(password);
        return m.find();
    }


}
