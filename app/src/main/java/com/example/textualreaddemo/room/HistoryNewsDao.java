package com.example.textualreaddemo.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryNewsDao {
    @Insert
    void insertHistoryNews(HistoryNews... historyNews);

    @Query("Delete From HistoryNews Where newsID = :newsID")
    void deleteHistoryNewsByNewsID (String newsID);

    @Query("Delete From HistoryNews")
    void deleteAllHistoryNews();

    @Query("Select * From HistoryNews Where newsID = :newsID")
    HistoryNews getHistoryNewsByNewsID (String newsID);

    @Query("Select * From HistoryNews")
    List<HistoryNews> getAllHistoryNews();
}
