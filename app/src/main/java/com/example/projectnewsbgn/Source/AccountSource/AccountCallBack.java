package com.example.projectnewsbgn.Source.AccountSource;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.News;

import java.util.List;

public interface AccountCallBack {
    void onSuccessFromAuthentication(Account account);
    void onSuccessFromAuthentication(String email,String id);
    void onSuccessFromRemoteDatabase(Account account);
    void onSuccessFromRemoteDatabase(String id,String accountName,String email,String country,List<String> topicList);
    void onSuccessLogout();

    void onFailureFromAuthentication(String message);
    void onFailureFromRemoteDatabase(String message);

}
