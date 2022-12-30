package com.example.projectnewsbgn;

import android.app.Application;
import android.telecom.Call;
import android.telephony.TelephonyManager;

import androidx.lifecycle.LiveData;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.homepage.RequestManager;

import java.util.List;

import javax.security.auth.callback.Callback;

public class NewsRepository implements INewsRepository{

    private Application application;
    private ResponseCallback responseCallback;
    private NewsDao newsDao;
    private LiveData<List<News>> newsList;


    public NewsRepository(Application application, ResponseCallback responseCallback, NewsDao newsDao, LiveData<List<News>> newsList) {
        this.application = application;
        this.responseCallback = responseCallback;
        this.newsDao = newsDao;
        this.newsList = newsList;
    }

    @Override
    public void fetchNews(String country, int page, long lastUpdate) {

        long currentTime =System.currentTimeMillis();

       if (currentTime - lastUpdate > 10000){
       }
    }

    @Override
    public void updateNews(News news) {

    }

    @Override
    public void getFavouriteNews() {

    }

    @Override
    public void deleteFavouriteNews() {

    }
}
