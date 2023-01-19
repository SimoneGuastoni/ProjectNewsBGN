package com.example.projectnewsbgn.Repository;


import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.Models.NewsResponse;

import java.util.List;

//questa classe recupera le news o in locale o in remoto

public class NewsRepositoryWithLiveData implements INewsRepositoryWithLiveData,NewsCallBack{

    private static final String TAG = NewsRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allNewsMutableLiveData;
    private final MutableLiveData<Result> favoriteNewsMutableLiveData;
    private final BaseNewsLocalDataSource newsLocalDataSource;
    private final BaseNewsRemoteDataSource newsRemoteDataSource;

    public NewsRepositoryWithLiveData(BaseNewsRemoteDataSource newsRemoteDataSource,BaseNewsLocalDataSource newsLocalDataSource) {
        allNewsMutableLiveData = new MutableLiveData<>();
        favoriteNewsMutableLiveData = new MutableLiveData<>();
        this.newsLocalDataSource = newsLocalDataSource;
        this.newsRemoteDataSource = newsRemoteDataSource;
        this.newsRemoteDataSource.setNewsCallBack(this);
        this.newsLocalDataSource.setNewsCallBack(this);
    }

    @Override
    public MutableLiveData<Result> fetchNews(String country, int page, long lastUpdate) {
        newsRemoteDataSource.getNews(country,1, lastUpdate,"general","");
        return allNewsMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getFavoriteNews() {
        return null;
    }

    @Override
    public void updateNews(News news) {

    }

    @Override
    public void deleteFavoriteNews() {

    }

    @Override
    public void onSuccessFromRemote(NewsApiResponse newsApiResponse, long lastUpdate) {
        List<News> controlList = newsApiResponse.getArticles();
        newsLocalDataSource.saveDataInDatabase(controlList);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error("Api call fail");
        allNewsMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(NewsApiResponse newsApiResponse) {

    }

    @Override
    public void onFailureFromLocal(Exception exception) {

    }

    @Override
    public void onSuccessFromLocal(List<News> newsList) {

    }

    @Override
    public void onSuccessFromLocal(List<News> newsList, Long lastUpdate) {
        Result.Success result = new Result.Success(new NewsResponse(newsList));
        allNewsMutableLiveData.postValue(result);
    }

    @Override
    public void onNewsFavoriteStatusChanged(News news, List<News> favoriteNews) {

    }

    @Override
    public void onNewsFavoriteStatusChanged(List<News> news) {

    }

    @Override
    public void onNewsFavoriteStatusChanged(News news) {

    }

    @Override
    public void onDeleteFavoriteNewsSuccess(List<News> favoriteNews) {

    }

    @Override
    public void onFailureEmptyFavouriteList(String message) {

    }
}
