package com.wf.secondhand.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wf.secondhand.data.SecondHandDatabaseHelper;
import com.wf.secondhand.data.pojo.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private SecondHandDatabaseHelper helper;
    private Context context;

    public  ProductDao(Context context){
        this.context=context;
    }


    //模糊查询全部
    public List<Product> getProAll(String title){
        List list =new ArrayList();
        //读取数据库
        helper = new SecondHandDatabaseHelper(context,"secondHand.db",null,1);

        //get数据库
        SQLiteDatabase db =helper.getReadableDatabase();
        //模糊查询
        Cursor cursor =db.query("t_product",new String[]{"id","image","title","content","author","price"},"title like ?",new String[]{"%"+title+"%"},null,null,null);
        while (cursor.moveToNext()){
            Product product = new Product(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            list.add(product);
        }
        cursor.close();
        return list;
    }

    //根据作者查全部
    public List<Product> getProByAuthor(String author){
        List list = new ArrayList();

        //读取数据库
        helper =new SecondHandDatabaseHelper(context,"secondHand.db",null,1);

        //get数据库
        SQLiteDatabase db =helper.getReadableDatabase();

        //精准查询
        Cursor cursor =db.query("t_product",new String[]{"id","image","title","content","author","price"},"author = ?",new String[]{author},null,null,null);
        while (cursor.moveToNext()){
            Product product = new Product(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            list.add(product);
        }
        cursor.close();
        return  list;
    }

    //添加操作
    public void addPro(Product pro){
        //读取数据库
        helper = new SecondHandDatabaseHelper(context,"secondHand.db",null,1);

        //get数据库
        SQLiteDatabase db =helper.getReadableDatabase();

        //添加商品
        ContentValues values =new ContentValues();
        values.put("image",pro.getImage());
        values.put("title",pro.getTitle());
        values.put("content",pro.getContent());
        values.put("author",pro.getAuthor());
        values.put("price",pro.getPrice());
        db.insert("t_product",null,values);

        db.close();
    }

    //交易功能
    public void transaction(int id,String name){
        //读取数据库
        helper = new SecondHandDatabaseHelper(context,"secondHand.db",null,1);

        //get数据库
        SQLiteDatabase db =helper.getReadableDatabase();

        //将商品信息关联个人
        ContentValues values = new ContentValues();
        values.put("p_id",id);
        values.put("u_name",name);
        db.insert("t_transaction",null,values);
        db.close();
    }

    //查询个人购买信息
    public List<Product> getTran(String name){
        List list = new ArrayList();

        //读取数据库
        helper = new SecondHandDatabaseHelper(context,"secondHand.db",null,1);

        //get数据库
        SQLiteDatabase db =helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select p.id,p.image,p.title,p.content,p.author,p.price from t_transaction t,t_product p where p.id=t.p_id and t.u_name=?",new String[]{name});
        while (cursor.moveToNext()){
            Product product = new Product(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            list.add(product);
        }
        return list;
    }
}
