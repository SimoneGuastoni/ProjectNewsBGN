package com.example.projectnewsbgn.Source.NewsSource;

import com.example.projectnewsbgn.Database.NewsDao;
import com.example.projectnewsbgn.Database.NewsDatabase;
import com.example.projectnewsbgn.Models.News;

import java.util.List;


public class NewsLocalDataSource extends BaseNewsLocalDataSource{

    private final NewsDao newsDao;

    public NewsLocalDataSource(NewsDatabase newsDatabase) {
        this.newsDao = newsDatabase.newsDao();
    }


    //Metodi Get
    @Override
    public void getFavoriteNewsList() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> favList = newsDao.getAllFavouriteNews();
            if(favList.size() != 0){
                newsCallBack.onSuccessFromLocal(favList);
            }
            else
                newsCallBack.onFailureEmptyFavouriteList("Empty list");
        });
    }

    @Override
    public void getNewsFromDatabase(Long lastUpdate) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            newsCallBack.onSuccessFromLocal(newsDao.getAll(),lastUpdate);
        });
    }

    //Metodi Update
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
    public void updateDataOnDatabase(News news,boolean favourite) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            newsDao.updateNews(news);
            if(newsDao.getNews(news.getId()).getFavourite() == news.getFavourite()){
                newsCallBack.onNewsFavoriteStatusChanged(news,newsDao.getAllFavouriteNews());
            }
            else{
                newsCallBack.onFailureFromLocal("Change like error");
            }
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

    //Metodi Delete
    @Override
    public void deleteAllFavoriteNews() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> favListNews = newsDao.getAllFavouriteNews();
            for (int i=0 ; i < favListNews.size() ; i++){
                favListNews.get(i).setFavourite(false);
            }
            favListNews = replaceNewsList(favListNews);
            if (favListNews.size() == 0){
                newsCallBack.onNewsFavoriteStatusChanged(favListNews);
            }
            else
                newsCallBack.onFailureFromLocal("Delete error");
        });
    }

    @Override
    public void clearDatabase() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> listNoFavNews = newsDao.getAllNoFavoriteNews();
            List<News> listFavNews = newsDao.getAllFavouriteNews();
            int id=1;
            newsDao.databaseCleaner(listNoFavNews);
            for(News news:listFavNews){
                news.setId(id);
                id++;
                newsDao.updateNews(news);
            }
        });
    }

    @Override
    public void clearAllDatabase() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> allNews = newsDao.getAll();
            newsDao.databaseCleaner(allNews);
            if(newsDao.getAll().size() == 0){
                newsCallBack.onSuccessFromLocalClear(newsDao.getAll());
            }
            else{
                newsCallBack.onFailureFromRemote("Errore nel pulire il database");
            }
        });
    }

    //Metodi di supporto
    private void insertDataOnDatabase(News news) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> allNewsFromDb = newsDao.getAll();
            int controlCounter = 0;
            if(allNewsFromDb != null) {
                for (News newsSaved : allNewsFromDb) {
                    if (news.equals(newsSaved)) {
                        if (!newsSaved.getFavourite()){
                            news.setId(newsSaved.getId());
                            newsDao.updateNews(news);
                        }
                    }
                    else {
                        controlCounter++;
                    }
                }
                if (controlCounter == allNewsFromDb.size()){
                    int idFree = newsDao.getFreeIdRow();
                    news.setId(idFree);
                    newsDao.insertNews(news);
                }
            }
            else {
                news.setId(1);
                newsDao.insertNews(news);
            }
        });
    }

    // Metodi di supporto al precedente metodo updateNewsNotSaved per le news provenienti dal Search Fragment
    private void deleteFromDatabase(News news) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> allNewsFromDb = newsDao.getAll();
            if(allNewsFromDb != null) {
                for (News newsSaved : allNewsFromDb) {
                    if (news.equals(newsSaved)) {
                        if (newsSaved.getFavourite()){
                            news.setId(newsSaved.getId());
                            newsDao.updateNews(news);
                        }
                    }
                }
            }
            newsCallBack.onNewsFavoriteStatusChanged(news,newsDao.getAllFavouriteNews());
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
