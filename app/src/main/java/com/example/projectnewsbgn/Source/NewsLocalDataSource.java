package com.example.projectnewsbgn.Source;

import com.example.projectnewsbgn.Database.NewsDao;
import com.example.projectnewsbgn.Database.NewsDatabase;
import com.example.projectnewsbgn.Models.News;

import java.util.List;


public class NewsLocalDataSource extends BaseNewsLocalDataSource{

    private final NewsDao newsDao;

    public NewsLocalDataSource(NewsDatabase newsDatabase) {
        this.newsDao = newsDatabase.newsDao();
    }


    @Override
    public void getFavoriteNewsList() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> favList = newsDao.getFavouriteNews();
            if(favList.size() != 0){
                newsCallBack.onSuccessFromLocal(favList);
            }
            else
                newsCallBack.onFailureEmptyFavouriteList(new Exception("Empty list"));
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
    public void deleteAllFavoriteNews() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> favListNews = newsDao.getFavouriteNews();
            for (int i=0 ; i < favListNews.size() ; i++){
                favListNews.get(i).setFavourite(false);
            }
            favListNews = replaceNewsList(favListNews);
            if (favListNews.size() == 0){
                newsCallBack.onNewsFavoriteStatusChanged(favListNews);
            }
            else
                newsCallBack.onFailureFromLocal(new Exception("Delete error"));
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

                newsCallBack.onSuccessFromLocal(newsList, System.currentTimeMillis());
            }
        });
    }

    @Override
    public void updateDataOnDatabase(News news,boolean favourite) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            newsDao.updateNews(news);
            newsCallBack.onNewsFavoriteStatusChanged(news,newsDao.getFavouriteNews());
        });
    }

    @Override
    public void updateNewsNotSaved(News news) {
        if (news.getFavourite()){
            news.setFavourite(false);
            deleteFromDatabase(news);
        }
        else {
            news.setFavourite(true);
            insertDataOnDatabase(news);
        }
    }

    @Override
    public void getNewsFromDatabase(long lastUpdate) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            newsCallBack.onSuccessFromLocal(newsDao.getAll(),lastUpdate);
        });
    }

    // Metodi di supporto al precedente metodo updateNewsNotSaved per le news provenienti dal Search Fragment
    private void deleteFromDatabase(News news) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            newsDao.deleteNews(news);
        });
    }

    private void insertDataOnDatabase(News news) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            newsDao.insertNews(news);
        });
    }

    //Metodo di supporto al precedente metodo deleteAllFavoriteNews
    private List<News> replaceNewsList(List<News> favListNews) {
        List<News> allNews = newsDao.getAll();
        for (int e=0; e<favListNews.size();e++){
            for (int i=0; i < allNews.size(); i++ ) {
                if (allNews.get(i).equals(favListNews.get(e))) {
                    allNews.remove(i);
                    allNews.add(favListNews.get(e));
                    favListNews.remove(e);
                }
            }
        }
        return favListNews;
    }


}
