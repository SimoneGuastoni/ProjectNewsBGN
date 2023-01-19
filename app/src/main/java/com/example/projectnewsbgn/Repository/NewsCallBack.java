package com.example.projectnewsbgn.Repository;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.Utility.CallNewsApi;

import java.util.List;

public interface NewsCallBack {
    void onSuccessFromRemote(NewsApiResponse newsApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(NewsApiResponse newsApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromLocal(List<News> newsList);
    void onSuccessFromLocal(List<News> newsList,Long lastUpdate);
    void onNewsFavoriteStatusChanged(News news, List<News> favoriteNews);
    void onNewsFavoriteStatusChanged(List<News> news);
    void onNewsFavoriteStatusChanged(News news);
    void onDeleteFavoriteNewsSuccess(List<News> favoriteNews);
    void onFailureEmptyFavouriteList(String message);
}
