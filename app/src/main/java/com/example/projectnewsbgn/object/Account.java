package com.example.projectnewsbgn.object;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_table")
public class Account implements Parcelable {
    String accountName,email,psw,country;
    /*ImageView icon;*/
    Boolean topic1,topic2,topic3,topic4,topic5,topic6;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Account(int id,String accountName,String email,String psw,String country,Boolean topic1,Boolean topic2,Boolean topic3,Boolean topic4,Boolean topic5,Boolean topic6){

        this.id = id;
        this.accountName = accountName;
        this.email = email;
        this.psw = psw;
        this.country = country;
        /*this.icon = icon;*/
        this.topic1 = topic1;
        this.topic2 = topic2;
        this.topic3 = topic3;
        this.topic4 = topic4;
        this.topic5 = topic5;
        this.topic6 = topic6;

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

    protected Account(Parcel in) {
        accountName = in.readString();
        email = in.readString();
        psw = in.readString();
        byte tmpTopic1 = in.readByte();
        topic1 = tmpTopic1 == 0 ? null : tmpTopic1 == 1;
        byte tmpTopic2 = in.readByte();
        topic2 = tmpTopic2 == 0 ? null : tmpTopic2 == 1;
        byte tmpTopic3 = in.readByte();
        topic3 = tmpTopic3 == 0 ? null : tmpTopic3 == 1;
        byte tmpTopic4 = in.readByte();
        topic3 = tmpTopic4 == 0 ? null : tmpTopic4 == 1;
        byte tmpTopic5 = in.readByte();
        topic3 = tmpTopic5 == 0 ? null : tmpTopic5 == 1;
        byte tmpTopic6 = in.readByte();
        topic3 = tmpTopic6 == 0 ? null : tmpTopic6 == 1;
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

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(accountName);
        parcel.writeString(email);
        parcel.writeString(psw);
        parcel.writeByte((byte) (topic1 == null ? 0 : topic1 ? 1 : 2));
        parcel.writeByte((byte) (topic2 == null ? 0 : topic2 ? 1 : 2));
        parcel.writeByte((byte) (topic3 == null ? 0 : topic3 ? 1 : 2));
        parcel.writeByte((byte) (topic4 == null ? 0 : topic4 ? 1 : 2));
        parcel.writeByte((byte) (topic5 == null ? 0 : topic5 ? 1 : 2));
        parcel.writeByte((byte) (topic6 == null ? 0 : topic6 ? 1 : 2));
    }
}
