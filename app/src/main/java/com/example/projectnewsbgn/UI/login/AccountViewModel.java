package com.example.projectnewsbgn.UI.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.Repository.AccountReposiroty.IAccountRepositoryWithLiveData;

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
}
