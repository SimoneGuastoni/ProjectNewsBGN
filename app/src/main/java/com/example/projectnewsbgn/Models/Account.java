package com.example.projectnewsbgn.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "account_table")
public class Account {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String accountName,email,psw,country;
    private List<String> favAccountTopics;
    /*ImageView icon;*/

    public Account(int id,String accountName,String email,String psw,String country,List<String> favAccountTopics){

        this.id = id;
        this.accountName = accountName;
        this.email = email;
        this.psw = psw;
        this.country = country;
        this.favAccountTopics = favAccountTopics;
        /*this.icon = icon;*/

    }

    public String getCountry() {
        return country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    /*public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }*/

}
