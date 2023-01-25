package com.example.projectnewsbgn.Source.NewsSource;

import com.example.projectnewsbgn.Models.News;

import java.util.List;

public abstract class BaseNewsLocalDataSource {

    protected NewsCallBack newsCallBack;

    public void setNewsCallBack(NewsCallBack newsCallBack) {
        this.newsCallBack = newsCallBack;
    }

    //Metodi GET
    public abstract void getFavoriteNewsList();
    public abstract void getNewsFromDatabase(Long lastUpdate);

    //Metodi Update
    public abstract void updateNews(News news);
    public abstract void updateDataOnDatabase(News news, boolean favourite);
    public abstract void updateNewsNotSaved(News news);

    //Metodi Save/Delete
    public abstract void saveDataInDatabase(List<News> newsList);
    public abstract void deleteAllFavoriteNews();
    public abstract void clearDatabase();
}
