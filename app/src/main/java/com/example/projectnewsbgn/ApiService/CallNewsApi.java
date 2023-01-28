package com.example.projectnewsbgn.ApiService;

import com.example.projectnewsbgn.Models.NewsApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CallNewsApi {

    //Call che considera una query come input
    @GET("top-headlines")
    Call<NewsApiResponse> callHeadlines(
            @Query("country")String country,
            @Query("category")String category,
            @Query("q")String query,
            @Query("pageSize")int pageSize,
            @Query("apiKey")String apiKey
    );

    //Call standard senza query
    @GET("top-headlines")
    Call<NewsApiResponse> callHeadlines(
            @Query("country")String country,
            @Query("category")String category,
            @Query("pageSize")int pageSize,
            @Query("apiKey")String apiKey
    );

    //Call eseguita dalla searchBar su tutte le notizie disponibili, non solo su quelle più nuove
    @GET("everything")
    Call<NewsApiResponse> callHeadlinesQuery(
            @Query("language") String country,
            @Query("q") String query,
            @Query("pageSize") int pageSize,
            @Query("apiKey")String apiKey
    );
}
