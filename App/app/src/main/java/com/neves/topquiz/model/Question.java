package com.neves.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.neves.topquiz.GlobalVariable;

import java.util.List;

public class Question implements Parcelable {

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    private final String mQuestionId;
    private final String mQuestionTitle;
    private Theme mTheme;
    private User mCreatedBy;
    private String mImageUrl;
    private String mQuestion;
    private List<String> mAnswerList;
    private int mAnswerIndex;

    /**
     * Constructeur
     *
     * @param theme       : le thème de la question
     * @param author      : l'auteur de la question
     * @param image       : l'image de la question
     * @param question    : la question
     * @param answerList  : la liste des réponses possibles
     * @param answerIndex : le numéro de la réponse correcte
     */
    public Question(Theme theme, User author, String questionId, String imageUrl, String questionTitle, String question, List<String> answerList, int answerIndex) {
        mTheme = theme;
        mCreatedBy = author;
        mQuestionId = questionId;
        mImageUrl = imageUrl;
        mQuestionTitle = questionTitle;
        mQuestion = question;
        mAnswerList = answerList;
        mAnswerIndex = answerIndex;
    }

    protected Question(Parcel in) {
        mTheme = in.readParcelable(Theme.class.getClassLoader());
        mCreatedBy = in.readParcelable(User.class.getClassLoader());
        mQuestionId = in.readString();
        mImageUrl = in.readString();
        mQuestion = in.readString();
        mQuestionTitle = in.readString();
        mAnswerList = in.createStringArrayList();
        mAnswerIndex = in.readInt();
    }

    /**
     * Permet de récupérer le thème de la question
     *
     * @return : le thème de la question
     */
    public Theme getTheme() {
        return mTheme;
    }

    /**
     * Permet d'ajouter un thème à la question
     *
     * @param theme : est le thème de la question
     */
    public void setTheme(Theme theme) {
        mTheme = theme;
    }

    /**
     * Retourne l'auteur de la question
     *
     * @return l'auteur de la question
     */
    public User getCreatedBy() {
        return mCreatedBy;
    }

    /**
     * Permet d'ajouter un auteur à la question
     *
     * @param createdBy : est l'auteur de la question
     */
    public void setCreatedBy(User createdBy) {
        mCreatedBy = createdBy;
    }

    /**
     * Retourne l'image de la question
     *
     * @return l'image de la question
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    /**
     * Permet d'ajouter une image à la question
     *
     * @param imageUrl : est l'URL de l'image de la question
     */
    public void setImageUrl(String imageUrl) {
        mImageUrl = GlobalVariable.API_URL + imageUrl;
    }

    /**
     * Retourner la question
     *
     * @return la question
     */
    public String getQuestion() {
        return mQuestion;
    }

    /**
     * Permet d'ajouter une question
     *
     * @param question : est l'énoncé de la question
     */
    public void setQuestion(String question) {
        mQuestion = question;
    }

    /**
     * Retourner la question
     *
     * @return la question
     */
    public String getQuestionTitle() {
        return mQuestionTitle;
    }

    /**
     * Retourne la liste des réponses
     *
     * @return la liste des réponses
     */
    public List<String> getAnswerList() {
        return mAnswerList;
    }

    /**
     * Permet d'ajouter une liste de réponses possibles à la question
     *
     * @param answerList: est la liste de réponses possibles de la question
     */
    public void setAnswerList(List<String> answerList) {
        mAnswerList = answerList;
    }

    /**
     * Retourne le numéro de la réponse à la question
     *
     * @return le numéro de la réponse à la question
     */
    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    /**
     * Permet d'ajouter le numéro de la réponse correcte à la question
     *
     * @param answerIndex : est le numéro de la réponse correcte de la question
     */
    public void setAnswerIndex(int answerIndex) {
        mAnswerIndex = answerIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(mTheme, flags);
        out.writeParcelable(mCreatedBy, flags);
        out.writeString(mQuestionId);
        out.writeString(mImageUrl);
        out.writeString(mQuestion);
        out.writeString(mQuestionTitle);
        out.writeStringList(mAnswerList);
        out.writeInt(mAnswerIndex);
    }

    public String getQuestionId() {
        return mQuestionId;
    }
}
