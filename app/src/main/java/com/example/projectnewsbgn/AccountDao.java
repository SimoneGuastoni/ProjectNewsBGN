package com.example.projectnewsbgn;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectnewsbgn.object.Account;

import java.util.List;

/* Data Access Object , contiene tutti i metodi utili ad accedere a tali oggetti*/
@Dao
public interface AccountDao {

    @Insert
    void insertAccount(Account account);

    @Update
    void updateAccount(Account account);

    @Delete
    void deleteAccount(Account account);

    @Query("DELETE FROM account_table    ")
    void deleteAllAccount();

    @Query("SELECT * FROM account_table ORDER BY id DESC")
    LiveData<List<Account>> getAllAccount();
}
