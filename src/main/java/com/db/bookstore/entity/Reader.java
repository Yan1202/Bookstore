package com.db.bookstore.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "READER")
@Entity
@Data
public class Reader implements Serializable {
    @Column(name="USER_ID")
    @Id
    String userId;
    @Column(name="NAME")
    String name;
    @Column(name="GENDER")
    String gender;
    @Column(name="BIRTH_DATE")
    Date birth_date;
    @Column(name="TEL")
    Integer tel;
    @Column(name="EMAIL")
    String email;


}
