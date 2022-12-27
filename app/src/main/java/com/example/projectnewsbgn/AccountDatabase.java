package com.example.projectnewsbgn;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projectnewsbgn.object.Account;

@Database(entities = {Account.class},version = 1)
public abstract class AccountDatabase extends RoomDatabase {

    private static AccountDatabase instance;

    /* Metodo per accedere al DAO*/
    public abstract AccountDao accountDao();

    /* Metodo per creare il dataBase,synchronized indica che solo un thread per volta piò accedere a questo metodo*/
    public static synchronized AccountDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AccountDatabase.class,"account_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
