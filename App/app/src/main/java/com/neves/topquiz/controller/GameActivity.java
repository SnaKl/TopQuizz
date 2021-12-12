package com.neves.topquiz.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.neves.topquiz.model.User;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";
    public static final String BUNDLE_QUESTION_BANK = "BUNDLE_QUESTION_BANK";
    public static final String MY_FILE_NAME = "questions.txt";
    public static final String USER = "USER";

    private User mUser;
    private QuestionBank mQuestionBank;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();

        if (intent.hasExtra(USER)) {
            mUser = intent.getParcelableExtra(USER);
        }
        mUser.setScore(new Score(null,0));
        mUser.initLastQuestionRecap();

        if (savedInstanceState != null) {
            mUser.setScore(new Score(null, savedInstanceState.getInt(BUNDLE_STATE_SCORE)));
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            mQuestionBank = savedInstanceState.getParcelable(BUNDLE_QUESTION_BANK);
            mQuestionBank.setNextQuestionIndex(mQuestionBank.getSize() - mRemainingQuestionCount);
        } else {
            mUser.setScore(new Score(null, 0));
            mRemainingQuestionCount = 4; // A AUGMENTER ICI POUR AUGMENTER LE NOMBRE DE QUESTIONS PAR PARTIE
            /*
            try {
                mQuestionBank = this.generateQuestions();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */

            generateQuestions();
        }

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

        mCurrentQuestion = mQuestionBank.getNextQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    /**
     * Affiche la question et les réponses dans l'interface
     *
     * @param question : object question
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayQuestion(final Question question) {
        mQuestionTitle.setText(question.getQuestionTitle());
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getAnswerList().get(0));
        mAnswerButton2.setText(question.getAnswerList().get(1));
        mAnswerButton3.setText(question.getAnswerList().get(2));
        mAnswerButton4.setText(question.getAnswerList().get(3));
        new DownLoadImageTask(mQuestionImageView).execute(question.getImageUrl());
    }

    private void deleteFile() {
        deleteFile(MY_FILE_NAME);
    }

    /**
     * Créer la banque de question
     *
     * @return une liste de question
    private QuestionBank generateQuestions() throws IOException {

        Question question1 = new Question(null, null, "https://i.ibb.co/kSLD4RJ/iv-office.png", "Question historique",(getString(R.string.question1)),
                Arrays.asList(getString(R.string.response11), getString(R.string.response12), getString(R.string.response13), getString(R.string.response14)),
                2);

        Question question2 = new Question(null, null, null, "Question layout",(getString(R.string.question2)),
                Arrays.asList(getString(R.string.response21), getString(R.string.response22), getString(R.string.response23), getString(R.string.response24)),
                2);

        Question question3 = new Question(null, null, "https://i.ibb.co/ys7B1MW/iv-bigbangtheory.jpg", "Question graphique",(getString(R.string.question3)),
                Arrays.asList(getString(R.string.response31), getString(R.string.response32), getString(R.string.response33), getString(R.string.response34)),
                1);

        Question question4 = new Question(null, null, null, "Question XML",(getString(R.string.question4)),
                Arrays.asList(getString(R.string.response41), getString(R.string.response42), getString(R.string.response43), getString(R.string.response44)),
                0);

        Question question5 = new Question(null, null, null,"Question architecture" ,(getString(R.string.question5)),
                Arrays.asList(getString(R.string.response51), getString(R.string.response52), getString(R.string.response53), getString(R.string.response54)),
                0);

        Question question6 = new Question(null, null, null, "Question de beauté" ,("Qui est le plus beau de la classe"),
                Arrays.asList("Richard", "Rochard", "Ricardo", "Abarna"),
                0);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6));
        //return loadQuestions();
    }
    */

    public void generateQuestions(){
        List<Question> questionList = new ArrayList<>();

        AndroidNetworking.get(GlobalVariable.API_URL+"/api/question/randomQuestion/Colin/5")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("questions");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                // Log.d("getQuestions", jsonArray.getJSONObject(i).toString());
                                JSONObject object = jsonArray.getJSONObject(i);
                                List<String> answerList = new ArrayList<>();
                                JSONArray array = object.getJSONArray("answerList");
                                questionList.add(new Question(
                                        "Colin",
                                        null,
                                        object.getString("imageUrl"),
                                        object.getString("description"),
                                        Arrays.asList(array.getString(0), array.getString(1), array.getString(2), array.getString(3)),
                                        object.getInt("correctAnswerIndex")
                                ));
                                Log.d("QuestionItem", questionList.get(i).getAnswerList().get(2) + " - " +questionList.get(i).getAnswerList().get(0));
                            }
                            mQuestionBank = new QuestionBank(questionList);
                            // Log.d("QUESTIONBANK", String.valueOf(mQuestionBank.getSize()));
                        }catch (JSONException jsonException){
                            jsonException.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d("callAPI", anError.toString());
                        Log.d("callAPI", "ERROR");
                    }
                });
        //return new QuestionBank(questionList);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            mUser.incrementScore();
            handleButtonColor(responseIndex, true);
            mUser.addQuestionToQuestionRecap(mCurrentQuestion,true);
            Toast.makeText(this, getString(R.string.rightAnswer), Toast.LENGTH_SHORT).show();
        } else {
            handleButtonColor(responseIndex, false);
            mUser.addQuestionToQuestionRecap(mCurrentQuestion,false);
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
                .setPositiveButton(getString(R.string.OKbutton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent AnswerRecap = new Intent(GameActivity.this, AnswerRecap.class);
                        AnswerRecap.putExtra(USER, mUser);
                        startActivity(AnswerRecap);
                        //finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void finish() {
        Intent intent = getIntent();
        if (intent != null){
            User user = intent.getParcelableExtra(USER);
            if (user != null){
                user.setScore(mUser.getScore());
                intent.putExtra(USER, user);
            }
        }
        setResult(RESULT_OK, intent);
        super.finish();
    }
    /*private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ShapeableImageView imageView;

        public DownLoadImageTask(ShapeableImageView imageView){
            this.imageView = imageView;
        }
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }*/

}
