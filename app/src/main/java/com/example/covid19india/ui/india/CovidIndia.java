package com.example.covid19india.ui.india;

import android.os.Parcel;
import android.os.Parcelable;

public class CovidIndia implements Parcelable {

    String mCovidState,mDeaths,mRecovered;
    String mCases;

    public CovidIndia(String mCovidState,String mCases, String mDeaths, String mRecovered ) {
        this.mCovidState = mCovidState;
        this.mDeaths = mDeaths;
        this.mRecovered = mRecovered;
        this.mCases = mCases;
    }

    public String getmCovidState() {
        return mCovidState;
    }

    public String getmDeaths() {
        return mDeaths;
    }

    public String getmRecovered() {
        return mRecovered;
    }


    public String getmCases() {
        return mCases;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCovidState);
        dest.writeString(this.mCases);
        dest.writeString(this.mDeaths);
        dest.writeString(this.mRecovered);



    }

    protected CovidIndia(Parcel in){
        this.mCovidState = in.readString();
        this.mCases = in.readString();
        this.mDeaths = in.readString();
        this.mRecovered = in.readString();

    }

    public static final Creator<CovidIndia> CREATOR= new Creator<CovidIndia>() {
        @Override
        public CovidIndia createFromParcel(Parcel source) {
            return new CovidIndia(source);
        }

        @Override
        public CovidIndia[] newArray(int size) {
            return new CovidIndia[size];
        }
    };
}
