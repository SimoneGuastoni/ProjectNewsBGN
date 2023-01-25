package com.example.projectnewsbgn.Database;

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

    @Query("SELECT * FROM news_table WHERE favourite = 1 ORDER BY publishedAt DESC")
    List<News> getAllFavouriteNews();

    @Query("SELECT * FROM news_table WHERE favourite = 0 ORDER BY publishedAt DESC")
    List<News> getAllNoFavoriteNews();

    @Query("SELECT 1 FROM news_table WHERE url == null")
    int getFreeIdRow();

    @Insert
    void insertNews(News news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long>insertNewsList(List<News> newsList);

    @Update
    void updateNews(News news);

    @Delete
    void databaseCleaner(List<News> newsListNoFav);

}
