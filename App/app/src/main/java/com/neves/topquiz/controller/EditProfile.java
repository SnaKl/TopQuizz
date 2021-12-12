package com.neves.topquiz.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.GlobalVariable;
import com.neves.topquiz.R;
import com.neves.topquiz.model.Theme;
import com.neves.topquiz.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {
    private ShapeableImageView mProfPic;
    private EditText mUsername;
    private EditText mMail;
    private EditText mConfirmMail;
    private EditText mConfirmPassword;
    private EditText mPassword;
    private Button mValidateBtn;
    private ImageButton mEditBtn;
    int SELECT_PICTURE = 200;
    private static final String USER = "USER";
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }

        mProfPic  = (ShapeableImageView) findViewById(R.id.profile_picture_siv);
        mUsername = (EditText) findViewById(R.id.profile_edit_username_input);
        mMail = (EditText) findViewById(R.id.profile_edit_mail_input);
        mConfirmMail = (EditText) findViewById(R.id.profile_edit_confirmMail_input);
        mPassword = (EditText) findViewById(R.id.profile_edit_password_input);
        mConfirmPassword = (EditText) findViewById(R.id.profile_edit_confirmPassword_input);
        mValidateBtn = (Button) findViewById(R.id.edit_profile_validate);
        mEditBtn = (ImageButton) findViewById(R.id.profile_edit_pictureEdit_btn);

        mUsername.setText(mUser.getNickname());
        mMail.setText(mUser.getEmail());
        mConfirmMail.setText(mUser.getEmail());
        mPassword.setText("●●●●●●●●●●●");
        mConfirmPassword.setText("●●●●●●●●●●●");
        new DownLoadImageTask(mProfPic).execute(mUser.getAvatar());

        mValidateBtn.setOnClickListener(v -> {
            Log.d("test button", "BUTTON CLICK");
            String username = mUsername.getText().toString();
            String mail = mMail.getText().toString();
            String confirmMail = mMail.getText().toString();
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmPassword.getText().toString();
            // GET USER NAME

            if(username.length()==0 || mail.length()==0 || confirmMail.length()==0 || password.length()==0 || confirmPassword.length() == 0){
                Toast.makeText(this, getString(R.string.ensureInput), Toast.LENGTH_SHORT).show();
          /*  }else if(!mail.equals(confirmMail)){
                Toast.makeText(this, getString(R.string.ensureMail), Toast.LENGTH_SHORT).show();
            }else if(!password.equals(confirmPassword)){
                Toast.makeText(this, getString(R.string.ensurePassword), Toast.LENGTH_SHORT).show();
            }else if(!checkMailRequirements(mail)){
                Toast.makeText(this, getString(R.string.ensureMailRequirements), Toast.LENGTH_SHORT).show();
            }else if(password.length()<4 || password.length()>30){
                Toast.makeText(this, getString(R.string.ensurePasswordLength), Toast.LENGTH_SHORT).show();
            }else if(!checkPassWordRequirements(password)){
                Toast.makeText(this, getString(R.string.ensurePasswordRequirements), Toast.LENGTH_SHORT).show();*/
            }else{
                //MODIFIER LE USER AVEC LES INFOS
                //selectionne image gallerie
                AndroidNetworking.put(GlobalVariable.API_URL+"/api/user/")
                        .addHeaders("Authorization", "Bearer " + mUser.getJwtToken())
                        .addBodyParameter("email", mMail.getText().toString())
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                Log.d("in respnse ", "WE'RE IN");
                                try {
                                    Log.d("email", response.getJSONObject("user").getString("nickname"));
                                } catch (JSONException jsonException) {
                                    jsonException.printStackTrace();
                                }

                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Log.d("In error", error.getErrorBody());
                            }
                        });
                Intent ProfileIntent = new Intent(EditProfile.this, Profile.class);
                ProfileIntent.putExtra(USER,mUser);
                startActivity(ProfileIntent);
                finish();

            }
        });

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
            }
        });

    }

    void chooseImg(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);

    }
    private Boolean checkPassWordRequirements(String password){
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,20}$");
        Matcher m = p.matcher(password);
        return m.find();
    }

    private Boolean checkMailRequirements(String mail){
        Pattern p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mail);
        return m.find();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    mProfPic.setImageURI(selectedImageUri);
                    AndroidNetworking.put(GlobalVariable.API_URL+"/api/avatar")
                            .addBodyParameter("avatar", mProfPic.getTransitionName())
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // do anything with response

                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                }
                            });
                }
            }
        }
    }
}
