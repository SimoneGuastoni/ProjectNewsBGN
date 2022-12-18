package com.example.projectnewsbgn.Listeners;

import com.example.projectnewsbgn.Models.News;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse>{

    void onFetchData(List<News> list, String message);
    void onError(String message);
}
