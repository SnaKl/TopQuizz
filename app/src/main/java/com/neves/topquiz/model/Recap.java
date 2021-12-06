package com.neves.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Recap implements Parcelable {
    private List<Pair<Question, Boolean>> mQuestionRecap;
    private int mNumberQuestions;
    private int mNumberGoodAnswers;

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
        //out.writeInt(score);
        //out.writeString(mFirstName);
    }

    private Recap(Parcel in) {
        mQuestionRecap= new ArrayList<Pair<Question, Boolean>>();
        mNumberQuestions=0;
        //mQuestionRecap=in.readList(mQuestionRecap, mQuestionRecap.class.getClassLoader());;
        //score = in.readInt();
        //mFirstName = in.readString();
    }

    public Pair<Question,Boolean> getQuestion(int index) {
        return mQuestionRecap.get(index);
    }

    public List<Pair<Question,Boolean>> getQuestionRecap() {
        return mQuestionRecap;
    }

    /**
     * Constructeur
     */
    public Recap() {
        mQuestionRecap= new ArrayList<Pair<Question, Boolean>>();
        mNumberQuestions=0;
    }
}
