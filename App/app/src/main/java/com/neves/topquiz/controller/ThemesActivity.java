package com.neves.topquiz.controller;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

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
import java.util.ArrayList;
import java.util.List;

public class ThemesActivity extends AppCompatActivity {
    public static final String USER = "USER";
    private static final String THEME = "THEME";
    private static final String MODE = "MODE";
    private User mUser;
    private String mMode;

    private final ThemesActivity mThemesActivity = this;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
            mMode = intent.getStringExtra(MODE);
        }

        List<Theme> themeList = new ArrayList<>();

        AndroidNetworking.get(GlobalVariable.API_URL + "/api/theme")
                .setTag("getAllThemes")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("themes")) {
                            try {
                                JSONArray themes = response.getJSONArray("themes");
                                for (int i = 0; i < themes.length(); i++) {
                                    JSONObject theme = themes.getJSONObject(i);
                                    themeList.add(new Theme(theme.getString("imageUrl"), theme.getString("title"), theme.getString("description"), theme.getInt("nbQuestion")));
                                }
                                createLayout(themeList);
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("themesError", error.toString());
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createLayout(List<Theme> themeList) throws IOException {
        LinearLayout listLayout = findViewById(R.id.themes_themesList_lt);
        ShapeableImageView left_btn_mockup = findViewById(R.id.themes_left_btn);
        ShapeableImageView right_btn_mockup = findViewById(R.id.themes_right_btn);
        TextView left_title_mockup = findViewById(R.id.themes_leftThemeTitle_tv);
        TextView right_title_mockup = findViewById(R.id.themes_rightThemeTitle_tv);
        ConstraintLayout row_mockup = findViewById(R.id.themes_mockupThemeRow_lt);

        listLayout.removeAllViews();

        boolean left = true;
        ConstraintLayout row = createRow(row_mockup);
        List<ShapeableImageView> buttonList = new ArrayList<>();

        for (Theme theme : themeList) {
            ShapeableImageView themeButton = new ShapeableImageView(this);
            TextView themeTitle = new TextView(this);
            themeTitle.setText(theme.getTitle());
            new DownLoadImageTask(themeButton).execute(theme.getImage());
            roundButtonCorners(themeButton);
            themeButton.setElevation(8);
            themeTitle.setTextColor(getResources().getColor(R.color.colorWhite));
            themeTitle.setTypeface(themeTitle.getTypeface(), Typeface.BOLD);
            themeTitle.setElevation(16);

            if (left && theme.equals(themeList.get(themeList.size() - 1))) {
                themeButton.setLayoutParams(new ViewGroup.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                themeButton.setScaleType(ImageView.ScaleType.CENTER_CROP);

                ConstraintLayout constraintLayout = row;
                int rowId = View.generateViewId();
                int themeButtonId = View.generateViewId();
                int titleId = View.generateViewId();
                row.setId(rowId);
                row.addView(themeButton);
                row.addView(themeTitle);
                themeTitle.setShadowLayer(0.7f, 1.5f, 1.5f, R.color.colorBlack);
                themeButton.setId(themeButtonId);
                themeTitle.setId(titleId);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(titleId, ConstraintSet.BOTTOM, themeButtonId, ConstraintSet.BOTTOM, 8);
                constraintSet.connect(titleId, ConstraintSet.START, themeButtonId, ConstraintSet.START, 0);
                constraintSet.connect(titleId, ConstraintSet.END, themeButtonId, ConstraintSet.END, 0);
                constraintSet.connect(themeButtonId, ConstraintSet.START, rowId, ConstraintSet.START, 96);
                constraintSet.connect(themeButtonId, ConstraintSet.END, rowId, ConstraintSet.END, 96);
                constraintSet.applyTo(constraintLayout);
                buttonList.add(themeButton);
                listLayout.addView(row);
            } else if (left) {
                themeButton.setLayoutParams(left_btn_mockup.getLayoutParams());
                themeButton.setId(R.id.themes_left_btn);
                themeButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                themeTitle.setLayoutParams(left_title_mockup.getLayoutParams());
                themeTitle.setId(R.id.themes_leftThemeTitle_tv);
                themeTitle.setShadowLayer(0.7f, 1.5f, 1.5f, R.color.colorBlack);
                row.addView(themeButton);
                row.addView(themeTitle);
                left = false;
            } else {
                themeButton.setLayoutParams(right_btn_mockup.getLayoutParams());
                themeButton.setId(R.id.themes_right_btn);
                themeButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                themeTitle.setLayoutParams(right_title_mockup.getLayoutParams());
                themeTitle.setId(R.id.themes_rightThemeTitle_tv);
                themeTitle.setShadowLayer(0.7f, 1.5f, 1.5f, R.color.colorBlack);
                left = true;
                row.addView(themeButton);
                row.addView(themeTitle);
                listLayout.addView(row);
                row = createRow(row_mockup);
                buttonList.add(themeButton);
            }
            checkQuestionAvailability(theme, themeButton);
        }

    }

    private ConstraintLayout createRow(ConstraintLayout row_mockup) {
        ConstraintLayout caseConstraint = new ConstraintLayout(this);
        caseConstraint.setLayoutParams(row_mockup.getLayoutParams());
        return caseConstraint;
    }

    private void manageButtonClickListener(ShapeableImageView button, Theme theme) {
        button.setOnClickListener(v -> {
            final Intent[] GameActivity = new Intent[1];
            if (theme.getQuestionNB() == 0) {
                Toast.makeText(mThemesActivity, getString(R.string.noQuestions), Toast.LENGTH_SHORT).show();
                return;
            }

            if (mMode.equals("GAME")) {
                GameActivity[0] = new Intent(ThemesActivity.this, GameActivity.class);
                GameActivity[0].putExtra(USER, mUser);
                GameActivity[0].putExtra(THEME, theme);
                startActivity(GameActivity[0]);
            } else {
                AndroidNetworking.get(GlobalVariable.API_URL + "/api/question/vote/" + theme.getTitle() + "/nb")
                        .addHeaders("Authorization", "Bearer " + mUser.getJwtToken())
                        .setTag("getQuestionNbToValidate")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int nbQuestion = response.getInt("questionNb");
                                    if (nbQuestion == 0) {
                                        Toast.makeText(mThemesActivity, getString(R.string.noQuestionsToValidate), Toast.LENGTH_SHORT).show();
                                    } else {
                                        theme.setQuestionNB(nbQuestion);
                                        GameActivity[0] = new Intent(ThemesActivity.this, ValidateQuestionActivity.class);
                                        GameActivity[0].putExtra(USER, mUser);
                                        GameActivity[0].putExtra(THEME, theme);
                                        startActivity(GameActivity[0]);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                Log.d("getQuestionNbToValidate", error.getErrorBody());
                            }
                        });


            }

        });
    }

    private void toastClickListener(ShapeableImageView button) {
        button.setOnClickListener(v -> {
            if (mMode.equals("GAME")) {
                Toast.makeText(mThemesActivity, getString(R.string.noQuestions), Toast.LENGTH_SHORT).show();
            } else if (mMode.equals("VALIDATE_THEME")) {
                Toast.makeText(mThemesActivity, getString(R.string.noQuestionsToValidate), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void roundButtonCorners(ShapeableImageView button) {
        button.setShapeAppearanceModel(button.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCornerSize(25)
                .setTopLeftCornerSize(25)
                .setBottomRightCornerSize(25)
                .setBottomLeftCornerSize(25)
                .build());
    }

    public void checkQuestionAvailability(Theme theme, ShapeableImageView button) {
        if (mMode.equals("GAME")) {
            AndroidNetworking.get(GlobalVariable.API_URL + "/api/question/randomQuestion/" + theme.getTitle() + "/" + 1)
                    .setTag("getQuestions")
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray questionsJSONArray = response.getJSONArray("questions");
                                int RemainingQuestionCount = questionsJSONArray.length();
                                if (RemainingQuestionCount == 0) {
                                    desaturateButtonColor(button);
                                    toastClickListener(button);
                                } else {
                                    manageButtonClickListener(button, theme);
                                }
                            } catch (JSONException jsonException) {
                                Log.d("getQuestions", jsonException.toString());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d("getQuestions", anError.toString());
                        }
                    });
        } else if (mMode.equals("VALIDATE_THEME")) {
            AndroidNetworking.get(GlobalVariable.API_URL + "/api/question/vote/" + theme.getTitle() + "/nb")
                    .addHeaders("Authorization", "Bearer " + mUser.getJwtToken())
                    .setTag("getQuestionNbToValidate")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int nbQuestion = response.getInt("questionNb");
                                if (nbQuestion == 0) {
                                    desaturateButtonColor(button);
                                    toastClickListener(button);
                                } else {
                                    manageButtonClickListener(button, theme);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            Log.d("getQuestionNbToValidate", error.getErrorBody());
                        }
                    });

        }
    }

    private void desaturateButtonColor(ShapeableImageView button) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        button.setColorFilter(filter);
    }

}