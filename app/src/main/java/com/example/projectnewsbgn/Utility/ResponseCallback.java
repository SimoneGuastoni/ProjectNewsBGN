package com.example.projectnewsbgn.Utility;

import com.example.projectnewsbgn.Models.News;

import java.util.List;

public interface ResponseCallback {
    void onSuccess(List<News> newsList, long lastUpdate);
    void onFailure(String errorMessage);
    void onNewsFavoriteStatusChange(News news);
}
