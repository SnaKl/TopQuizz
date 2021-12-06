package com.neves.topquiz.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.neves.topquiz.R;
import com.neves.topquiz.model.Question;
import com.neves.topquiz.model.QuestionBank;
import com.neves.topquiz.model.Score;
import com.neves.topquiz.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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

    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    private int mRemainingQuestionCount;
    private boolean mEnableTouchEvents;

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

        deleteFile();
        try {
            Question q = new Question(null, null, null,"Question historique", getString(R.string.question1), Arrays.asList(getString(R.string.response11),
                    getString(R.string.response12), getString(R.string.response13), getString(R.string.response14)), 3);
            saveQuestion(q);
            q = new Question(null, null, null, "Question Layout", getString(R.string.question2), Arrays.asList(getString(R.string.response21),
                    getString(R.string.response22), getString(R.string.response23), getString(R.string.response24)), 3);
            saveQuestion(q);
            q = new Question(null, null, null,"Question Graphique", getString(R.string.question3), Arrays.asList(getString(R.string.response31),
                    getString(R.string.response32), getString(R.string.response33), getString(R.string.response34)), 2);
            saveQuestion(q);
            q = new Question(null, null, null, "Question 4", "Question 4", Arrays.asList("cinq", "six", "sept", "bonne reponse"), 3);
            saveQuestion(q);
            q = new Question(null, null, null, "Question 5", "Question 5", Arrays.asList("cinq", "six", "sept", "bonne reponse"), 3);
            saveQuestion(q);
            q = new Question(null, null, null,"Question 6", "Question 6", Arrays.asList("cinq", "six", "sept", "bonne reponse"), 3);
            saveQuestion(q);
            q = new Question(null, null, null,"Question 7", "Question 7", Arrays.asList("cinq", "six", "sept", "bonne reponse"), 3);
            saveQuestion(q);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (savedInstanceState != null) {
            mUser.setScore(new Score(null, savedInstanceState.getInt(BUNDLE_STATE_SCORE)));
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            mQuestionBank = savedInstanceState.getParcelable(BUNDLE_QUESTION_BANK);
            mQuestionBank.setNextQuestionIndex(mQuestionBank.getSize() - mRemainingQuestionCount);
        } else {
            mUser.setScore(new Score(null, 0));
            mRemainingQuestionCount = 4; // A AUGMENTER ICI POUR AUGMENTER LE NOMBRE DE QUESTIONS PAR PARTIE
            try {
                mQuestionBank = this.generateQuestions();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mEnableTouchEvents = true;

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
    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getAnswerList().get(0));
        mAnswerButton2.setText(question.getAnswerList().get(1));
        mAnswerButton3.setText(question.getAnswerList().get(2));
        mAnswerButton4.setText(question.getAnswerList().get(3));
    }

    private void deleteFile() {
        deleteFile(MY_FILE_NAME);
    }

    private void saveQuestion(Question q) throws IOException {
        //prise en charge - écriture UTF8 - test
        FileOutputStream fos = new FileOutputStream(MY_FILE_NAME);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(osw);
        writer.append(q.getQuestion());
        for (int i = 0; i < 10; i++) {
            writer.append(q.getAnswerList().get(i));
        }
        writer.append(Integer.toString(q.getAnswerIndex()));
        fos.close();

        //prise en charge - lecture UTF8 - test
        Log.d("test-UTF8", "1");
        FileInputStream fis = new FileInputStream(MY_FILE_NAME);
        Log.d("test-UTF8", "2");
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        Log.d("test-UTF8", "3");
        BufferedReader reader = new BufferedReader(isr);
        Log.d("test-UTF8", "4");
        String str;
        if (reader.readLine() == null) {
            Log.d("test-UTF8", "readLine vide");
        }
        Log.d("test-UTF8", "5");
        while ((str = reader.readLine()) != null) {
            Log.d("test-UTF8", str);
        }
        fis.close();
    }

    private QuestionBank loadQuestions() throws IOException {
        String ligne = null;
        int content;
        int i = 0;
        Question q;
        String questionTitle="";
        String question = "";
        int answerIndex = -1;
        List<String> answers = new ArrayList<String>();
        List<Question> listQuestions = new ArrayList<Question>();
        QuestionBank qb = null;

        FileInputStream inputStream = openFileInput(MY_FILE_NAME);
        StringBuilder stringb = new StringBuilder();

        while ((content = inputStream.read()) != -1) {
            if (content != '\n') {
                ligne = String.valueOf(stringb.append((char) content));
            } else if (i == 0) {
                question = ligne;
                Log.d("test_load", "question in : " + question);
                i++;
                stringb.setLength(0);
            } else if (i == 5) {
                answerIndex = Integer.parseInt(ligne);
                Log.d("test_load", "answerIndex in : " + answerIndex);
                i = 0;
                stringb.setLength(0);
                q = new Question(null, null, null, questionTitle, question, answers, answerIndex);
                answers = new ArrayList<String>();
                listQuestions.add(q);
            } else {
                answers.add(ligne);
                Log.d("test_load", "reponse in " + i + " : " + ligne);
                i++;
                stringb.setLength(0);
            }
        }
        qb = new QuestionBank(listQuestions);
        inputStream.close();
        return qb;
    }

    /**
     * Créer la banque de question
     *
     * @return une liste de question
     */
    private QuestionBank generateQuestions() throws IOException {

        Question question1 = new Question(null, null, null, "Question historique",(getString(R.string.question1)),
                Arrays.asList(getString(R.string.response11), getString(R.string.response12), getString(R.string.response13), getString(R.string.response14)),
                2);

        Question question2 = new Question(null, null, null, "Question layout",(getString(R.string.question2)),
                Arrays.asList(getString(R.string.response21), getString(R.string.response22), getString(R.string.response23), getString(R.string.response24)),
                2);

        Question question3 = new Question(null, null, null, "Question graphique",(getString(R.string.question3)),
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
                        //AnswerRecap.putExtra(SCORE, mUser.getScore().getPoints()+"");
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

    //@Override
    //protected void onSaveInstanceState(Bundle outState) {
        //outState.putInt(BUNDLE_STATE_SCORE, mUser.getScore());
        //outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);
        //outState.putParcelable(BUNDLE_QUESTION_BANK, mQuestionBank);
        //super.onSaveInstanceState(outState);
    //}

}
