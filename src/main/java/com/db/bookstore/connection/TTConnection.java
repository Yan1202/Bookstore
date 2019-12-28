package com.db.bookstore.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: yan
 * @Date: 2019/12/28
 * @Description:
 **/
public class TTConnection {
    private static Connection connection = null;

    private static final String URL = "jdbc:timesten:client:book_CS";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null){
            Class.forName("com.timesten.jdbc.TimesTenDriver");
            connection = DriverManager.getConnection(URL);
        }
        return connection;
    }
}
