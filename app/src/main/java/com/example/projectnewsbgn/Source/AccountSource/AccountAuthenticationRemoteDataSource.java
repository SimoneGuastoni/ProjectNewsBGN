package com.example.projectnewsbgn.Source.AccountSource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectnewsbgn.Models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AccountAuthenticationRemoteDataSource extends BaseAccountAuthenticationRemoteDataSource{

    private static final String TAG = AccountAuthenticationRemoteDataSource.class.getSimpleName();

    private final FirebaseAuth firebaseAuth;

    public AccountAuthenticationRemoteDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    accountCallBack.onSuccessFromAuthentication
                            (firebaseUser.getEmail(),firebaseUser.getUid());
                }
                else{
                    accountCallBack.onFailureFromAuthentication("Account equal null");
                }
            }
            else {
                accountCallBack.onFailureFromAuthentication("Call error");
            }
        });
    }

    @Override
    public void logout() {
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    firebaseAuth.removeAuthStateListener(this);
                    Log.d(TAG, "User logged out");
                    accountCallBack.onSuccessLogout(new Account("","","","",null));
                }
                else
                    accountCallBack.onFailureFromAuthentication("Logout error");
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);
        firebaseAuth.signOut();
    }

    @Override
    public void signUp(String accountName,String email, String password,String country,List<String> topicList) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    accountCallBack.onSuccessFromAuthentication(new Account(firebaseUser.getUid(),
                            accountName, email, country, topicList));
                } else {
                    accountCallBack.onFailureFromRemoteDatabase("firebaseUser equal null");
                }
            } else {
                accountCallBack.onFailureFromRemoteDatabase("Unsuccessful task");
            }
        });
    }

    @Override
    public void getLoggedAccount() {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if(firebaseUser != null){
                accountCallBack.onSuccessFromAuthentication(
                        firebaseUser.getEmail(),firebaseUser.getUid());
            }
            else{
                accountCallBack.onFailureFromAuthentication("Errore utente non trovato");
            }
    }

}
