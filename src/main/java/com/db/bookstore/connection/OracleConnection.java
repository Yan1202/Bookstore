package com.db.bookstore.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: yan
 * @Date: 2019/12/28
 * @Description:
 **/
public class OracleConnection {
    private static Connection connection = null;

    private static final String URL = "jdbc:oracle:thin:@47.103.217.53:1521:XE";
    private static final String name = "admin";
    private static final String pwd = "admin";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null){
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(URL,name,pwd);
        }
        return connection;
    }
}
