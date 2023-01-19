package com.example.projectnewsbgn.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class NewsResponse implements Parcelable {

    private List<News> newsList;

    public NewsResponse() {}

    public NewsResponse(List<News> newsList) {this.newsList = newsList;}

    public List<News> getNewsList() { return  newsList;}

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public String toString() {
        return "NewsResponse{" +
                "newsList=" + newsList +
                '}';
    }

    protected NewsResponse(Parcel in) {
    }

    public static final Creator<NewsResponse> CREATOR = new Creator<NewsResponse>() {
        @Override
        public NewsResponse createFromParcel(Parcel in) {
            return new NewsResponse(in);
        }

        @Override
        public NewsResponse[] newArray(int size) {
            return new NewsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
