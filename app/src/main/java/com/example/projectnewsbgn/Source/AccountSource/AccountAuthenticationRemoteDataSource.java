package com.example.projectnewsbgn.Source.AccountSource;

import com.example.projectnewsbgn.Models.Account;
import com.google.firebase.auth.FirebaseAuth;

public class AccountAuthenticationRemoteDataSource extends BaseAccountAuthenticationRemoteDataSource{

    private static final String TAG = AccountAuthenticationRemoteDataSource.class.getSimpleName();

    private final FirebaseAuth firebaseAuth;

    public AccountAuthenticationRemoteDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public Account getLoggedAccount() {
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public void signUp(String email, String password) {

    }

    @Override
    public void signIn(String email, String password) {

    }
}
