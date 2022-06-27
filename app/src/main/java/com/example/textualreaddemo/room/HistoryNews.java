package com.example.textualreaddemo.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryNews {
    @PrimaryKey
    @ColumnInfo(name = "newsID",typeAffinity = ColumnInfo.TEXT)
    @NonNull
    private String newsID;

    @ColumnInfo(name = "newsName",typeAffinity = ColumnInfo.TEXT)
    @NonNull
    private String newsName;

    @NonNull
    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(@NonNull String newsName) {
        this.newsName = newsName;
    }

    public HistoryNews(@NonNull String newsID) {
        this.newsID = newsID;
    }

    @NonNull
    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(@NonNull String newsID) {
        this.newsID = newsID;
    }
}
