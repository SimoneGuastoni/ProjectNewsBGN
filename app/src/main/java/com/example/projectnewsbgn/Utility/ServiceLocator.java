package com.example.projectnewsbgn.Utility;

import android.app.Application;

import com.example.projectnewsbgn.ApiService.CallNewsApi;
import com.example.projectnewsbgn.Database.NewsDatabase;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Source.BaseNewsLocalDataSource;
import com.example.projectnewsbgn.Source.BaseNewsRemoteDataSource;
import com.example.projectnewsbgn.Repository.INewsRepositoryWithLiveData;
import com.example.projectnewsbgn.Source.NewsLocalDataSource;
import com.example.projectnewsbgn.Source.NewsRemoteDataSource;
import com.example.projectnewsbgn.Repository.NewsRepositoryWithLiveData;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {

    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    public CallNewsApi getNewsApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/").
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(CallNewsApi.class);
    }

    public NewsDatabase getNewsDao(Application application) {
        return NewsDatabase.getInstanceDatabase(application);
    }

    public INewsRepositoryWithLiveData getNewsRepository(Application application){
        BaseNewsRemoteDataSource baseNewsRemoteDataSource;
        BaseNewsLocalDataSource baseNewsLocalDataSource;

        baseNewsRemoteDataSource =new NewsRemoteDataSource(application.getString(R.string.api_key));

        baseNewsLocalDataSource =new NewsLocalDataSource(getNewsDao(application));

        return new NewsRepositoryWithLiveData(application, baseNewsRemoteDataSource,baseNewsLocalDataSource);
    }

}
