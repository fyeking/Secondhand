package com.wf.secondhand.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SecondHandDatabaseHelper extends SQLiteOpenHelper {
    //创建user表用于登录注册
    public static final String CREATE_USER="create table t_user(" +
            "username text not null," +
            "password text not null," +
            "email text not null," +
            "phone text not null," +
            "icon text," +
            "primary key(username,phone))";


    //创建product表用于商品展示
    public static final String CREATE_PRODUCT="create table t_product(" +
            "id integer not null primary key autoincrement ," +
            "image text ," +
            "title text not null," +
            "content text not null," +
            "author text not null," +
            "price real not null," +
            "foreign key(author) references t_user(username))";


    //创建transaction表用于交易
    public static final String CREATE_TRANSACTION="CREATE TABLE [t_transaction](" +
            "  [id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "  [p_id] INTEGER NOT NULL REFERENCES [t_product]([id])," +
            "  [u_name] TEXT NOT NULL);";

    SecondhandData data =new SecondhandData();


    public SecondHandDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //user表
        sqLiteDatabase.execSQL(CREATE_USER);
        //插数据
        for (String user: data.getUser()) {
            sqLiteDatabase.execSQL(user);
        }

        //product表
        sqLiteDatabase.execSQL(CREATE_PRODUCT);
        //插数据
        for (String pro: data.getProduct()) {
            sqLiteDatabase.execSQL(pro);
        }


        //transaction表
        sqLiteDatabase.execSQL(CREATE_TRANSACTION);

        Log.i("SecondHandDatabase","数据库构建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
