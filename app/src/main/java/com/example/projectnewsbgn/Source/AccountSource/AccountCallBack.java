package com.example.projectnewsbgn.Source.AccountSource;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.News;

import java.util.List;

public interface AccountCallBack {
    void onSuccessFromAuthentication(Account account);
    void onFailureFromAuthentication(String message);
    void onSuccessLogout();

    void onSuccessFromRemoteDatabase(Account account);
    void onSuccessFromRemoteDatabase(List<News> newsList);
    void onSuccessFromGettingUserPreferences();
    void onFailureFromRemoteDatabase(String message);

}
