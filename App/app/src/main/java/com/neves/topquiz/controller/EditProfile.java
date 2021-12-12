package com.neves.topquiz.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.neves.topquiz.R;
import com.neves.topquiz.model.User;

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
            String username = mUsername.getText().toString();
            String mail = mMail.getText().toString();
            String confirmMail = mMail.getText().toString();
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmPassword.getText().toString();
            if(username.length()==0 || mail.length()==0 || confirmMail.length()==0 || password.length()==0 || confirmPassword.length() == 0){
                Toast.makeText(this, getString(R.string.ensureInput), Toast.LENGTH_SHORT).show();
            }else if(!mail.equals(confirmMail)){
                Toast.makeText(this, getString(R.string.ensureMail), Toast.LENGTH_SHORT).show();
            }else if(!password.equals(confirmPassword)){
                Toast.makeText(this, getString(R.string.ensurePassword), Toast.LENGTH_SHORT).show();
            }else if(!checkMailRequirements(mail)){
                Toast.makeText(this, getString(R.string.ensureMailRequirements), Toast.LENGTH_SHORT).show();
            }else if(password.length()<4 || password.length()>30){
                Toast.makeText(this, getString(R.string.ensurePasswordLength), Toast.LENGTH_SHORT).show();
            }else if(!checkPassWordRequirements(password)){
                Toast.makeText(this, getString(R.string.ensurePasswordRequirements), Toast.LENGTH_SHORT).show();
            }else{
                //MODIFIER LE USER AVEC LES INFOS
                Intent ProfileIntent = new Intent(EditProfile.this, Profile.class);
                startActivity(ProfileIntent);
                finish();
            }
        });

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
                //selectionne image gallerie
                //mettre Ã  jour bdd
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    mProfPic.setImageURI(selectedImageUri);
                }
            }
        }
    }
}
