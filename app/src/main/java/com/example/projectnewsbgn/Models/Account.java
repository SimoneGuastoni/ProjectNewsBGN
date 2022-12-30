package com.example.projectnewsbgn.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_table")
public class Account {

    @PrimaryKey(autoGenerate = true)
    private int id;

    /*ImageView icon;*/
    String accountName,email,psw,country;
    Boolean topic1,topic2,topic3,topic4,topic5,topic6;


    public Account(int id,String accountName,String email,String psw,String country,Boolean topic1,Boolean topic2,Boolean topic3,Boolean topic4,Boolean topic5,Boolean topic6){

        this.id = id;
        this.accountName = accountName;
        this.email = email;
        this.psw = psw;
        this.country = country;
        this.topic1 = topic1;
        this.topic2 = topic2;
        this.topic3 = topic3;
        this.topic4 = topic4;
        this.topic5 = topic5;
        this.topic6 = topic6;
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

    public Boolean getTopic4() {
        return topic4;
    }

    public void setTopic4(Boolean topic4) {
        this.topic4 = topic4;
    }

    public Boolean getTopic5() {
        return topic5;
    }

    public void setTopic5(Boolean topic5) {
        this.topic5 = topic5;
    }

    public Boolean getTopic6() {
        return topic6;
    }

    public void setTopic6(Boolean topic6) {
        this.topic6 = topic6;
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

    public Boolean getTopic1() {
        return topic1;
    }

    public void setTopic1(Boolean topic1) {
        this.topic1 = topic1;
    }

    public Boolean getTopic2() {
        return topic2;
    }

    public void setTopic2(Boolean topic2) {
        this.topic2 = topic2;
    }

    public Boolean getTopic3() {
        return topic3;
    }

    public void setTopic3(Boolean topic3) {
        this.topic3 = topic3;
    }

    /*public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }*/

}
