package com.example.projectnewsbgn.UI.homepage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Repository.NewsRepository.INewsRepositoryWithLiveData;
import com.example.projectnewsbgn.Models.Result;

import java.util.List;


public class NewsViewModel extends ViewModel {

    private final Application application;
    private final INewsRepositoryWithLiveData newsRepositoryWithLiveData;
    private final int page;
    private MutableLiveData<Result> newsListLiveData;
    //tra parentesi angolari è presente il tipo di dato che noi ci aspettiamo venga restituito dal liveData
    private MutableLiveData<Result> favoriteNewsListLiveData;
    private MutableLiveData<Result> topicChoseNewsList;

    public NewsViewModel(INewsRepositoryWithLiveData iNewsRepositoryWithLiveData,Application application){
        this.newsRepositoryWithLiveData = iNewsRepositoryWithLiveData;
        this.application = application;
        this.page = 100;
    }
    //Metodi per eseguire il recupero delle news in locale o in remoto

        //Fetch eseguita dal HomeFragment, al momento questa fetch viene effettuata ogni ora(3600000 ms)
    public MutableLiveData<Result> getNews(String country, List<String> topicList, long lastUpdate){
        long currentTime = System.currentTimeMillis();
        if(lastUpdate == 0 || currentTime - lastUpdate > 3600000) {
            SharedPreferences sharedPreferences = application.getSharedPreferences(MainActivity.SHARED_PREFS_FETCH, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(String.valueOf(MainActivity.TIME), currentTime);
            editor.apply();
            newsRepositoryWithLiveData.clearDatabase();
            fetchNews(country,topicList,lastUpdate);
        }
        else {
            if (newsListLiveData == null){
                localFetch(lastUpdate);
            }
        }
        return newsListLiveData;
    }

    public MutableLiveData<Result> getNewsFromDatabase(Long lastUpdate){
        if(newsListLiveData == null){
            localFetch(lastUpdate);
        }
        return newsListLiveData;
    }

    private void localFetch(Long lastUpdate) {
        newsListLiveData = newsRepositoryWithLiveData.localFetch(lastUpdate);
    }

    //Fetch eseguita dal SearchFrament mirato al topic
    public MutableLiveData<Result> getNews(String country,String topic,String query){
        fetchNewsWithTopic(country,topic,query);
        return topicChoseNewsList;
    }

        //Fetch eseguita dal Search fragment, più topic con una sola query
    public MutableLiveData<Result> getNews(String country,List<String> topicList,String query){
        fetchNewsWithTopicAndQuery(country,topicList,query);
        return topicChoseNewsList;
    }

        //Fetch eseguita dal FavoriteFragment
    public MutableLiveData<Result> getAllFavNews() {
        favoriteNewsListLiveData = newsRepositoryWithLiveData.getFavoriteNewsList();
        return favoriteNewsListLiveData;
    }

    //Metodi fetchNews rappresentano metodi di supporto ai metodi precedenti getNews
    private void fetchNewsWithTopicAndQuery(String country, List<String> topicList, String query) {
        topicChoseNewsList = newsRepositoryWithLiveData.fetchNewsChoseTopicAndQuery(country,page,topicList,query);
    }

    private void fetchNews(String country, List<String> topicList, long lastUpdate){
        newsListLiveData = newsRepositoryWithLiveData.fetchNews(country,topicList,page,lastUpdate);
    }

    private void fetchNewsWithTopic(String country,String topic,String query){
        topicChoseNewsList = newsRepositoryWithLiveData.fetchNewsChoseTopic(country,page,topic,query);
    }

    // Metodi per andare a modificare lo status di favorite
    public MutableLiveData<Result> updateNews(News news) {
        favoriteNewsListLiveData = newsRepositoryWithLiveData.updateNews(news);
        return favoriteNewsListLiveData;
    }

    private void deleteAllFavoriteNews(){
        favoriteNewsListLiveData = newsRepositoryWithLiveData.deleteCheckedFavoriteNews();
    }

    //Metodo per andare a salvare news specifiche che non sono state salvate ma a cui metto like
    public MutableLiveData<Result> updateNewsNotSaved(News news) {
        favoriteNewsListLiveData = newsRepositoryWithLiveData.updateNewsNotSaved(news);
        return favoriteNewsListLiveData;
    }

    public MutableLiveData<Result> clearAllDatabase() {
        return newsRepositoryWithLiveData.clearAllDb();
    }
}
