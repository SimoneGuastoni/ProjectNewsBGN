package com.example.projectnewsbgn.Repository.NewsRepository;


import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.Models.NewsResponse;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.Source.NewsSource.BaseNewsLocalDataSource;
import com.example.projectnewsbgn.Source.NewsSource.BaseNewsRemoteDataSource;
import com.example.projectnewsbgn.Source.NewsSource.NewsCallBack;

import java.util.List;

/* La repository si occupa di decidere se mandare al ViewModel
   delle notizie prelevate localmente o tramite fetch news chiamando la
   classe apposita che si occupa della chiamata all'API */

public class NewsRepositoryWithLiveData implements INewsRepositoryWithLiveData, NewsCallBack {

    private static final String TAG = NewsRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allNewsMutableLiveData;
    private final MutableLiveData<Result> favoriteNewsMutableLiveData;
    private final MutableLiveData<Result> topicChoseNewsList;
    private final BaseNewsLocalDataSource newsLocalDataSource;
    private final BaseNewsRemoteDataSource newsRemoteDataSource;

    public NewsRepositoryWithLiveData(BaseNewsRemoteDataSource newsRemoteDataSource, BaseNewsLocalDataSource newsLocalDataSource) {
        allNewsMutableLiveData = new MutableLiveData<>();
        favoriteNewsMutableLiveData = new MutableLiveData<>();
        topicChoseNewsList = new MutableLiveData<>();
        this.newsLocalDataSource = newsLocalDataSource;
        this.newsRemoteDataSource = newsRemoteDataSource;
        this.newsRemoteDataSource.setNewsCallBack(this);
        this.newsLocalDataSource.setNewsCallBack(this);
    }

    //Metodi per eseguire fetch delle news in locale o da remoto
    @Override
    public MutableLiveData<Result> fetchNews(String country, List<String> topicList, int page, long lastUpdate) {
        newsRemoteDataSource.getNews(country,page,lastUpdate,topicList);
        return allNewsMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> fetchNewsChoseTopic(String country,int page,String topic, String query) {
        newsRemoteDataSource.getNewsChoseTopic(country,page,topic,query);
        return topicChoseNewsList;
    }

    @Override
    public MutableLiveData<Result> fetchNewsChoseTopicAndQuery(String country, int page, List<String> topicList, String query) {
        newsRemoteDataSource.getNewsChoseTopicQuery(country, page,topicList,query);
        return topicChoseNewsList;
    }

    @Override
    public MutableLiveData<Result> localFetch(Long lastUpdate) {
        newsLocalDataSource.getNewsFromDatabase(lastUpdate);
        return allNewsMutableLiveData;
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

    //Metodo per pulire il database da tutte le news che non hanno uno like
    @Override
    public void clearDatabase() {
        newsLocalDataSource.clearDatabase();
    }


    // Metodi onSuccess
    @Override
    public void onSuccessFromRemote(List<News> newsList, long lastUpdate) {
        newsLocalDataSource.saveDataInDatabase(newsList);
    }

    @Override
    public void onSuccessFromRemote(NewsApiResponse newsApiResponse) {
        List<News> controlList = newsApiResponse.getArticles();
        Result.Success result = new Result.Success(new NewsResponse(controlList));
        topicChoseNewsList.postValue(result);
    }

    @Override
    public void onSuccessFromRemote(List<News> newsList) {
        Result.Success result = new Result.Success(new NewsResponse(newsList));
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
