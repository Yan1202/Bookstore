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
    String id;
    @Column(name="NAME")
    String name;
    @Column(name="GENDER")
    String gender;
    @Column(name="BIRTH_DATE")
    Date birth_date;
    @Column(name="TEL")
    long tel;
    @Column(name="EMAIL")
    String email;
    @Column(name="PASSWORD")
    String password;


    public Reader(String id,String name,String gender,Date date,int tel,String email,String pwd){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.birth_date=date;
        this.tel=tel;
        this.email=email;
        this.password=pwd;
    }
    public Reader(){

    }


}
