package com.example.projectnewsbgn.Repository.AccountReposiroty;

import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.Result;

import java.util.List;
import java.util.Set;

public interface IAccountRepositoryWithLiveData {
    MutableLiveData<Result> authentication(String accountName, String email,
                                           String password, String country, List<String> topicList);
    MutableLiveData<Result> getAccountFavoriteNews(String idToken);
    MutableLiveData<Result> getAccountPreferences(String idToken);
    MutableLiveData<Result> logout();
    MutableLiveData<Result> getLoggedAccount();
    void signUp(String accountName,String email, String password,String country,List<String> topicList);
    void login(String email, String password);
    void saveAccountPreferences(String favoriteCountry, Set<String> favoriteTopics, String idToken);
}
