package com.example.projectnewsbgn.Repository.NewsRepository;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.Result;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
public interface    INewsRepositoryWithLiveData {

    MutableLiveData<Result> fetchNews(String country, List<String> topicList, int page, long lastUpdate);

    MutableLiveData<Result> fetchNewsChoseTopic(String country,int page,String topic,String query);

    MutableLiveData<Result> fetchNewsChoseTopicAndQuery(String country, int page, List<String> topicList, String query);

    MutableLiveData<Result> getFavoriteNewsList();

    MutableLiveData<Result> deleteCheckedFavoriteNews();

    MutableLiveData<Result> localFetch(Long lastUpdate);

    MutableLiveData<Result> clearAllDb();

    MutableLiveData<Result> updateNews(News news);

    MutableLiveData<Result> updateNewsNotSaved(News news);

    void clearDatabase();

}
