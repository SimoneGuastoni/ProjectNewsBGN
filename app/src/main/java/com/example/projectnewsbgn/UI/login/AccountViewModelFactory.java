package com.example.projectnewsbgn.UI.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectnewsbgn.Repository.AccountReposiroty.IAccountRepositoryWithLiveData;

public class AccountViewModelFactory implements ViewModelProvider.Factory {

    private final IAccountRepositoryWithLiveData accountRepository;

    public AccountViewModelFactory(IAccountRepositoryWithLiveData accountRepository) {
        this.accountRepository = accountRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AccountViewModel(accountRepository);
    }
}
