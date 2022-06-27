package com.example.textualreaddemo.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUsers(User... users);

    @Query("Delete From User Where userID = :userID")
    void deleteUserByUserID(String userID);

    @Query("Delete From user")
    void deleteAllUsers();

    @Update
    void updateUsers(User... users);

    @Query("select * From User Where userID = (:userID)")
    User getUserByUserID(String userID);

    @Query("Select * From user Order By userID Desc")
    List<User> getAllUsers();
}
