package com.example.projectnewsbgn.Source.AccountSource;

import androidx.annotation.NonNull;

import com.example.projectnewsbgn.Models.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountInfoRemoteDataSource extends BaseAccountInfoRemoteDataSource{


    private final DatabaseReference databaseReference;

    public AccountInfoRemoteDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://newsbgn-78fbd-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference().getRef();
    }



    @Override
    public void saveAccountData(Account account) {
        databaseReference.child("account").child(account.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //Record già presente
                    accountCallBack.onSuccessFromRemoteDatabase(account);
                }
                else{
                    //Record non presente
                    databaseReference.child("account").child(account.getId()).setValue(account)
                            .addOnSuccessListener(unused -> accountCallBack.
                                    onSuccessFromRemoteDatabase(account)).
                            addOnFailureListener(e -> accountCallBack.
                                    onFailureFromRemoteDatabase("Saving account error"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                accountCallBack.onFailureFromRemoteDatabase(error.getMessage());
            }
        });
    }

    @Override
    public void changeAccountDataRemote(String accountId,String email,String newName, String newCountry, List<String> newTopicList) {
        databaseReference.child("account").child(accountId)
                .child("accountName").setValue(newName).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        databaseReference.child("account").child(accountId)
                                .child("country").setValue(newCountry).addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()){
                                        databaseReference.child("account").child(accountId)
                                                .child("favAccountTopics").setValue(newTopicList).addOnCompleteListener(task3 -> {
                                                    if (task3.isSuccessful()){
                                                        accountCallBack.onSuccessFromRemoteDatabase(new Account(accountId,newName,email,newCountry,newTopicList));
                                                    }
                                                    else {
                                                        accountCallBack.onFailureFromRemoteDatabase("Error can't change info");
                                                    }
                                                });
                                    }
                                    else {
                                        accountCallBack.onFailureFromRemoteDatabase("Error can't change info");
                                    }
                                });
                    }
                    else {
                        accountCallBack.onFailureFromRemoteDatabase("Error can't change info");
                    }
        });
    }

    @Override
    public void getAccountPreferences(String email,String id) {
        databaseReference.child("account").child(id).child("country")
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                       String country = task.getResult().getValue(String.class);
                       databaseReference.child("account").child(id).child("favAccountTopics")
                               .get().addOnCompleteListener(task1 -> {
                                   if (task1.isSuccessful()){
                                       List<String> topicList = new ArrayList<>();
                                       for(DataSnapshot ds : task1.getResult().getChildren()) {
                                           String topic = ds.getValue(String.class);
                                           topicList.add(topic);
                                       }
                                       if (topicList.size() != 0){
                                           databaseReference.child("account").child(id).child("accountName")
                                                           .get().addOnCompleteListener(task2 -> {
                                                               if (task2.isSuccessful()){
                                                                   String accountName = task2.getResult().getValue(String.class);
                                                                   accountCallBack.onSuccessFromRemoteDatabase(id,accountName,email,country,topicList);
                                                               }
                                                               else {
                                                                   accountCallBack.onFailureFromRemoteDatabase("Errore recupero informazioni utente");
                                                               }
                                                           });
                                       }
                                       else {
                                           accountCallBack.onFailureFromRemoteDatabase("Errore recupero informazioni utente");
                                       }
                                   }
                                   else {
                                       accountCallBack.onFailureFromRemoteDatabase("Errore recupero informazioni utente");
                                   }
                               });
                    }
                    else{
                        accountCallBack.onFailureFromRemoteDatabase("Errore recupero informazioni utente");
                    }

        });
    }

}
