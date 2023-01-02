package com.example.projectnewsbgn;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.projectnewsbgn.Interface.OnFetchDataListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.homepage.RequestManager;
import com.example.projectnewsbgn.NewsDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository implements INewsRepository {

    private final Application application;
    /*private RequestManager manager;*/
    private final ResponseCallback responseCallback;
    private final CallNewsApi callNewsApi;
    private final NewsDao newsDao;
    private List<News> newsList;


    public NewsRepository(Application application, /*RequestManager manager,*/
                          ResponseCallback responseCallback) {
        this.application = application;
        /*this.manager = manager;*/
        this.callNewsApi =ServiceLocator.getInstance().getNewsApiService();
        NewsDatabase newsDatabase = ServiceLocator.getInstance().getNewsDao(application);
        this.newsDao = newsDatabase.newsDao();
        this.responseCallback = responseCallback;
    }

    @Override
    public void fetchNews(String country, int page, long lastUpdate) {
        Call<NewsApiResponse> newsApiResponseCall = callNewsApi.callHeadlines(country,"general","", String.valueOf(R.string.api_key));

        try {
            newsApiResponseCall.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.body() != null && response.isSuccessful() &&
                                !response.body().getStatus().equals("errorStatusResponseBody")) {
                            newsList = response.body().getArticles();
                            saveDataInDatabase(newsList);
                        } else {
                            responseCallback.onFailure("Fetch_Error_onFailure");
                        }
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    responseCallback.onFailure(t.getMessage());
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


    private void saveDataInDatabase(List<News> newsList) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> allNewsFromDb = newsDao.getAll();
            for(News news : allNewsFromDb){
                if(newsList.contains(news)){
                    newsList.set(newsList.indexOf(news),news);
                }
            }
            List<Long> insertedNewsId = newsDao.insertNewsList(newsList);
            for(int i=0 ; i < newsList.size() ; i++){
                newsList.get(i).setId(insertedNewsId.get(i));
            }
            responseCallback.onSuccess(newsList,System.currentTimeMillis());
        });
    }
}
