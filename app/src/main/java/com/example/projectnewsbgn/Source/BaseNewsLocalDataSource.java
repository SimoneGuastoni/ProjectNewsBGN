package com.example.projectnewsbgn.Source;

import com.example.projectnewsbgn.Models.News;

import java.util.List;

public abstract class BaseNewsLocalDataSource {

    protected NewsCallBack newsCallBack;

    public void setNewsCallBack(NewsCallBack newsCallBack) {
        this.newsCallBack = newsCallBack;
    }

    public abstract void getFavoriteNewsList();
    public abstract void updateNews(News news);
    public abstract void deleteAllFavoriteNews();
    public abstract void saveDataInDatabase(List<News> newsList);
    public abstract void updateDataOnDatabase(News news, boolean favourite);
    public abstract void updateNewsNotSaved(News news);
    public abstract void getNewsFromDatabase(long lastUpdate);
}
