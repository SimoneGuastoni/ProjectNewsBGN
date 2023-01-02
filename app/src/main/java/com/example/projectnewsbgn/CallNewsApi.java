package com.example.projectnewsbgn;

import com.example.projectnewsbgn.Models.NewsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CallNewsApi {
    @GET("top-headlines")
    Call<NewsApiResponse> callHeadlines(
            @Query("country")String country,
            @Query("category")String category,
            @Query("q")String query,
            @Query("apiKey")String apiKey
    );
}
