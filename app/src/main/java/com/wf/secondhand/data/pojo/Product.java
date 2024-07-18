package com.wf.secondhand.data.pojo;

import java.io.Serializable;
import java.sql.Blob;

public class Product implements Serializable {
    private int id;
    private String image;
    private String title;
    private String content;
    private String author;
    private String price;


    public Product() {

    }

    public Product(String title, String content, String author, String price) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.price = price;
    }

    public Product(String image, String title, String content, String author, String price) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.author = author;
        this.price = price;
    }

    public Product(int id, String image, String title, String content, String author, String price) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.content = content;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
