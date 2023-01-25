package com.example.projectnewsbgn.Source.AccountSource;

import android.app.Application;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

public class AccountInfoRemoteDataSource extends BaseAccountInfoRemoteDataSource{

    private static final String TAG = AccountInfoRemoteDataSource.class.getSimpleName();

    private final DatabaseReference databaseReference;

    public AccountInfoRemoteDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://newsbgn-78fbd-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference().getRef();
    }

    @Override
    public void saveAccountData(Account account) {

    }

    @Override
    public void getAccountFavoriteNews(String idToken) {

    }

    @Override
    public void getAccountPreferences(String idToken) {

    }

    @Override
    public void saveAccountPreferences(String favoriteCountry, Set<String> favoriteTopics, String idToken) {

    }
}
