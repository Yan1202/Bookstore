package com.db.bookstore.service;

import com.db.bookstore.connection.OracleConnection;
import com.db.bookstore.connection.TTConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: yan
 * @Date: 2019/12/28
 * @Description:
 **/

@Service
public class OracleService {


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn= OracleConnection.getConnection();
        if(conn!=null){
            System.out.println("yes");
        }
    }
}
