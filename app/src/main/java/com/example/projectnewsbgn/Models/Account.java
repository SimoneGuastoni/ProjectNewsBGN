package com.example.projectnewsbgn.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "account_table")
public class Account {

    @PrimaryKey(autoGenerate = true)
    private String accountName,email,country,id;
    private List<String> favAccountTopics;
    /*ImageView icon;*/

    public Account(String id,String accountName,String email,String country,List<String> favAccountTopics){

        this.id = id;
        this.accountName = accountName;
        this.email = email;
        this.country = country;
        this.favAccountTopics = favAccountTopics;
        /*this.icon = icon;*/

    }

    public List<String> getFavAccountTopics() {
        return favAccountTopics;
    }

    public void setFavAccountTopics(List<String> favAccountTopics) {
        this.favAccountTopics = favAccountTopics;
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    /*public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }*/

}
