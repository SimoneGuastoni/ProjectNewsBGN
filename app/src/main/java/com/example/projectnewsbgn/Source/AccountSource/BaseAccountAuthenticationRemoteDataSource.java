package com.example.projectnewsbgn.Source.AccountSource;

import com.example.projectnewsbgn.Models.Account;

public abstract class BaseAccountAuthenticationRemoteDataSource {
    protected AccountCallBack accountCallBack;

    public void setAccountCallBack(AccountCallBack accountCallBack) {
        this.accountCallBack = accountCallBack;
    }
    public abstract Account getLoggedAccount();
    public abstract void logout();
    public abstract void signUp(String email, String password);
    public abstract void signIn(String email, String password);
}
