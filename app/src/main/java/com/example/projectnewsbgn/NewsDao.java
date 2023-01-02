package com.example.projectnewsbgn;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectnewsbgn.Models.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news_table ORDER BY publishedAt DESC")
    LiveData<List<News>> getAll();

    @Query("SELECT * FROM news_table WHERE id = :id")
    News getNews(int id);

    @Query("SELECT * FROM news_table WHERE favourite = 1 ORDER BY publishedAt DESC")
    LiveData<List<News>> getFavouriteNews();

    @Insert
    void insertNews(News news);

    /* Update LIVEDATA? TODO */
    @Update
    void updateNews(News news);

    @Update
    void updateFavouriteNews(News news);

    @Delete
    void deleteAllFavouriteNews(News news);

    @Delete
    void deleteNews(News news);
}
