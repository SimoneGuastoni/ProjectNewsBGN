package com.example.projectnewsbgn.Repository.AccountRepository;

import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsbgn.Models.Result;

import java.util.List;

public interface IAccountRepositoryWithLiveData {

    MutableLiveData<Result> authentication(String accountName, String email, String password, String country, List<String> topicList);
    MutableLiveData<Result> logout();
    MutableLiveData<Result> getLoggedAccount();
    MutableLiveData<Result> changeAccountDataFirebase(String accountId,String email, String newName, String newCountry, List<String> newTopicList);


    void signUp(String accountName,String email, String password,String country,List<String> topicList);
    void login(String email, String password);
    void sendPasswordResetEmail(String emailAddress); //invia mail di recupero password

}
