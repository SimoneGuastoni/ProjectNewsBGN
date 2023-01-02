package com.example.projectnewsbgn;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.projectnewsbgn.Interface.OnFetchDataListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.homepage.RequestManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository implements INewsRepository {

    private Application application;
    private RequestManager manager;
    private ResponseCallback responseCallback;
    private CallNewsApi callNewsApi;
    private NewsDao newsDao;
    private LiveData<List<News>> newsList;


    public NewsRepository(Application application, RequestManager manager,
                          ResponseCallback responseCallback) {
        this.application = application;
        this.manager = manager;
        this.responseCallback = responseCallback;
        this.callNewsApi =ServiceLocator.getInstance().getNewsApiService();
        NewsDatabase newsDatabase = ServiceLocator.getInstance().getNewsDao(application);
        this.newsDao = newsDatabase.newsDao();
        this.newsList = newsList;
    }

    @Override
    public void fetchNews(String country, int page, long lastUpdate) {
        Call<NewsApiResponse> newsApiResponseCall = callNewsApi.callHeadlines(country,"general","", String.valueOf(R.string.api_key));

        try {
            newsApiResponseCall.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if (!response.isSuccessful()){
                        if (response.body() != null && response.isSuccessful() &&
                                !response.body().getStatus().equals("error")) {
                            List<News> newsList = response.body().getArticles();
                            saveDataInDatabase(newsList);
                        } else {
                            responseCallback.onFailure("FEtch_Error2");
                        }
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request Failed");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
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
/*
    private NewsDatabase createDatabase(Application application) {
        return NewsDatabase.getInstanceDatabase(application);
    }*/

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<News> data, String message) {
        }

        @Override
        public void onError(String message) {
        }
    };

    private void saveDataInDatabase(List<News> newsList) {

    }
}
