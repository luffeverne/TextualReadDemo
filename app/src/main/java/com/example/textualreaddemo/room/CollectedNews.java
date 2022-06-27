package com.example.textualreaddemo.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CollectedNews {
    @PrimaryKey
    @ColumnInfo(name = "newsID",typeAffinity = ColumnInfo.TEXT)
    @NonNull
    private String newsID;

    @ColumnInfo(name = "newsName",typeAffinity = ColumnInfo.TEXT)
    @NonNull
    private String newsName;

    public CollectedNews(@NonNull String newsID) {
        this.newsID = newsID;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    @NonNull
    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(@NonNull String newsID) {
        this.newsID = newsID;
    }
}
