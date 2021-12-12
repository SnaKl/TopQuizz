package com.neves.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank implements Parcelable {

    public static final Parcelable.Creator<QuestionBank> CREATOR
            = new Parcelable.Creator<QuestionBank>() {
        public QuestionBank createFromParcel(Parcel in) {
            return new QuestionBank(in);
        }

        public QuestionBank[] newArray(int size) {
            return new QuestionBank[size];
        }
    };
    private static final int mSize = 10;
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    /**
     * Constructeur
     *
     * @param questionList : Liste de questions
     */
    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;
        //Collections.shuffle(mQuestionList);
        mNextQuestionIndex = 0;
    }

    public QuestionBank() {
        mQuestionList = new ArrayList<>();
        mNextQuestionIndex = 0;
    }

    private QuestionBank(Parcel in) {
        mQuestionList = new ArrayList<>();
        in.readList(mQuestionList, Question.class.getClassLoader());
        mNextQuestionIndex = in.readInt();
    }

    public void addQuestion(Question question) {
        mQuestionList.add(question);
    }

    /**
     * Implémentation de Parcelable
     */
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeList(mQuestionList);
        out.writeInt(mNextQuestionIndex);
    }

    /**
     * Permet de récupérer la liste de questions
     *
     * @return : la liste de questions
     */
    public List<Question> getQuestionList() {
        return mQuestionList;
    }

    public void setQuestionList(List<Question> questionList) {
        mQuestionList = questionList;
    }

    /**
     * Permet de récupérer l'indice de la prochaine question
     *
     * @return : l'indice de la prochaine question
     */
    public int getNextQuestionIndex() {
        return mNextQuestionIndex;
    }

    /**
     * Permet d'ajouter un  au score
     *
     * @param nextQuestionIndex : est le thème du score
     */
    public void setNextQuestionIndex(int nextQuestionIndex) {
        this.mNextQuestionIndex = nextQuestionIndex;
    }

    /**
     * Permet de récupérer la taille de la banque de questions
     *
     * @return : la taille de la banque de questions
     */
    public int getSize() {
        return mSize;
    }

    /**
     * Renvoie la question actuelle de la liste
     *
     * @return : la question actuelle de la liste
     */
    public Question getCurrentQuestion() {
        return mQuestionList.get(mNextQuestionIndex);
    }

    /**
     * Renvoie la question suivante de la liste
     *
     * @return : la question suivante de la liste
     */
    public Question getNextQuestion() {
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }
        mNextQuestionIndex++;
        return getCurrentQuestion();
    }

    /**
     * Permet l'ajout d'une question à la banque
     *
     * @param question : est la question à ajouter
     */
    public void setQuestion(Question question) {
        mQuestionList.add(question);
        mNextQuestionIndex++;
    }

}
