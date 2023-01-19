package com.example.projectnewsbgn.Repository;

import com.example.projectnewsbgn.Models.News;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;
public interface    INewsRepositoryWithLiveData {
    MutableLiveData<Result> fetchNews(String country, int page, long lastUpdate);

    MutableLiveData<Result> getFavoriteNews();

    void updateNews(News news);

    void deleteFavoriteNews();

}
