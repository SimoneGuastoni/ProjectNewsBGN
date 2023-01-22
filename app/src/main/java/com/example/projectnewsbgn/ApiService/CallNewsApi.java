package com.example.projectnewsbgn.ApiService;

import com.example.projectnewsbgn.Models.NewsApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CallNewsApi {
    @GET("top-headlines")
    Call<NewsApiResponse> callHeadlines(
            @Query("country")String country,
            @Query("category")String category,
            @Query("q")String query,
            @Query("pageSize")int pageSize,
            @Query("apiKey")String apiKey
    );

    @GET("top-headlines")
    Call<NewsApiResponse> callHeadlines(
            @Query("country")String country,
            @Query("category")String category,
            @Query("pageSize")int pageSize,
            @Query("apiKey")String apiKey
    );

    /* Test multi category */
    @GET("top-headlines")
    Call<NewsApiResponse> callHeadlines(
            @Query("country")String country,
            @Query("category")List<String> category,
            @Query("q") String query,
            @Query("pageSize")int pageSize,
            @Query("apiKey")String apiKey
    );
}
