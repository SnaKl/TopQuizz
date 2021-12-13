package com.neves.topquiz.controller;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.neves.topquiz.GlobalVariable;
import com.neves.topquiz.R;
import com.neves.topquiz.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity {
    private LinearLayout mChooseTheme;
    private LinearLayout mMyAccount;
    private LinearLayout mLeaderboard;
    private LinearLayout mCreateQuiz;
    private LinearLayout mValidateQuiz;
    public static final String USER = "USER";
    private static final String MODE = "MODE";
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
            getUser(mUser.getJwtToken());
        }

        mChooseTheme= findViewById(R.id.main_menu_chooseTheme_btn);
        mMyAccount = findViewById(R.id.main_menu_account_btn);
        mLeaderboard = findViewById(R.id.main_menu_leaderboard_btn);
        mCreateQuiz = findViewById(R.id.main_menu_createQuiz_btn);
        mValidateQuiz = findViewById(R.id.main_menu_validateQuiz_btn);

        mChooseTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChooseTheme = new Intent(MainMenu.this, Themes.class);
                ChooseTheme.putExtra(USER, mUser);
                ChooseTheme.putExtra(MODE,"GAME");
                startActivity(ChooseTheme);
            }
        });

        mMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyAccount = new Intent(MainMenu.this, Profile.class);
                MyAccount.putExtra(USER, mUser);
                startActivity(MyAccount);
            }
        });

        mLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Leaderboard = new Intent(MainMenu.this, LeaderboardGeneral.class);
                startActivity(Leaderboard);
            }
        });

        mCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CreateTheme = new Intent(MainMenu.this, CreateTheme.class);
                CreateTheme.putExtra(USER,mUser);
                startActivity(CreateTheme);
            }
        });

        mValidateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ValidateQuiz = new Intent(MainMenu.this, Themes.class);
                ValidateQuiz.putExtra(USER, mUser);
                ValidateQuiz.putExtra(MODE,"VALIDATE_THEME");
                startActivity(ValidateQuiz);
            }
        });
    }
    private void getUser(String token) {
        System.out.println(token);
        AndroidNetworking.get(GlobalVariable.API_URL + "/api/user/")
                .addHeaders("Authorization", ("Bearer " + token).trim())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject user = response.getJSONObject("user");
                            String email = user.getString("email");
                            String avatar = user.getString("avatar");
                            if(email!=null){
                                mUser.setEmail(email);
                            }
                            if(avatar!=null){
                                mUser.setAvatar(avatar);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d("themesError", error.toString());
                    }
                });
    }

}
