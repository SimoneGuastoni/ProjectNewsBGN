package com.example.projectnewsbgn.UI.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.Repository.AccountReposiroty.IAccountRepositoryWithLiveData;

import java.util.List;

public class AccountViewModel extends ViewModel {

    private static final String TAG = AccountViewModel.class.getSimpleName();

    private final IAccountRepositoryWithLiveData accountRepository;
    private MutableLiveData<Result> accountMutableLiveData;
    private MutableLiveData<Result> accountFavoriteNewsMutableLiveData;
    private MutableLiveData<Result> accountPreferencesMutableLiveData;
    private boolean authenticationError;

    public AccountViewModel(IAccountRepositoryWithLiveData accountRepository) {
        this.accountRepository = accountRepository;
        authenticationError = false;
    }

    //TODO cambiare nome al flusso di metodo??
    public MutableLiveData<Result> authentication(String accountName, String emailString,
                                                  String pswString, String country, List<String> topicList) {
        getAccountFromRepository(accountName,emailString,pswString,country,topicList);
        return accountMutableLiveData;
    }

    private void getAccountFromRepository(String accountName,String emailString, String pswString,
                                          String country,List<String> topicList) {
        accountMutableLiveData = accountRepository.authentication(accountName,emailString,pswString,country,topicList);
    }

    public void setAuthenticationError(boolean authenticationErrorFromRegister) {
        authenticationError = authenticationErrorFromRegister;
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
}
