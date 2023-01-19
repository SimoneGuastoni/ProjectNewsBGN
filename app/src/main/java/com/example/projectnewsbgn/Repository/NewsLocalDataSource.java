package com.example.projectnewsbgn.Repository;

import com.example.projectnewsbgn.Database.NewsDao;
import com.example.projectnewsbgn.Database.NewsDatabase;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;

import java.util.List;


public class NewsLocalDataSource extends BaseNewsLocalDataSource{

    private final NewsDao newsDao;

    public NewsLocalDataSource(NewsDatabase newsDatabase) {
        this.newsDao = newsDatabase.newsDao();
    }


    @Override
    public void getFavoriteNews() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> testList = newsDao.getFavouriteNews();
            if(testList.size() != 0){
                newsCallBack.onDeleteFavoriteNewsSuccess(testList);
            }
            else
                newsCallBack.onFailureEmptyFavouriteList("Nothing liked yet");
        });
    }

    @Override
    public void updateNews(News news) {
        if (news.getFavourite()){
            news.setFavourite(false);
            updateDataOnDatabase(news,news.getFavourite());
        }
        else {
            news.setFavourite(true);
            updateDataOnDatabase(news,news.getFavourite());
        }
    }

    @Override
    public void deleteFavoriteNews() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> favListNews = newsDao.getFavouriteNews();
            for (int i=0 ; i < favListNews.size() ; i++){
                favListNews.get(i).setFavourite(false);
            }
            newsCallBack.onNewsFavoriteStatusChanged(favListNews);
        });
    }

    @Override
    public void saveDataInDatabase(List<News> newsList) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> allNewsFromDb = newsDao.getAll();
            if(allNewsFromDb != null) {
                for (News news : allNewsFromDb) {
                    if (newsList.contains(news)) {
                        newsList.set(newsList.indexOf(news), news);
                    }
                }
                List<Long> insertedNewsId = newsDao.insertNewsList(newsList);
                for (int i = 0; i < newsList.size(); i++) {
                    newsList.get(i).setId(insertedNewsId.get(i));
                }

            newsCallBack.onSuccessFromLocal(newsList,System.currentTimeMillis());
        }});
    }

    @Override
    public void readDataFromDatabase(long lastUpdate) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            newsCallBack.onSuccessFromLocal(newsDao.getAll());
        });
    }

    @Override
    public void updateDataOnDatabase(News news,boolean favourite) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            if(favourite){
                newsDao.updateNews(news);
                newsCallBack.onNewsFavoriteStatusChanged(news);
            }
            else{
                newsDao.updateNews(news);
                newsCallBack.onNewsFavoriteStatusChanged(news);
            }
        });
    }

}
