package com.example.projectnewsbgn.Repository.AccountReposiroty;

import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.Source.AccountSource.AccountCallBack;
import com.example.projectnewsbgn.Source.AccountSource.BaseAccountAuthenticationRemoteDataSource;
import com.example.projectnewsbgn.Source.AccountSource.BaseAccountInfoRemoteDataSource;
import com.example.projectnewsbgn.Source.NewsSource.BaseNewsLocalDataSource;
import com.example.projectnewsbgn.Source.NewsSource.NewsCallBack;

import java.util.List;
import java.util.Set;

public class AccountRepositoryWithLiveData implements IAccountRepositoryWithLiveData, AccountCallBack, NewsCallBack {

    private static final String TAG = AccountRepositoryWithLiveData.class.getSimpleName();

    private final BaseAccountAuthenticationRemoteDataSource baseAccountAuthenticationRemoteDataSource;
    private final BaseAccountInfoRemoteDataSource baseAccountInfoRemoteDataSource;
    private final BaseNewsLocalDataSource newsLocalDataSource;
    private final MutableLiveData<Result> accountMutableLiveData;
    private final MutableLiveData<Result> accountFavoriteNewsMutableLiveData;
    private final MutableLiveData<Result> accountPreferencesMutableLiveData;

    public AccountRepositoryWithLiveData(BaseAccountAuthenticationRemoteDataSource baseAccountAuthenticationRemoteDataSource,
                          BaseAccountInfoRemoteDataSource baseAccountInfoRemoteDataSource,
                          BaseNewsLocalDataSource newsLocalDataSource) {
        this.accountMutableLiveData = new MutableLiveData<>();
        this.accountPreferencesMutableLiveData = new MutableLiveData<>();
        this.accountFavoriteNewsMutableLiveData = new MutableLiveData<>();
        this.baseAccountAuthenticationRemoteDataSource = baseAccountAuthenticationRemoteDataSource;
        this.baseAccountInfoRemoteDataSource = baseAccountInfoRemoteDataSource;
        this.newsLocalDataSource = newsLocalDataSource;
        this.baseAccountInfoRemoteDataSource.setAccountCallBack(this);
        this.baseAccountInfoRemoteDataSource.setAccountCallBack(this);
        this.newsLocalDataSource.setNewsCallBack(this);
    }

    @Override
    public MutableLiveData<Result> getAccount(String email, String password, boolean isAccountRegistered) {
        return null;
    }

    @Override
    public MutableLiveData<Result> getAccountFavoriteNews(String idToken) {
        return null;
    }

    @Override
    public MutableLiveData<Result> getAccountPreferences(String idToken) {
        return null;
    }

    @Override
    public MutableLiveData<Result> logout() {
        return null;
    }

    @Override
    public Account getLoggedAccount() {
        return null;
    }

    @Override
    public void signUp(String email, String password) {

    }

    @Override
    public void signIn(String email, String password) {

    }

    @Override
    public void saveAccountPreferences(String favoriteCountry, Set<String> favoriteTopics, String idToken) {

    }

    @Override
    public void onSuccessFromAuthentication(Account account) {

    }

    @Override
    public void onFailureFromAuthentication(String message) {

    }

    @Override
    public void onSuccessLogout() {

    }

    @Override
    public void onSuccessFromRemoteDatabase(Account account) {

    }

    @Override
    public void onSuccessFromRemoteDatabase(List<News> newsList) {

    }

    @Override
    public void onSuccessFromGettingUserPreferences() {

    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {

    }

    @Override
    public void onSuccessFromRemote(List<News> newsList, long lastUpdate) {

    }

    @Override
    public void onSuccessFromRemote(NewsApiResponse newsApiResponse) {

    }

    @Override
    public void onSuccessFromRemote(List<News> newsList) {

    }

    @Override
    public void onFailureFromRemote(Exception exception) {

    }

    @Override
    public void onFailureFromLocal(Exception exception) {

    }

    @Override
    public void onSuccessFromLocal(List<News> newsList) {

    }

    @Override
    public void onSuccessFromLocal(List<News> newsList, Long lastUpdate) {

    }

    @Override
    public void onNewsFavoriteStatusChanged(News news, List<News> favoriteNews) {

    }

    @Override
    public void onNewsFavoriteStatusChanged(List<News> news) {

    }

    @Override
    public void onFailureEmptyFavouriteList(Exception exception) {

    }
}
