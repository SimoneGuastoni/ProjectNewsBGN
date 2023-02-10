package com.example.projectnewsbgn.Source.AccountSource;

import com.example.projectnewsbgn.Models.Account;

import java.util.List;

public abstract class BaseAccountInfoRemoteDataSource {
    protected AccountCallBack accountCallBack;

    public void setAccountCallBack(AccountCallBack accountCallBack) {
        this.accountCallBack = accountCallBack;
    }

    public abstract void saveAccountData(Account account);
    public abstract void getAccountPreferences(String email,String id);
    public abstract void changeAccountDataRemote(String accountId,String email,String newName, String newCountry, List<String> newTopicList);
}
