package com.example.projectnewsbgn.Repository;

public abstract class BaseNewsRemoteDataSource {
    protected NewsCallBack newsCallBack;

    public void setNewsCallBack(NewsCallBack newsCallBack) {
        this.newsCallBack = newsCallBack;
    }

    public abstract void getNews(String country,int page, long lastUpdate,String topic,String query);
}
