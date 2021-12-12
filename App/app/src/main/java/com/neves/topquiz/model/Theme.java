package com.neves.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Theme implements Parcelable {

    private String mImageUrl;

    private String mTitle;

    private String mDescription;

    private int mQuestionNB;

    /**
     * Constructeur vide
     */
    public Theme() {}

    /**
     * Constructeur
     * @param imageUrl : est l'image du thème
     * @param title : est le nom du thème
     * @param description : est la description du thème
     * @param questionNB : est le nombre de questions du thème
     */
    public Theme(String imageUrl, String title, String description, int questionNB) {
        mImageUrl = imageUrl;
        mTitle = title;
        mDescription = description;
        mQuestionNB = questionNB;
    }

    protected Theme(Parcel in) {
        mImageUrl = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mQuestionNB = in.readInt();
    }

    public static final Creator<Theme> CREATOR = new Creator<Theme>() {
        @Override
        public Theme createFromParcel(Parcel in) {
            return new Theme(in);
        }

        @Override
        public Theme[] newArray(int size) {
            return new Theme[size];
        }
    };

    /**
     * Permet de récupérer l'image du thème
     * @return : l'image du thème
     */
    public String getImage() {
        return mImageUrl;
    }

    /**
     * Permet d'ajouter une image au thème
     * @param imageUrl : est l'URL de l'image du thème
     */
    public void setImage(String imageUrl) {
        mImageUrl = imageUrl;
    }

    /**
     * Permet de récupérer le nom du thème
     * @return : le nom du thème
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Permet d'ajouter un titre au thème
     * @param title : est le titre du thème
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Permet de récupérer la description du thème
     * @return : la description du thème
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Permet d'ajouter une description au thème
     * @param description : est la description du thème
     */
    public void setDescription(String description) {
        mDescription = description;
    }

    /**
     * Permet de récupérer le nombre de questions du thème
     * @return : le nombre de questions du thème
     */
    public int getQuestionNB() {
        return mQuestionNB;
    }

    /**
     * Permet d'ajouter un nombre de questions au thème
     * @param questionNB : est le nombre de questions du thème
     */
    public void setQuestionNB(int questionNB) {
        mQuestionNB = questionNB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mImageUrl);
        out.writeString(mTitle);
        out.writeString(mDescription);
        out.writeInt(mQuestionNB);
    }



  
}
