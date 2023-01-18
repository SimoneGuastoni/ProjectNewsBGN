package com.example.projectnewsbgn.UI.Main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectnewsbgn.Repository.INewsRepositoryWithLiveData;

import javax.xml.transform.Result;

public class NewsViewModel extends ViewModel {
    //prende il nome della classe
    private static final String TAG = NewsViewModel.class.getSimpleName();
    //la prima volta che il viewmodel verrà lanciato il currentName sarà a null, ovviamente

    private final INewsRepositoryWithLiveData newsRepositoryWithLiveData;
    private final int page;
    private MutableLiveData<Result> newsListLiveData;
    //tra parentesi angolari c'è il tipo di dato che noi ci aspettiamo venga restituito dal liveData
    private MutableLiveData<Result> favoriteNewsListLiveData;

    public NewsViewModel(INewsRepositoryWithLiveData iNewsRepositoryWithLiveData){
        this.newsRepositoryWithLiveData = iNewsRepositoryWithLiveData;
        this.page = 1;
    }
    //se il livedata è nullo cerca le news, altrimenti ritorna il livedata
    public MutableLiveData<Result> getNews(String country, long lastupdate){
        if(newsListLiveData == null){
            fetchNews(country, lastupdate);
        }
        return newsListLiveData;
    }

    public void updateNews(News news) {
        newsRepositoryWithLiveData.updateNews(news); //aggiungere tale classe

    }

    private void fetchNews(String country, long lastUpdate){
        newsListLiveData = newsRepositoryWithLiveData.fetchNews(country, page, lastUpdate);
    }

    /*
    private void getFavoriteNews(){
        favoriteNewsListLiveData = newsRepositoryWithLiveData.getFavoriteNews();
    }
    */

}
