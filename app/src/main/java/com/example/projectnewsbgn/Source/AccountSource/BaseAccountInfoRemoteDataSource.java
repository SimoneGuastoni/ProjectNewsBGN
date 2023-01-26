package com.example.projectnewsbgn.Source.AccountSource;

import com.example.projectnewsbgn.Models.Account;

import java.util.Set;

public abstract class BaseAccountInfoRemoteDataSource {
    protected AccountCallBack accountCallBack;

    public void setAccountCallBack(AccountCallBack accountCallBack) {
        this.accountCallBack = accountCallBack;
    }

    public abstract void saveAccountData(Account account);
    public abstract void getAccountFavoriteNews(String idToken);
    public abstract void getAccountPreferences(String email,String id);
    public abstract void saveAccountPreferences(String favoriteCountry, Set<String> favoriteTopics, String idToken);
}
