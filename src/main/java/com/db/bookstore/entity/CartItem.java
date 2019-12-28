package com.db.bookstore.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartItem implements Serializable {
    int num;
    String isbn;
    float price;
    String title;
    int stock;

    public CartItem(int num,String isbn,String title,float price,int stock){
        this.num=num;
        this.isbn=isbn;
        this.title=title;
        this.price=price;
        this.stock=stock;
    }

    public CartItem(){

    }
}
