package com.db.bookstore.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name="BOOK_ORDER")
public class Order {
    @Id
    @Column(name="ORDER_ID")
    String id;
    @Column(name="USER_ID")
    String userId;
    @Column(name="ISBN")
    String isbn;
    @Column(name="ORDER_DATE")
    Date orderDate;
    @Column(name="ORDER_NUM")
    Integer num;
    @Column(name="SCORE")
    Float score;
    @Column(name="EVALUATE")
    String comment;
}
