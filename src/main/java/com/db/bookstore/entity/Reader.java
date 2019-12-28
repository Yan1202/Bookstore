package com.db.bookstore.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "reader")
@Entity
@Data
public class Reader implements Serializable {
    @Id
    String user_id;
    String name;
    String gender;
    Date birth_date;
    int tel;
    String email;


}
