package com.example.textualreaddemo.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey()
    @ColumnInfo(name = "userID",typeAffinity = ColumnInfo.TEXT)
    @NonNull
    private String userID;

    @ColumnInfo(name = "name",typeAffinity = ColumnInfo.TEXT)
    private String name;

    @ColumnInfo(name = "password",typeAffinity = ColumnInfo.TEXT)
    private String password;

    @ColumnInfo(name = "headshot",typeAffinity = ColumnInfo.TEXT)
    private String headshot;

    @ColumnInfo(name = "personalData",typeAffinity = ColumnInfo.TEXT)
    private String personalData;

    @Ignore
    public User() {
    }

    public User(String userID, String name, String password, String headshot, String personalData) {
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.headshot = headshot;
        this.personalData = personalData;
    }

    @NonNull
    public String getUserID() {
        return userID;
    }

    public void setUserID(@NonNull String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public String getPersonalData() {
        return personalData;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", headshot='" + headshot + '\'' +
                ", personalData='" + personalData + '\'' +
                '}';
    }
}
