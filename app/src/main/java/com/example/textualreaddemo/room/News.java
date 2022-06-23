package com.example.textualreaddemo.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class News {
    @PrimaryKey()
    @ColumnInfo(name = "newsID",typeAffinity = ColumnInfo.TEXT)
    @NonNull
    private String newsID;

    @ColumnInfo(name = "userID",typeAffinity = ColumnInfo.TEXT)
    private String userID;

    @ColumnInfo(name = "isLike",typeAffinity = ColumnInfo.TEXT)
    private String isLike;

    @ColumnInfo(name = "isCollected",typeAffinity = ColumnInfo.TEXT)
    private String isCollected;

    @ColumnInfo(name = "isCommented",typeAffinity = ColumnInfo.TEXT)
    private String isCommented;

    @ColumnInfo(name = "isHistory",typeAffinity = ColumnInfo.TEXT)
    private String isHistory;

    @Ignore
    public News() {
    }

    public News(@NonNull String newsID, String userID, String isLike, String isCollected, String isCommented, String isHistory) {
        this.newsID = newsID;
        this.userID = userID;
        this.isLike = isLike;
        this.isCollected = isCollected;
        this.isCommented = isCommented;
        this.isHistory = isHistory;
    }

    @NonNull
    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(@NonNull String newsID) {
        this.newsID = newsID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(String isCollected) {
        this.isCollected = isCollected;
    }

    public String getIsCommented() {
        return isCommented;
    }

    public void setIsCommented(String isCommented) {
        this.isCommented = isCommented;
    }

    public String getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsID=" + newsID +
                ", userID=" + userID +
                ", isLike=" + isLike +
                ", isCollected=" + isCollected +
                ", isCommented=" + isCommented +
                ", isHistory=" + isHistory +
                '}';
    }
}
