package com.neves.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Recap implements Parcelable {
    List<Pair<Question, Boolean>> mQuestionRecap = new ArrayList<Pair<Question, Boolean>>();
    private String mFirstName;
    private int score;

    public static final Creator<Recap> CREATOR = new Creator<Recap>() {
        @Override
        public Recap createFromParcel(Parcel in) {
            return new Recap(in);
        }

        @Override
        public Recap[] newArray(int size) {
            return new Recap[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(score);
        out.writeString(mFirstName);
    }

    private Recap(Parcel in) {
        score = in.readInt();
        mFirstName = in.readString();
    }

    /**
     * Permet d'incrémenter de 1 le score
     */
    public void incScoreByOne(){
        score++;
    }

    /**
     * Retourne le score de l'utilisateur
     * @return Le score de l'utilisateur
     */
    public int getScore() {
        return score;
    }

    /**
     * Affecte un score à un utilisateur
     * @param score de l'utilisateur
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Constructeur
     * @param mFirstName : est le nom de l'utilisateur
     * @param score : esr le score du joueur
     */
    public Recap(String mFirstName, int score) {
        this.mFirstName = mFirstName;
        this.score = score;
    }

    /**
     * Permet de récupérer le nom de l'utilisateur
     * @return : le nom de l'utilisateur
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Permet d'ajouter un nom à utilisateur
     * @param firstName : est le nom de l'utilisateur
     */
    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }
}
