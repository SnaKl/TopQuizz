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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        mProfPic  = (ShapeableImageView) findViewById(R.id.profile_picture_siv);
        mUsername = (EditText) findViewById(R.id.profile_edit_username_input);
        mMail = (EditText) findViewById(R.id.profile_edit_mail_input);
        mConfirmMail = (EditText) findViewById(R.id.profile_edit_confirmMail_input);
        mPassword = (EditText) findViewById(R.id.profile_edit_password_input);
        mConfirmPassword = (EditText) findViewById(R.id.profile_edit_confirmPassword_input);
        mValidateBtn = (Button) findViewById(R.id.edit_profile_validate);
        mEditBtn = (ImageButton) findViewById(R.id.profile_edit_pictureEdit_btn);

        //UserDB userDB = new UserDB(EditProfile.this.getContext());
        //mUsername.setText(userDB.getAll().getString(0));
        //mMail.setText(userDB.getAll().getString(0));
        //mPassword.setText(userDB.getAll().getString(0));

        mValidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mConfirmMail.equals(mMail)){
                    if(mConfirmPassword.equals(mPassword)){
                        profileUpdate();
                        Intent ChooseThemeIntent = new Intent(EditProfile.this, Profile.class);
                        startActivity(ChooseThemeIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(EditProfile.this, R.string.wrongPassword, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(EditProfile.this, R.string.wrongMail, Toast.LENGTH_SHORT).show();
                }

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

    void profileUpdate(){
        /*userDB.setUserName(mUsername.getText());
        userDB.setMail(mMail.getText());
        userDB.setPassword(mPassword.getText());*/
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
