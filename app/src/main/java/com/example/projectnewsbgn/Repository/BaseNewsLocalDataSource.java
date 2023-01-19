package com.example.projectnewsbgn.Repository;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;

import java.util.List;

public abstract class BaseNewsLocalDataSource {

    protected NewsCallBack newsCallBack;

    public void setNewsCallBack(NewsCallBack newsCallBack) {
        this.newsCallBack = newsCallBack;
    }

    public abstract void getFavoriteNews();
    public abstract void updateNews(News news);
    public abstract void deleteFavoriteNews();

    /* Metodi che lavorano su DB*/

    public abstract void saveDataInDatabase(List<News> newsList);
    public abstract void readDataFromDatabase(long lastUpdate);
    public abstract void updateDataOnDatabase(News news, boolean favourite);
}
