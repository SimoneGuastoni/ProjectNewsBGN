package com.example.projectnewsbgn.Source;

import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.ApiService.CallNewsApi;
import com.example.projectnewsbgn.Utility.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRemoteDataSource extends BaseNewsRemoteDataSource {

    private final CallNewsApi callNewsApi;
    private final String apiKey;

    public NewsRemoteDataSource(String apiKey) {
        this.apiKey = apiKey;
        this.callNewsApi = ServiceLocator.getInstance().getNewsApiService();
    }

    //Fetch eseguita dal Home Fragment
    @Override
    public void getNews(String country, int page, long lastUpdate, List<String> topicList) {
        List<Call<NewsApiResponse>> listCallNewsApiResponse = new ArrayList<>();
        for (int i = 0; i < topicList.size(); i++) {
            listCallNewsApiResponse.add(callNewsApi.callHeadlines(country, topicList.get(i), 7, apiKey));
        }
        for (int p = 0; p < listCallNewsApiResponse.size(); p++) {
            try {
                listCallNewsApiResponse.get(p).enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.body() != null && response.isSuccessful() &&
                                !response.body().getStatus().equals("errorStatusResponseBody")) {
                            newsCallBack.onSuccessFromRemote(response.body(), System.currentTimeMillis());
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
           /*try {
                newsApiResponseCall.enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.body() != null && response.isSuccessful() &&
                                !response.body().getStatus().equals("errorStatusResponseBody")) {
                            newsCallBack.onSuccessFromRemote(response.body(), System.currentTimeMillis());
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
     }*/
    }


     //Fetch eseguita dal Search fragment su di un topic specifico
    @Override
    public void getNewsChoseTopic(String country, int page, String topic, String query) {
        Call<NewsApiResponse> newsApiResponseCall = callNewsApi.callHeadlines(country, topic, query,100,apiKey);

        try {
            newsApiResponseCall.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if (response.body() != null && response.isSuccessful() &&
                            !response.body().getStatus().equals("errorStatusResponseBody")) {
                        newsCallBack.onSuccessFromRemote(response.body());
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

    //Fetch eseguita dal Search Fragment su tutti i topic ma con un query specifica
    //TODO Sistemare fetch serch news
    @Override
    public void getNewsChoseTopicQuery(String country, int page, List<String> topicList, String query) {
        List<Call<NewsApiResponse>> listCallNewsApiResponse = new ArrayList<>();
        for (int i = 0; i < topicList.size(); i++) {
            listCallNewsApiResponse.add(callNewsApi.callHeadlines(country, topicList.get(i), query, 100, apiKey));
        }
        for (int p = 0; p < listCallNewsApiResponse.size(); p++) {
            try {
                listCallNewsApiResponse.get(p).enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.body() != null && response.isSuccessful() &&
                                !response.body().getStatus().equals("errorStatusResponseBody")) {
                            newsCallBack.onSuccessFromRemote(response.body());
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
}


