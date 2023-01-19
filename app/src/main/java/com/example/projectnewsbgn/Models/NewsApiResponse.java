package com.example.projectnewsbgn.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class NewsApiResponse extends NewsResponse {
     String status;
     int totalResults;
     List<News> articles;

    public NewsApiResponse(){}

    public NewsApiResponse(String status, int totalResults, List<News> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    protected NewsApiResponse(Parcel in) {
        status = in.readString();
        totalResults = in.readInt();
    }

    public static final Creator<NewsApiResponse> CREATOR = new Creator<NewsApiResponse>() {
        @Override
        public NewsApiResponse createFromParcel(Parcel in) {
            return new NewsApiResponse(in);
        }

        @Override
        public NewsApiResponse[] newArray(int size) {
            return new NewsApiResponse[size];
        }
    };


    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<News> getArticles() {
        return articles;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeInt(totalResults);
    }
}
