package com.neves.topquiz.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.neves.topquiz.model.Question;
import com.neves.topquiz.model.QuestionBank;
import com.neves.topquiz.model.Score;
import com.neves.topquiz.model.Theme;
import com.neves.topquiz.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";
    public static final String BUNDLE_QUESTION_BANK = "BUNDLE_QUESTION_BANK";
    public static final String USER = "USER";
    public static final String THEME = "THEME";
    private final GameActivity mGameActivity = this;
    private User mUser;
    private Theme mTheme;
    private QuestionBank mQuestionBank = new QuestionBank();
    private Question mCurrentQuestion;
    private TextView mQuestionTitle;
    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;
    private ShapeableImageView mQuestionImageView;
    private int mRemainingQuestionCount;
    private boolean mEnableTouchEvents;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }
        mUser.setScore(new Score(null, 0));
        mUser.initLastQuestionRecap();

        if (!intent.hasExtra(THEME)) {
            this.finish();
        }
        mTheme = intent.getParcelableExtra(THEME);

        if (savedInstanceState != null) {
            mUser.setScore(new Score(mTheme, savedInstanceState.getInt(BUNDLE_STATE_SCORE)));
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            mQuestionBank = savedInstanceState.getParcelable(BUNDLE_QUESTION_BANK);
            mQuestionBank.setNextQuestionIndex(mQuestionBank.getSize() - mRemainingQuestionCount);
        } else {
            mUser.setScore(new Score(mTheme, 0));
            mRemainingQuestionCount = 4; // TODO A AUGMENTER ICI POUR AUGMENTER LE NOMBRE DE QUESTIONS PAR PARTIE
            generateQuestions();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mEnableTouchEvents = true;

        mQuestionImageView = findViewById(R.id.question_image_siv);
        mQuestionTitle = findViewById(R.id.question_title_tv);
        mQuestionTextView = findViewById(R.id.question_question_tv);
        mAnswerButton1 = findViewById(R.id.question_answer1_btn);
        mAnswerButton2 = findViewById(R.id.question_answer2_btn);
        mAnswerButton3 = findViewById(R.id.question_answer3_btn);
        mAnswerButton4 = findViewById(R.id.question_answer4_btn);

        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);
    }

    /**
     * Affiche la question et les réponses dans l'interface
     *
     * @param question : object question
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public final void displayQuestion(final Question question) {
        mQuestionTitle.setText(question.getQuestionTitle());
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getAnswerList().get(0));
        mAnswerButton2.setText(question.getAnswerList().get(1));
        mAnswerButton3.setText(question.getAnswerList().get(2));
        mAnswerButton4.setText(question.getAnswerList().get(3));
        if (question.getImageUrl() == "null") {
            new DownLoadImageTask(mQuestionImageView).execute(mTheme.getImage());
        } else {
            new DownLoadImageTask(mQuestionImageView).execute(GlobalVariable.API_URL +question.getImageUrl());
        }

    }

    public void generateQuestions() {
        AndroidNetworking.get(GlobalVariable.API_URL + "/api/question/randomQuestion/" + mTheme.getTitle() + "/" + mRemainingQuestionCount)
                .setTag("getQuestions")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray questionsJSONArray = response.getJSONArray("questions");
                            mRemainingQuestionCount = questionsJSONArray.length();
                            if (mRemainingQuestionCount == 0) {
                                Toast.makeText(mGameActivity, getString(R.string.noQuestions), Toast.LENGTH_SHORT).show();
                                mGameActivity.finish();
                            } else {
                                for (int i = 0; i < questionsJSONArray.length(); i++) {
                                    JSONObject questionJSONObject = questionsJSONArray.getJSONObject(i);
                                    JSONArray answerListJSONArray = questionJSONObject.getJSONArray("answerList");
                                    List<String> answerList = Arrays.asList(answerListJSONArray.getString(0), answerListJSONArray.getString(1), answerListJSONArray.getString(2), answerListJSONArray.getString(3));
                                    Question question = new Question(
                                            mTheme,
                                            new User("UNKNOWN", "", "", ""),
                                            questionJSONObject.getString("_id"),
                                            questionJSONObject.getString("imageUrl"),
                                            questionJSONObject.getString("questionTitle"),
                                            questionJSONObject.getString("description"),
                                            answerList,
                                            questionJSONObject.getInt("correctAnswerIndex"));
                                    mQuestionBank.addQuestion(question);
                                }
                                mCurrentQuestion = mQuestionBank.getCurrentQuestion();
                                mGameActivity.displayQuestion(mCurrentQuestion);
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
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();
        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            mUser.incrementScore();
            handleButtonColor(responseIndex, true);
            mUser.addQuestionToQuestionRecap(mCurrentQuestion, true);
            Toast.makeText(this, getString(R.string.rightAnswer), Toast.LENGTH_SHORT).show();
        } else {
            handleButtonColor(responseIndex, false);
            mUser.addQuestionToQuestionRecap(mCurrentQuestion, false);
            Toast.makeText(this, getString(R.string.wrongAnswer), Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;
        mRemainingQuestionCount--;

        new Handler().postDelayed(() -> {
            mEnableTouchEvents = true;
            if (mRemainingQuestionCount > 0) {
                // If this is the last question, ends the game.
                // Else, display the next question.
                mCurrentQuestion = mQuestionBank.getNextQuestion();
                handleButtonColor(4, true);
                displayQuestion(mCurrentQuestion);
            } else {
                // No questions left, end the game
                endGame();
            }
        }, 2000);
    }

    /**
     * Permet de colorer l'affichage des boutons après réponse à une question
     *
     * @param numQuestion : est le numéro de la question, 4 pour réinitialiser les boutons avec les
     *                    couleurs d'origine
     * @param answer      : si la réponse est vrai ou fausse
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    void handleButtonColor(int numQuestion, boolean answer) {
        switch (numQuestion) {
            case 0:
                if (answer) {
                    mAnswerButton1.setBackground(getDrawable(R.drawable.container_answer_button_correct));
                } else {
                    mAnswerButton1.setBackground(getDrawable(R.drawable.container_answer_button_wrong));
                }
                break;
            case 1:
                if (answer) {
                    mAnswerButton2.setBackground(getDrawable(R.drawable.container_answer_button_correct));
                } else {
                    mAnswerButton2.setBackground(getDrawable(R.drawable.container_answer_button_wrong));
                }
                break;
            case 2:
                if (answer) {
                    mAnswerButton3.setBackground(getDrawable(R.drawable.container_answer_button_correct));
                } else {
                    mAnswerButton3.setBackground(getDrawable(R.drawable.container_answer_button_wrong));
                }
                break;
            case 3:
                if (answer) {
                    mAnswerButton4.setBackground(getDrawable(R.drawable.container_answer_button_correct));
                } else {
                    mAnswerButton4.setBackground(getDrawable(R.drawable.container_answer_button_wrong));
                }
                break;
            case 4:
                mAnswerButton1.setBackground(getDrawable(R.drawable.container_answer_button));
                mAnswerButton2.setBackground(getDrawable(R.drawable.container_answer_button));
                mAnswerButton3.setBackground(getDrawable(R.drawable.container_answer_button));
                mAnswerButton4.setBackground(getDrawable(R.drawable.container_answer_button));
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString((R.string.congrats)))
                .setMessage(getString(R.string.messageScore) + mUser.getScore().getPoints() + getString(R.string.points))
                .setPositiveButton(getString(R.string.OKbutton), (dialog, which) -> {
                    Intent AnswerRecap = new Intent(GameActivity.this, AnswerRecapActivity.class);
                    AnswerRecap.putExtra(USER, mUser);
                    startActivity(AnswerRecap);
                })
                .create()
                .show();
    }

    @Override
    public void finish() {
        Intent intent = getIntent();
        if (intent != null) {
            User user = intent.getParcelableExtra(USER);
            if (user != null) {
                user.setScore(mUser.getScore());
                intent.putExtra(USER, user);
            }
        }
        setResult(RESULT_OK, intent);
        super.finish();
    }

}
