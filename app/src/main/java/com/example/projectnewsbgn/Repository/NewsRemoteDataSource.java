package com.example.projectnewsbgn.Repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.UI.homepage.MainActivity;
import com.example.projectnewsbgn.Utility.CallNewsApi;
import com.example.projectnewsbgn.Utility.NewsApiService;
import com.example.projectnewsbgn.Utility.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRemoteDataSource extends BaseNewsRemoteDataSource{

    private final CallNewsApi callNewsApi;
    private final String apiKey;

    public NewsRemoteDataSource(String apiKey){
        this.apiKey = apiKey;
        this.callNewsApi = ServiceLocator.getInstance().getNewsApiService();
    }

    @Override
    public void getNews(String country, int page, long lastUpdate, String topic, String query) {
            Call<NewsApiResponse> newsApiResponseCall = callNewsApi.callHeadlines(country, topic, query,"7c0d484ec8d8410182e6ce2ea2c02f36");

            try {
                newsApiResponseCall.enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.body() != null && response.isSuccessful() &&
                                !response.body().getStatus().equals("errorStatusResponseBody")) {
                            newsCallBack.onSuccessFromRemote(response.body(),System.currentTimeMillis());
                        } else {
                            newsCallBack.onFailureFromRemote(new Exception("Error Missing Body"));
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                        newsCallBack.onFailureFromRemote(new Exception("Error from retrofit"));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

