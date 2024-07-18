package com.wf.secondhand.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wf.secondhand.data.SecondHandDatabaseHelper;
import com.wf.secondhand.data.pojo.User;

public class UserDao {
    private SecondHandDatabaseHelper helper;
    private Context context;

    public UserDao(Context context) {
        this.context = context;
    }

    //添加user
    public void insert(User user){
        //创建数据库
        helper = new SecondHandDatabaseHelper(context,"secondHand.db",null,1);
        //get数据库
        SQLiteDatabase db=helper.getReadableDatabase();

        //插user数据
        db.execSQL("insert into t_user(username,password,email,phone,icon) values(?,?,?,?,?)",new Object[]{user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone()});

        db.close();
    }

    //查询user
    public User getByName(String userName){
        User user=null;
        helper = new SecondHandDatabaseHelper(context,"secondHand.db",null,1);

        SQLiteDatabase db=helper.getReadableDatabase();

        //查询user数据
        Cursor cursor =db.query("t_user",new String[]{"userName","password","email","phone","icon"},"userName = ?",new String[]{userName},null,null,null);

        if (cursor.moveToFirst()){
            user= new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        }

        cursor.close();
        db.close();

        return user;
    }

    //修改user
    public void update(User user){
        helper = new SecondHandDatabaseHelper(context,"secondHand.db",null,1);

        SQLiteDatabase db =helper.getReadableDatabase();

        //修改user数据
        ContentValues values =new ContentValues();

        values.put("userName",user.getUsername());
        values.put("password",user.getPassword());
        values.put("email",user.getEmail());
        values.put("phone",user.getPhone());
        values.put("icon",user.getIcon());
        db.update("t_user",values,"username =?",new String[]{user.getUsername()});

        db.close();
    }


    //判断username是否存在
    public boolean existsName(String name){
        Boolean f=false;
         User user=getByName(name);
        if (user!=null){
            f=true;
        }
        return f;
    }
}
