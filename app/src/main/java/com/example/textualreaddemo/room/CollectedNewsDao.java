package com.example.textualreaddemo.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CollectedNewsDao {
    @Insert
    void insertCollectedNews(CollectedNews... collectedNews);

    @Query("Delete From CollectedNews Where newsID = :newsID")
    void deleteCollectedNewsByNewID(String newsID);

    @Query("Delete From CollectedNews")
    void deleteAllCollectedNews();

    @Query("Select * From CollectedNews Where newsID = :newsID")
    CollectedNews getCollectedNewsByNewsID(String newsID);

    @Query("Select * From CollectedNews")
    List<CollectedNews> getAllCollectedNews();
}
