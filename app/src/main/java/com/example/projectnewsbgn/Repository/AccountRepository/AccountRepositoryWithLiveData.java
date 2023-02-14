package com.example.projectnewsbgn.Repository.AccountRepository;

import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.Source.AccountSource.AccountCallBack;
import com.example.projectnewsbgn.Source.AccountSource.BaseAccountAuthenticationRemoteDataSource;
import com.example.projectnewsbgn.Source.AccountSource.BaseAccountInfoRemoteDataSource;

import java.util.List;

public class AccountRepositoryWithLiveData implements IAccountRepositoryWithLiveData, AccountCallBack {


    private final BaseAccountAuthenticationRemoteDataSource baseAccountAuthenticationRemoteDataSource;
    private final BaseAccountInfoRemoteDataSource baseAccountInfoRemoteDataSource;
    private final MutableLiveData<Result> accountMutableLiveData;
    private final MutableLiveData<Result> resultMutableLiveData;

    public AccountRepositoryWithLiveData(BaseAccountAuthenticationRemoteDataSource baseAccountAuthenticationRemoteDataSource,
                          BaseAccountInfoRemoteDataSource baseAccountInfoRemoteDataSource) {
        this.accountMutableLiveData = new MutableLiveData<>();
        this.resultMutableLiveData = new MutableLiveData<>();
        this.baseAccountAuthenticationRemoteDataSource = baseAccountAuthenticationRemoteDataSource;
        this.baseAccountInfoRemoteDataSource = baseAccountInfoRemoteDataSource;
        this.baseAccountAuthenticationRemoteDataSource.setAccountCallBack(this);
        this.baseAccountInfoRemoteDataSource.setAccountCallBack(this);
    }

    @Override
    public MutableLiveData<Result> authentication(String accountName, String email, String password,
                                                  String country, List<String> topicList) {
        if(accountName.equals("")){
            login(email,password);
        }
        else{
            signUp(accountName,email,password,country,topicList);
        }
        return accountMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> logout() {
        baseAccountAuthenticationRemoteDataSource.logout();
        return resultMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getLoggedAccount() {
        baseAccountAuthenticationRemoteDataSource.getLoggedAccount();
        return accountMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> changeAccountDataFirebase(String accountId,String email ,String newName, String newCountry, List<String> newTopicList) {
        baseAccountInfoRemoteDataSource.changeAccountDataRemote(accountId,email,newName,newCountry,newTopicList);
        return accountMutableLiveData;
    }

    @Override
    public void signUp(String accountName,String email, String password,
                       String country,List<String> topicList) {
        baseAccountAuthenticationRemoteDataSource.signUp(accountName,email,password,country,topicList);
    }

    @Override
    public void login(String email, String password) {
        baseAccountAuthenticationRemoteDataSource.login(email,password);
    }

    public void sendPasswordResetEmail(String emailAddress){
        baseAccountAuthenticationRemoteDataSource.sendPassword(emailAddress);

    }

    //Metodi onSuccess

    @Override
    public void onSuccessFromAuthentication(Account account) {
        if(account != null){
            baseAccountInfoRemoteDataSource.saveAccountData(account);
        }
    }

    @Override
    public void onSuccessFromAuthentication(String email, String id) {
        if (email != null && id !=null){
            baseAccountInfoRemoteDataSource.getAccountPreferences(email,id);
        }
    }

    @Override
    public void onSuccessLogout(Account currentUser) {
        Result.AccountSuccess result = new Result.AccountSuccess(currentUser);
        resultMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(Account account) {
        Result.AccountSuccess result = new Result.AccountSuccess(account);
        accountMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(String id, String accountName, String email, String country, List<String> topicList) {
        Account accountCopy = new Account(id,accountName,email,country,topicList);
        Result.AccountSuccess result = new Result.AccountSuccess(accountCopy);
        accountMutableLiveData.postValue(result);
    }

    //Metodi OnFailure
    @Override
    public void onFailureFromAuthentication(String message) {
        Result.Error result = new Result.Error(message);
        accountMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        accountMutableLiveData.postValue(result);
    }

}
