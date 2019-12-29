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
    String name;
    String gender;
    Date birth_date;
    int tel;
    String email;


}
