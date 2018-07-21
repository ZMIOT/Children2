package com.example.administrator.broadcastbestpractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/2/19.
 */

public class MyDatabasehelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table User("
            + "id integer primary key autoincrement,"
            + "username text,"
            + "nickname text,"
            + "birth text,"
            + "autograph text,"
            + "sex text,"
            + "touxiang BLOB,"
            + "state integer,"
            + "password text)";
    public static final String CREATE_dynamic = "create table Dyna("
            + "id integer primary key autoincrement,"
            + "teachername text,"
            + "teacherid integer,"
            + "title text,"
            + "content text,"
            + "time,"
            + "uri text)";
    public static final String CREATE_Grade = "create table Grade("
            + "id integer primary key autoincrement,"
            + "teachername text,"
            + "teacherid integer,"
            + "title text,"
            + "content text,"
            + "time,"
            + "uri text)";
    public static final String CREATE_Commnet = "create table Comment_TB("
            + "id integer primary key autoincrement,"
            + "teachername text,"
            + "teacherid integer,"
            + "babyid integer,"
            + "babyname text,"
            + "comment text)";
    private Context mContext;


    public MyDatabasehelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_Commnet);
        db.execSQL(CREATE_dynamic);
        db.execSQL(CREATE_Grade);
        Log.i("oncreate:", "here");
        Toast.makeText(mContext, "create successed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Comment");
        db.execSQL("drop table if exists Dyna");
        db.execSQL("drop table if exists Grade");
        onCreate(db);

    }
}
