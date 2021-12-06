package com.neves.topquiz.model;

import java.util.List;

public class Question {

    private Theme mTheme;
    private User mCreatedBy;
    private String mImageUrl;
    private String mQuestion;
    private List<String> mAnswerList;
    private int mAnswerIndex;

    /**
     * Constructeur
     * @param theme : le thème de la question
     * @param author : l'auteur de la question
     * @param image : l'image de la question
     * @param question : la question
     * @param answerList : la liste des réponses possibles
     * @param answerIndex : le numéro de la réponse correcte
     */
    public Question(Theme theme, User author, String image, String question, List<String> answerList, int answerIndex) {
        if ((answerIndex >= 0 && answerIndex < 4) && (answerList.size() != 0) && question.length() > 0) {
            mTheme = theme;
            mCreatedBy = author;
            mImageUrl = image;
            mQuestion = question;
            mAnswerList = answerList;
            mAnswerIndex = answerIndex;
        }
    }

    /**
     * Permet de récupérer le thème de la question
     * @return : le thème de la question
     */
    public Theme getTheme() {
        return mTheme;
    }

    /**
     * Permet d'ajouter un thème à la question
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
     * @param imageUrl : est l'URL de l'image de la question
     */
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
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
     * @param question : est l'énoncé de la question
     */
    public void setQuestion(String question) {
        mQuestion = question;
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
     * @param answerIndex : est le numéro de la réponse correcte de la question
     */
    public void setAnswerIndex(int answerIndex) {
        mAnswerIndex = answerIndex;
    }

}
