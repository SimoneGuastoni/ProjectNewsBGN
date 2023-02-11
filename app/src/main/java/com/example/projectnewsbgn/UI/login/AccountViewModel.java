package com.example.projectnewsbgn.UI.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.Repository.AccountRepository.IAccountRepositoryWithLiveData;

import java.util.List;

public class AccountViewModel extends ViewModel {


    private final IAccountRepositoryWithLiveData accountRepository;
    private MutableLiveData<Result> accountMutableLiveData;

    public AccountViewModel(IAccountRepositoryWithLiveData accountRepository) {
        this.accountRepository = accountRepository;
    }

    public MutableLiveData<Result> authentication(String accountName, String emailString,
                                                  String pswString, String country, List<String> topicList) {
        getAccountFromRepository(accountName,emailString,pswString,country,topicList);
        return accountMutableLiveData;
    }

    private void getAccountFromRepository(String accountName,String emailString, String pswString,
                                          String country,List<String> topicList) {
        accountMutableLiveData = accountRepository.authentication(accountName,emailString,pswString,country,topicList);
    }

    public void sendResetEmailPassword(String emailAddress)
    {
        accountRepository.sendPasswordResetEmail(emailAddress);
    }

    public MutableLiveData<Result> getAccountData(){
        //livedata != null se passo dal login o registrazione
        if(accountMutableLiveData == null){
            //Apro l'applicazione da zero e mi ritrovo nella home
            getLoggedAccount();
        }
        return accountMutableLiveData;
    }

    private void getLoggedAccount(){
        accountMutableLiveData = accountRepository.getLoggedAccount();
    }

    public MutableLiveData<Result> changeAccountData(String accountId, String email,String newName, String newCountry, List<String> newTopicList) {
        accountMutableLiveData = accountRepository.changeAccountDataFirebase(accountId,email,newName,newCountry,newTopicList);
        return accountMutableLiveData;
    }

    public MutableLiveData<Result> logOut() {
        return accountRepository.logout();
    }
}
