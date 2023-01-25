package com.example.projectnewsbgn.Repository.AccountReposiroty;

import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.Result;

import java.util.Set;

public interface IAccountRepositoryWithLiveData {
    MutableLiveData<Result> getAccount(String email, String password, boolean isAccountRegistered);
    MutableLiveData<Result> getAccountFavoriteNews(String idToken);
    MutableLiveData<Result> getAccountPreferences(String idToken);
    MutableLiveData<Result> logout();
    Account getLoggedAccount();
    void signUp(String email, String password);
    void signIn(String email, String password);
    void saveAccountPreferences(String favoriteCountry, Set<String> favoriteTopics, String idToken);
}
