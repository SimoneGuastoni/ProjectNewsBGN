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
        this.baseAccountAuthenticationRemoteDataSource.setAccountCallBack(this);
        this.baseAccountInfoRemoteDataSource.setAccountCallBack(this);
        this.newsLocalDataSource.setNewsCallBack(this);
    }

    @Override
    public MutableLiveData<Result> authentication(String accountName, String email, String password,
                                                  String country, List<String> topicList) {
        if(accountName.equals("")){
            login(email,password);
        }
        else{
            signUp(accountName,email,password,country,topicList);
        }
        return accountMutableLiveData;
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
    public MutableLiveData<Result> getLoggedAccount() {
        baseAccountAuthenticationRemoteDataSource.getLoggedAccount();
        return accountMutableLiveData;
    }

    @Override
    public void signUp(String accountName,String email, String password,
                       String country,List<String> topicList) {
        baseAccountAuthenticationRemoteDataSource.signUp(accountName,email,password,country,topicList);
    }

    @Override
    public void login(String email, String password) {
        baseAccountAuthenticationRemoteDataSource.login(email,password);
    }

    @Override
    public void saveAccountPreferences(String favoriteCountry, Set<String> favoriteTopics, String idToken) {

    }

    @Override
    public void onSuccessFromAuthentication(Account account) {
        if(account != null){
            baseAccountInfoRemoteDataSource.saveAccountData(account);
        }
    }

    @Override
    public void onSuccessFromAuthentication(String email, String id) {
        if (email != null && id !=null){
            baseAccountInfoRemoteDataSource.getAccountPreferences(email,id);
        }
    }

    @Override
    public void onFailureFromAuthentication(String message) {
        Result.Error result = new Result.Error(message);
        accountMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessLogout() {

    }

    @Override
    public void onSuccessFromRemoteDatabase(Account account) {
        Result.AccountSuccess result = new Result.AccountSuccess(account);
        accountMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(String id, String accountName, String email, String country, List<String> topicList) {
        Account accountCopy = new Account(id,accountName,email,country,topicList);
        Result.AccountSuccess result = new Result.AccountSuccess(accountCopy);
        accountMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(List<News> newsList) {

    }

    @Override
    public void onSuccessFromGettingUserPreferences() {

    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        accountMutableLiveData.postValue(result);
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
