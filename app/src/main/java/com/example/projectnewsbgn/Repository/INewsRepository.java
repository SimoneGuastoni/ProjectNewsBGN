package com.example.projectnewsbgn.Repository;

import com.example.projectnewsbgn.Models.News;

import java.util.List;

public interface INewsRepository {


    enum JsonParserType{
        JSON_READER,
        JSON_OBJECT_ARRAY,
        GSON,
        JSON_ERROR
    };


    void fetchNews(String country,int page,long lastUpdate);

    void updateNews(News news);

    void getFavouriteNews();

    void deleteFavouriteNews();

}
