package com.neves.topquiz.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class User implements Parcelable {

    private String mNickname;
    private String mPassword;
    private String mSalt;
    private String mJwtToken;
    private String mEmail;
    private LocalDate mSignUpDate;
    private int mTotalScore;
    private Score mScore;
    private String mAvatar;

    /**
     * Constructeur vide
     */
    public User() {}

    /**
     * Constructeur
     * @param nickname : est le nom de l'utilisateur
     * @param password : est le mot de passe de l'utilisateur
     * @param salt : est le salt de l'utilisateur
     * @param jwtToken : est le jwt token de l'utilisateur
     * @param email : est l'email de l'utilisateur
     * @param avatar : est l'avatar de l'utilisateur
     */
    public User(String nickname, String password, String salt, String jwtToken, String email, String avatar) {
        mNickname = nickname;
        mPassword = password;
        mSalt = salt;
        mJwtToken = jwtToken;
        mEmail = email;
        mTotalScore = 0;
        mAvatar = avatar;
    }

    /**
     * Implémentation de Parcelable
     */
    public static final Creator<User> CREATOR = new Creator<User>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mNickname);
        out.writeString(mPassword);
        out.writeString(mSalt);
        out.writeString(mJwtToken);
        out.writeString(mEmail);
        out.writeInt(mTotalScore);
        out.writeValue(mSignUpDate);
        out.writeParcelable(mScore,flags);
        out.writeString(mAvatar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private User(Parcel in) {
        mNickname = in.readString();
        mPassword = in.readString();
        mSalt = in.readString();
        mJwtToken = in.readString();
        mEmail = in.readString();
        mTotalScore = in.readInt();
        mSignUpDate = (LocalDate) in.readValue(LocalDate.class.getClassLoader());
        mScore = in.readParcelable(Score.class.getClassLoader());
        mAvatar = in.readString();
    }

    /**
     * Permet de récupérer le nom de l'utilisateur
     * @return : le nom de l'utilisateur
     */
    public String getNickname() {
        return mNickname;
    }

    /**
     * Permet d'ajouter un nom à l'utilisateur
     * @param nickname : est le nom de l'utilisateur
     */
    public void setNickname(String nickname) {
        mNickname = nickname;
    }

    /**
     * Permet de récupérer le mot de passe de l'utilisateur
     * @return : le mot de passe de l'utilisateur
     */
    public String getPassword() {
        return mPassword;
    }

    /**
     * Permet d'ajouter un mot de passe à l'utilisateur
     * @param password : est le mot de passe de l'utilisateur
     */
    public void setPassword(String password) {
        mPassword = password;
    }

    /**
     * Permet de récupérer le salt de l'utilisateur
     * @return : le salt de l'utilisateur
     */
    public String getSalt() {
        return mSalt;
    }

    /**
     * Permet d'ajouter un salt à l'utilisateur
     * @param salt: est le salt de l'utilisateur
     */
    public void setSalt(String salt) {
        mSalt = salt;
    }

    /**
     * Permet de récupérer le jwt token de l'utilisateur
     * @return : le jwt token de l'utilisateur
     */
    public String getJwtToken() {
        return mJwtToken;
    }

    /**
     * Permet d'ajouter un jwt token à l'utilisateur
     * @param jwtToken : est le jwt token de l'utilisateur
     */
    public void setJwtToken(String jwtToken) {
        mJwtToken = jwtToken;
    }

    /**
     * Permet de récupérer l'email de l'utilisateur
     * @return : l'email de l'utilisateur
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Permet d'ajouter un email à l'utilisateur
     * @param email : est l'email de l'utilisateur
     */
    public void setEmail(String email) {
        mEmail = email;
    }

    /**
     * Permet de récupérer la date de création de compte de l'utilisateur
     * @return : la date de création de compte de l'utilisateur
     */
    public LocalDate getSignUpDate() {
        return mSignUpDate;
    }

    /**
     * Permet d'ajouter une date de création de compte à l'utilisateur
     * @param signUpDate : est la date de création de compte de l'utilisateur
     */
    public void setSignUpDate(LocalDate signUpDate) {
        mSignUpDate = signUpDate;
    }

    /**
     * Retourne le score total de l'utilisateur
     * @return le score total de l'utilisateur
     */
    public int getTotalScore() {
        return mTotalScore;
    }

    /**
     * Affecte un score total à l'utilisateur
     * @param totalScore de l'utilisateur
     */
    public void setTotalScore(int totalScore) {
        mTotalScore = totalScore;
    }

    /**
     * Retourne le score de l'utilisateur
     * @return le score de l'utilisateur
     */
    public Score getScore() {
        return mScore;
    }

    /**
     * Affecte un score à l'utilisateur
     * @param score de l'utilisateur
     */
    public void setScore(Score score) {
        mScore = score;
    }

    /**
     * Permet de récupérer l'avatar de l'utilisateur
     * @return : l'avatar de l'utilisateur
     */
    public String getAvatar() {
        return mAvatar;
    }

    /**
     * Permet d'ajouter un avatar à l'utilisateur
     * @param avatar : est l'avatar de l'utilisateur
     */
    public void setAvatar(String avatar) {
        this.mAvatar = avatar;
    }

    /**
     * Permet d'incrémenter de 1 le score
     */
    public void incrementScore(){
        mScore.incrementScore();
    }

    /**
     * Permet d'ajouter le score au score total
     */
    public void addScoreToTotal() {
        mTotalScore += mScore.getPoints();
    }

}
