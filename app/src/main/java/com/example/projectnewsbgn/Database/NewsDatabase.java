package com.example.projectnewsbgn.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projectnewsbgn.Models.News;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {News.class},version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();

    private static NewsDatabase instance;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService dataBaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /* Metodo per accedere al DAO
    public abstract NewsDao newsDao();*/

    /* Metodo per creare il dataBase,synchronized indica che solo un thread per volta piò accedere a questo metodo*/
    public static synchronized NewsDatabase getInstanceDatabase(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NewsDatabase.class,"account_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
