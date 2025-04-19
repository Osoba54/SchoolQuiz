package com.example.myquiz.quizPackage;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Question implements Parcelable {
    final private int img;
    final private String answer;

    public Question(String answer, int img) {
        this.answer = answer;
        this.img = img;
    }

    protected Question(Parcel in) {
        img = in.readInt();
        answer = in.readString();
    }

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

    public String getAnswer() {
        return answer;
    }

    public int getImg() {
        return img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(answer);
        dest.writeInt(img);
    }
}
