package com.example.projectnewsbgn;

/* Consiste in un ulteriore step tra le i dati,le risorse e il viewModel (elementi osservati dall'utente UI) */

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projectnewsbgn.object.Account;

import java.util.List;

public class AccountRepository {
    private AccountDao accountDao;
    private LiveData<List<Account>> accountList;

    public AccountRepository(Application application){
        AccountDatabase newsDatabase = AccountDatabase.getInstance(application);
        accountDao = newsDatabase.accountDao();
        accountList = accountDao.getAllAccount();
    }
}
