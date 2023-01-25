package com.example.projectnewsbgn.Source.NewsSource;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;

import java.util.List;

public interface NewsCallBack {
    //TODO controllare onSuccess onFailure

    //Onsuccess
    void onSuccessFromRemote(List<News> newsList, long lastUpdate);
    void onSuccessFromRemote(NewsApiResponse newsApiResponse);
    void onSuccessFromRemote(List<News> newsList);
    void onSuccessFromLocal(List<News> newsList);
    void onSuccessFromLocal(List<News> newsList,Long lastUpdate);

    //Onfailure
    void onFailureFromRemote(Exception exception);
    void onFailureFromLocal(Exception exception);

    //NewsStatusChange
    void onNewsFavoriteStatusChanged(News news, List<News> favoriteNews);
    void onNewsFavoriteStatusChanged(List<News> news);
    void onFailureEmptyFavouriteList(Exception exception);
}
