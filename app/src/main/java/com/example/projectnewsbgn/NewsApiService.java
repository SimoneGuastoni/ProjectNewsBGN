package com.example.projectnewsbgn;

import com.example.projectnewsbgn.Models.NewsApiResponse;

import retrofit2.Call;

public interface NewsApiService {
    Call<NewsApiResponse> getNews(String country,int pageSize,String apiKey);
}
