package com.example.projectnewsbgn.Source.AccountSource;

import androidx.annotation.NonNull;

import com.example.projectnewsbgn.Models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AccountAuthenticationRemoteDataSource extends BaseAccountAuthenticationRemoteDataSource{

    private static final String TAG = AccountAuthenticationRemoteDataSource.class.getSimpleName();

    private final FirebaseAuth firebaseAuth;

    public AccountAuthenticationRemoteDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
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

    @Override
    public void logout() {

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
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    accountCallBack.onSuccessFromAuthentication
                            (firebaseUser.getEmail(),firebaseUser.getUid());
                    /*accountCallBack.onSuccessFromAuthentication(new Account
                            (firebaseUser.getUid(),firebaseUser.getDisplayName(),
                                    email,null,null));*/
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
}