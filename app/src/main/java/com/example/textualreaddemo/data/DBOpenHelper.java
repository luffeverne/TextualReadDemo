package com.example.textualreaddemo.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public static final String CREATE_USER = "CREATE TABLE IF NOT EXISTS user(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "password TEXT)";

    public static final String DROP_USER = "DROP TABLE IF EXISTS user";

    public DBOpenHelper(Context context){
        super(context,"db_reader",null,1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER);
        onCreate(db);
    }

    /**增删改
     * */
    public void add(String name,String password){
        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
    }
    public void delete(String name,String password){
        db.execSQL("DELETE FROM user WHERE name = AND password ="+name+password);
    }
    public void updata(String password){
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }

    /**查
     * 查询表 user 全部内容，需要有个容器存放，所以定义了 ArrayList 类的 List
     * 查询数据，使用游标 Cursor, 第一个参数是：“表明”， 中间5个：null
     * 游标定义好了，写个 while 循环，让游标从表头游到表尾
     * 在游的过程中，将游出来的数据放入到 List 容器中
     * */
    public ArrayList<User> getAllData() {
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = db.query("user", null, null, null, null, null, "name DESC");
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(name,password));
        }
        return list;
    }
}