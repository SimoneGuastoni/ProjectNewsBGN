package com.example.projectnewsbgn.Utility;

import android.app.Application;

import com.example.projectnewsbgn.ApiService.CallNewsApi;
import com.example.projectnewsbgn.Database.NewsDatabase;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Repository.AccountRepository.AccountRepositoryWithLiveData;
import com.example.projectnewsbgn.Repository.AccountRepository.IAccountRepositoryWithLiveData;
import com.example.projectnewsbgn.Source.AccountSource.AccountAuthenticationRemoteDataSource;
import com.example.projectnewsbgn.Source.AccountSource.AccountInfoRemoteDataSource;
import com.example.projectnewsbgn.Source.AccountSource.BaseAccountAuthenticationRemoteDataSource;
import com.example.projectnewsbgn.Source.AccountSource.BaseAccountInfoRemoteDataSource;
import com.example.projectnewsbgn.Source.NewsSource.BaseNewsLocalDataSource;
import com.example.projectnewsbgn.Source.NewsSource.BaseNewsRemoteDataSource;
import com.example.projectnewsbgn.Repository.NewsRepository.INewsRepositoryWithLiveData;
import com.example.projectnewsbgn.Source.NewsSource.NewsLocalDataSource;
import com.example.projectnewsbgn.Source.NewsSource.NewsRemoteDataSource;
import com.example.projectnewsbgn.Repository.NewsRepository.NewsRepositoryWithLiveData;

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

    //Creazione repository per le news
    public INewsRepositoryWithLiveData getNewsRepository(Application application){
        BaseNewsRemoteDataSource baseNewsRemoteDataSource;
        BaseNewsLocalDataSource baseNewsLocalDataSource;

        baseNewsRemoteDataSource =new NewsRemoteDataSource(application.getString(R.string.api_key));

        baseNewsLocalDataSource =new NewsLocalDataSource(getNewsDao(application));

        return new NewsRepositoryWithLiveData(baseNewsRemoteDataSource,baseNewsLocalDataSource);
    }

    //Creazione repository per gli account
    //TODO rimuovere l'application dal metodo?
    public IAccountRepositoryWithLiveData getAccountRepository(Application application){
        BaseAccountAuthenticationRemoteDataSource baseAccountAuthenticationRemoteDataSource;
        BaseAccountInfoRemoteDataSource baseAccountInfoRemoteDataSource;

        baseAccountAuthenticationRemoteDataSource = new AccountAuthenticationRemoteDataSource();
        baseAccountInfoRemoteDataSource = new AccountInfoRemoteDataSource();

        return new AccountRepositoryWithLiveData(baseAccountAuthenticationRemoteDataSource,
                baseAccountInfoRemoteDataSource);
    }

}
