package com.example.textualreaddemo.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert
    void insertNews(News... news);

    @Delete
    void deleteNews(News... news);

    @Update
    void updateNews(News... news);

    @Query("Select * From News Where newsID = :newsID")
    News getNewsByNewsID(String newsID);

    @Query("Select * From News Where userID = :userID")
    News getNewsByUserID(String userID);

    @Query("Delete From News")
    void deleteAllNews();

    @Query("Select * From News Order By newsID Desc")
    List<News> getAllNews();

}
