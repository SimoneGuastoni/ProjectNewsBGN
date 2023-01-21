package com.example.projectnewsbgn.Repository;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.Result;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
public interface    INewsRepositoryWithLiveData {

    MutableLiveData<Result> fetchNews(String country, List<String> topicList, int page, long lastUpdate);

    MutableLiveData<Result> fetchNewsChoseTopic(String country,int page,String topic,String query);

    MutableLiveData<Result> fetchNewsChoseTopicAndQuery(String country, int i, List<String> topicList, String query);

    MutableLiveData<Result> getFavoriteNewsList();

    MutableLiveData<Result> deleteAllFavoriteNews();

    void updateNews(News news);

    void updateNewsNotSaved(News news);


}
