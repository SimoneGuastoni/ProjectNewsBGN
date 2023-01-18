package com.example.projectnewsbgn.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.R;

import retrofit2.Call;

//questa classe recupera le news o in locale o in remoto

public class NewsRepositoryWithLiveData {
    private static final String TAG = NewsRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allNewsMutableLiveData;
    private final MutableLiveData<Result> favoriteNewsMutableLiveData;
    private final BaseNewsRemoteDataSource newsRemoteDataSource;
    private final BaseNewsLocalDataSource newsLocalDataSource;

    public NewsRepositoryWithLiveData(BaseNewsRemoteDataSource newsRemoteDataSource,
                                      BaseNewsLocalDataSource newsLocalDataSource) {

        allNewsMutableLiveData = new MutableLiveData<>();
        //favoriteNewsMutableLiveData = new MutableLiveData<>();
        this.newsRemoteDataSource = newsRemoteDataSource;
        this.newsLocalDataSource = newsLocalDataSource;
        this.newsRemoteDataSource.setNewsCallback(this);
        this.newsLocalDataSource.setNewsCallback(this);

        @Override
        public MutableLiveData<Result> fetchNews(String country, int page, long lastupdate, String topic, String query){
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastUpdate > FRESH_TIMEOUT) {
                //newsRemoteDataSource.getNews(country);
                Call<NewsApiResponse> newsApiResponseCall = callNewsApi.callHeadlines(country, topic, query, application.getString(R.string.api_key));

            } else {
                newsLocalDataSource.getNews();
            }
            return allNewsMutableLiveData;
        }
    }

}
