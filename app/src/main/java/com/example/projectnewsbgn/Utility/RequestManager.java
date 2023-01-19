package com.example.projectnewsbgn.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


import com.example.projectnewsbgn.UI.homepage.MainActivity;
import com.example.projectnewsbgn.Utility.CallNewsApi;
import com.example.projectnewsbgn.Interface.OnFetchDataListener;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {


    Context context;
    long time;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getNewsHeadlines(OnFetchDataListener<NewsApiResponse> listener, String category, String query,String country){

        /* Shared pref che mi permette di calcolare il tempo tra le fetch*/
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREFS_FETCH,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        time = System.currentTimeMillis();
        editor.putLong(String.valueOf(MainActivity.TIME),time);
        editor.apply();

        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);

        Call<NewsApiResponse> call = callNewsApi.callHeadlines(country, category, query, context.getString(R.string.api_key));

        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                    /*listener.onFetchData(response.body().getArticles(), response.message());*/
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request Failed");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public Call<NewsApiResponse> getNewsHeadlinesRepository(OnFetchDataListener<NewsApiResponse> listener, String category, String query, String country){

        /* Shared pref che mi permette di calcolare il tempo tra le fetch*/
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREFS_FETCH,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        time = System.currentTimeMillis();
        editor.putLong(String.valueOf(MainActivity.TIME),time);
        editor.apply();

        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);

        Call<NewsApiResponse> call = callNewsApi.callHeadlines(country, category, query, context.getString(R.string.api_key));

        return call;
    }

}
