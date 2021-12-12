package com.neves.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {

    private Theme mTheme;

    private int mPoints;

    /**
     * Constructeur par défaut
     */
    public Score() {
        mTheme = null;
        mPoints = 0;
    }

    /**
     * Constructeur
     * @param theme : est le thème sur lequel a été fait le score
     * @param points : est le nombre de points du joueur sur le thème
     */
    public Score(Theme theme, int points) {
        mTheme = theme;
        mPoints = points;
    }

    protected Score(Parcel in) {
        mTheme = in.readParcelable(Theme.class.getClassLoader());
        mPoints = in.readInt();
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    /**
     * Permet de récupérer le thème du score
     * @return : le thème du score
     */
    public Theme getTheme() {
        return mTheme;
    }

    /**
     * Permet d'ajouter un thème au score
     * @param theme : est le thème du score
     */
    public void setTheme(Theme theme) {
        mTheme = theme;
    }

    /**
     * Permet de récupérer le nombre de points du score
     * @return : le nombre de points du score
     */
    public int getPoints() {
        return mPoints;
    }

    /**
     * Permet d'ajouter un nombre de points au score
     * @param points : est le nombre de points du score
     */
    public void setPoints(int points) {
        this.mPoints = points;
    }

    /**
     * Permet d'incrémenter le score de 1
     */
    public void incrementScore(){
        mPoints++;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(mTheme,flags);
        out.writeInt(mPoints);
    }
}
