package com.example.projectnewsbgn.Repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Database;

import com.example.projectnewsbgn.UI.homepage.MainActivity;
import com.example.projectnewsbgn.Utility.CallNewsApi;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.Database.NewsDao;
import com.example.projectnewsbgn.Database.NewsDatabase;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Utility.ResponseCallback;
import com.example.projectnewsbgn.Utility.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository implements INewsRepository {

    private final Application application;
    private final ResponseCallback responseCallback;
    private final CallNewsApi callNewsApi;
    private final NewsDao newsDao;
    private List<News> newsList;
    private Context context;
    private long time;


    public NewsRepository(Application application,
                          ResponseCallback responseCallback) {
        this.application = application;
        this.callNewsApi = ServiceLocator.getInstance().getNewsApiService();
        NewsDatabase newsDatabase = ServiceLocator.getInstance().getNewsDao(application);
        this.newsDao = newsDatabase.newsDao();
        this.responseCallback = responseCallback;
        context = application.getBaseContext();
    }

/* Metodi della INewsRepository */


    @Override
    public void fetchNews(String country, int page, long lastUpdate,String topic,String query) {

        long currentTime = System.currentTimeMillis();

        if(lastUpdate == 0 || currentTime - lastUpdate > 20000) {

            /*Shared pref che mi permette di calcolare il tempo tra le fetch*/
            SharedPreferences sharedPreferences = application.getSharedPreferences(MainActivity.SHARED_PREFS_FETCH, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            time = System.currentTimeMillis();
            editor.putLong(String.valueOf(MainActivity.TIME),time);
            editor.apply();

            Call<NewsApiResponse> newsApiResponseCall = callNewsApi.callHeadlines(country, topic, query, application.getString(R.string.api_key));

            try {
                newsApiResponseCall.enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.body() != null && response.isSuccessful() &&
                                !response.body().getStatus().equals("errorStatusResponseBody")) {
                            newsList = response.body().getArticles();
                            saveDataInDatabase(newsList);
                        } else {
                            responseCallback.onFailure("Fetch_Error_onFailure");
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                        responseCallback.onFailure(t.getMessage());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            readDataFromDatabase(lastUpdate);
        }
    }


    @Override
    public void updateNews(News news) {
        if (news.getFavourite()){
            news.setFavourite(false);
            updateDatabase(news,news.getFavourite());
        }
        else {
            news.setFavourite(true);
            updateDatabase(news,news.getFavourite());
        }
    }

    @Override
    public void getFavouriteNews() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> testList = newsDao.getFavouriteNews();
            if(testList.size() != 0){
                responseCallback.onSuccess(testList, System.currentTimeMillis());
            }
            else
                responseCallback.onFailure("Nothing liked yet");
        });
    }

    @Override
    public void deleteFavouriteNews() {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> favListNews = newsDao.getFavouriteNews();
            for (int i=0 ; i < favListNews.size() ; i++){
                favListNews.get(i).setFavourite(false);
            }
            responseCallback.onSuccess(favListNews,System.currentTimeMillis());
        });
    }

/* Metodi che lavorano sul Database */

    private void saveDataInDatabase(List<News> newsList) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            List<News> allNewsFromDb = newsDao.getAll();
            for(News news : allNewsFromDb){
                if(newsList.contains(news)){
                    newsList.set(newsList.indexOf(news),news);
                }
            }
            List<Long> insertedNewsId = newsDao.insertNewsList(newsList);
            for(int i=0 ; i < newsList.size() ; i++){
                newsList.get(i).setId(insertedNewsId.get(i));
            }
            responseCallback.onSuccess(newsList,System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            responseCallback.onSuccess(newsDao.getAll(), lastUpdate);
        });
    }

    private void updateDatabase(News news, boolean favourite) {
        NewsDatabase.dataBaseWriteExecutor.execute(() -> {
            if(favourite){
                newsDao.updateNews(news);
                responseCallback.onNewsFavoriteStatusChange(news);
            }
            else{
                newsDao.updateNews(news);
                responseCallback.onNewsFavoriteStatusChange(news);
            }
        });
    }
}
