package com.example.projectnewsbgn.Repository;

import com.example.projectnewsbgn.Models.News;
import java.util.ArrayList;
import java.util.List;

public interface INewsRepository {


    enum JsonParserType{
        JSON_READER,
        JSON_OBJECT_ARRAY,
        GSON,
        JSON_ERROR
    };

/* Esegue una Fetch delle news se rispetta la SharedPref del tempo, altrimenti le recupera dal database*/
    void fetchNews(String country,int page,long lastUpdate,String topic,String query);

    //fetch con una lista di topic
    void fetchNews(String country,int page,long lastUpdate,List<String> topic,String query);

/* Esegue la modifica dell'attributo favourite della news passata, sia settarla a true che false, stesso metodo
* , viene chiamata anche quando si cancella una news, perchè essa viene settata a false
* e quindi non rientra più nella ricerca */
    void updateNews(News news);

/* Esegue una ricerca nel database di tutte le news che hanno l'attributo favourite true */
    void getFavouriteNews();

    void deleteFavouriteNews();

}
