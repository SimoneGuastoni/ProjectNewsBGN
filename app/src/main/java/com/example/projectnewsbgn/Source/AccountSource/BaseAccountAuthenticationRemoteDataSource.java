package com.example.projectnewsbgn.Source.AccountSource;

import com.example.projectnewsbgn.Models.Account;

import java.util.List;

public abstract class BaseAccountAuthenticationRemoteDataSource {
    protected AccountCallBack accountCallBack;

    public void setAccountCallBack(AccountCallBack accountCallBack) {
        this.accountCallBack = accountCallBack;
    }
    public abstract void getLoggedAccount();
    public abstract void logout();
    public abstract void signUp(String accountName,String email, String password,String country,List<String> topicList);
    public abstract void login(String email, String password);
}
