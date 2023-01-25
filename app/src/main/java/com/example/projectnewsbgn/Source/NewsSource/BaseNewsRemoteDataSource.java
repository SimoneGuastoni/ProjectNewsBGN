package com.example.projectnewsbgn.Source.NewsSource;

import java.util.List;

public abstract class BaseNewsRemoteDataSource {
    protected NewsCallBack newsCallBack;

    public void setNewsCallBack(NewsCallBack newsCallBack) {
        this.newsCallBack = newsCallBack;
    }

    public abstract void getNews(String country, int page,
                                 long lastUpdate, List<String> topicList);

    public abstract void getNewsChoseTopic(String country,int page,
                                           String topic,String query);

    public abstract void getNewsChoseTopicQuery(String country, int page,
                                                List<String> topicList, String query);

}
