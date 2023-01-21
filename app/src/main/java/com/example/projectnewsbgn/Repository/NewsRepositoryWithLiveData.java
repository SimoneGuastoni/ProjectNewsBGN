package com.example.projectnewsbgn.Repository;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.Models.NewsResponse;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.Source.BaseNewsLocalDataSource;
import com.example.projectnewsbgn.Source.BaseNewsRemoteDataSource;
import com.example.projectnewsbgn.Source.NewsCallBack;
import com.example.projectnewsbgn.UI.homepage.MainActivity;

import java.util.List;

//questa classe recupera le news o in locale o in remoto

public class NewsRepositoryWithLiveData implements INewsRepositoryWithLiveData, NewsCallBack {

    private static final String TAG = NewsRepositoryWithLiveData.class.getSimpleName();

    private final Application application;
    private final MutableLiveData<Result> allNewsMutableLiveData;
    private final MutableLiveData<Result> favoriteNewsMutableLiveData;
    private final MutableLiveData<Result> topicChoseNewsList;
    private final BaseNewsLocalDataSource newsLocalDataSource;
    private final BaseNewsRemoteDataSource newsRemoteDataSource;

    public NewsRepositoryWithLiveData(Application application, BaseNewsRemoteDataSource newsRemoteDataSource, BaseNewsLocalDataSource newsLocalDataSource) {
        allNewsMutableLiveData = new MutableLiveData<>();
        favoriteNewsMutableLiveData = new MutableLiveData<>();
        topicChoseNewsList = new MutableLiveData<>();
        this.application = application;
        this.newsLocalDataSource = newsLocalDataSource;
        this.newsRemoteDataSource = newsRemoteDataSource;
        this.newsRemoteDataSource.setNewsCallBack(this);
        this.newsLocalDataSource.setNewsCallBack(this);
    }

    //Metodi per eseguire fetch delle news in locale o da remoto
    @Override
    public MutableLiveData<Result> fetchNews(String country, List<String> topicList, int page, long lastUpdate) {

        long currentTime = System.currentTimeMillis();

        if(lastUpdate == 0 || currentTime - lastUpdate > 20000) {
            SharedPreferences sharedPreferences = application.getSharedPreferences(MainActivity.SHARED_PREFS_FETCH, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            long time;
            time = System.currentTimeMillis();
            editor.putLong(String.valueOf(MainActivity.TIME), time);
            editor.apply();
            newsRemoteDataSource.getNews(country, 1, lastUpdate, topicList);
        }
        else {
            newsLocalDataSource.getNewsFromDatabase(lastUpdate);
        }
        return allNewsMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> fetchNewsChoseTopic(String country,int page,String topic, String query) {
        newsRemoteDataSource.getNewsChoseTopic(country,page,topic,query);
        return topicChoseNewsList;
    }

    @Override
    public MutableLiveData<Result> fetchNewsChoseTopicAndQuery(String country, int i, List<String> topicList, String query) {
        newsRemoteDataSource.getNewsChoseTopicQuery(country,i,topicList,query);
        return topicChoseNewsList;
    }

    @Override
    public MutableLiveData<Result> getFavoriteNewsList() {
        newsLocalDataSource.getFavoriteNewsList();
        return favoriteNewsMutableLiveData;
    }

    //Metodi per modificare lo stato di favorite delle news
    @Override
    public void updateNews(News news) {
        newsLocalDataSource.updateNews(news);
    }

    @Override
    public void updateNewsNotSaved(News news) {
        newsLocalDataSource.updateNewsNotSaved(news);
    }

    @Override
    public MutableLiveData<Result> deleteAllFavoriteNews() {
        newsLocalDataSource.deleteAllFavoriteNews();
        return favoriteNewsMutableLiveData;
    }


    // Metodi onSuccess
    @Override
    public void onSuccessFromRemote(NewsApiResponse newsApiResponse, long lastUpdate) {
        List<News> controlList = newsApiResponse.getArticles();
        newsLocalDataSource.saveDataInDatabase(controlList);
    }

    @Override
    public void onSuccessFromRemote(NewsApiResponse newsApiResponse) {
        List<News> controlList = newsApiResponse.getArticles();
        Result.Success result = new Result.Success(new NewsResponse(controlList));
        topicChoseNewsList.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(List<News> newsList) {
        Result.Success result = new Result.Success(new NewsResponse(newsList));
        favoriteNewsMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(List<News> newsList, Long lastUpdate) {
        Result.Success result = new Result.Success(new NewsResponse(newsList));
        allNewsMutableLiveData.postValue(result);
    }

    // Metodi onFailure

    @Override
    public void onFailureEmptyFavouriteList(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        favoriteNewsMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error("Api call fail");
        allNewsMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error result = new Result.Error("Error from local");
        favoriteNewsMutableLiveData.postValue(result);
        allNewsMutableLiveData.postValue(result);
    }

    //Metodi onSuccess dela modifica di stato di favorite
    @Override
    public void onNewsFavoriteStatusChanged(News news, List<News> favoriteNews) {
        Result result = allNewsMutableLiveData.getValue();
        favoriteNewsMutableLiveData.postValue(new Result.Success(new NewsResponse(favoriteNews)));
    }

    @Override
    public void onNewsFavoriteStatusChanged(List<News> emptyNewsList) {
        Result.Success result = new Result.Success(new NewsResponse(emptyNewsList));
        favoriteNewsMutableLiveData.postValue(result);
    }


}
