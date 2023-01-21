package com.example.projectnewsbgn.UI.Main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Repository.INewsRepositoryWithLiveData;
import com.example.projectnewsbgn.Models.Result;

import java.util.List;


public class NewsViewModel extends ViewModel {
    //prende il nome della classe
    private static final String TAG = NewsViewModel.class.getSimpleName();
    //la prima volta che il viewmodel verrà lanciato il currentName sarà a null, ovviamente

    private final INewsRepositoryWithLiveData newsRepositoryWithLiveData;
    private final int page;
    private MutableLiveData<Result> newsListLiveData;
    //tra parentesi angolari c'è il tipo di dato che noi ci aspettiamo venga restituito dal liveData
    private MutableLiveData<Result> favoriteNewsListLiveData;
    private MutableLiveData<Result> topicChoseNewsList;

    public NewsViewModel(INewsRepositoryWithLiveData iNewsRepositoryWithLiveData){
        this.newsRepositoryWithLiveData = iNewsRepositoryWithLiveData;
        this.page = 1;
    }
    //Metodi per eseguire il recupero delle news in locale o in remoto

        //Fetch eseguita dal HomeFragment
    public MutableLiveData<Result> getNews(String country, List<String> topicList, long lastupdate){
        //TODO Inserire controllo
        if(newsListLiveData == null){
            fetchNews(country,topicList,lastupdate);
        }
        /* operazione di controllo */
        Result controlList = newsListLiveData.getValue();
        return newsListLiveData;
    }

        //Fetch eseguita dal SearchFrament mirato al topic
    public MutableLiveData<Result> getNews(String country,String topic,String query){
        fetchNewsWithTopic(country,topic,query);
        return topicChoseNewsList;
    }

        //Fetch eseguita dal Search fragment, più topic con una sola query
    public MutableLiveData<Result> getNews(String country,List<String> topicList,String query){
        fetchNewsWithTopicAndQuery(country,topicList,query);
        return topicChoseNewsList;
    }

        //Fetch eseguita dal FavoriteFragment
    public MutableLiveData<Result> getAllFavNews() {
        favoriteNewsListLiveData = newsRepositoryWithLiveData.getFavoriteNewsList();
        return favoriteNewsListLiveData;
    }

    //Metodi fetchNews rappresentano metodi di supporto ai metodi precedenti getNews
    private void fetchNewsWithTopicAndQuery(String country, List<String> topicList, String query) {
        topicChoseNewsList = newsRepositoryWithLiveData.fetchNewsChoseTopicAndQuery(country,1,topicList,query);
    }

    private void fetchNews(String country, List<String> topicList, long lastUpdate){
        newsListLiveData = newsRepositoryWithLiveData.fetchNews(country,topicList,page,lastUpdate);
    }

    private void fetchNewsWithTopic(String country,String topic,String query){
        topicChoseNewsList = newsRepositoryWithLiveData.fetchNewsChoseTopic(country,1,topic,query);
    }

    // Metodi per andare a modificare lo status di favorite
    public void updateNews(News news) {
        newsRepositoryWithLiveData.updateNews(news);
    }

    private void deleteAllFavoriteNews(){
        favoriteNewsListLiveData = newsRepositoryWithLiveData.deleteAllFavoriteNews();
    }

    //Metodo per andare a salvare news specifiche che non sono state salvate ma a cui metto like
    public void updateNewsNotSaved(News news) {
        newsRepositoryWithLiveData.updateNewsNotSaved(news);
    }
}
