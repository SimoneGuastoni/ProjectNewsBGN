package com.example.projectnewsbgn.Database;

import android.support.annotation.WorkerThread;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectnewsbgn.Models.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news_table ORDER BY publishedAt DESC")
    List<News> getAll();

    @Query("SELECT * FROM news_table WHERE id = :id")
    News getNews(int id);

    @Query("SELECT * FROM news_table WHERE favourite = 1 ORDER BY publishedAt DESC")
    List<News> getFavouriteNews();

    @Query("SELECT * FROM news_table WHERE favourite = 0 ORDER BY publishedAt DESC")
    List<News> getAllNoFavoriteNews();

    @Insert
    void insertNews(News news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long>insertNewsList(List<News> newsList);

    @Update
    void updateNews(News news);

    @Update
    void updateFavouriteNews(News news);

    @Delete
    void databaseCleaner(List<News> newsListNoFav);

    @Delete
    void deleteAllFavouriteNews(News news);

    @Delete
    void deleteNews(News news);


}
