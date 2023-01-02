package com.example.projectnewsbgn;

import android.app.Application;

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
}
