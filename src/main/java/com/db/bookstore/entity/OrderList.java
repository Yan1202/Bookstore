package com.db.bookstore.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderList {
    String isbn;
    Date date;
    int num;
    float cost;

    public OrderList(String isbn,Date date,int num,float cost){
        this.isbn=isbn;
        this.date=date;
        this.num=num;
        this.cost=cost;
    }
    public OrderList(){

    }
}
