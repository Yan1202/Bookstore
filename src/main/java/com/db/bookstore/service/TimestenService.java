package com.db.bookstore.service;

import com.db.bookstore.connection.TTConnection;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yan
 * @Date: 2019/12/28
 * @Description:
 **/

@Service
public class TimestenService {

//    public Map<String,Object> getVersionByMysql(String name) throws SQLException, ClassNotFoundException {
//        Connection conn= TTConnection.getConnection();
//
//        PreparedStatement psql;
//        psql = conn.prepareStatement("select title from book where isbn = '7806647880'");
//        //psql.setString(1, name);
//
//        long start=System.currentTimeMillis();
//        psql.execute();
//        long end=System.currentTimeMillis();
//
//        ResultSet resultSet=psql.getResultSet();
//        while(resultSet.next()){  //循环遍历查询结果集
//            String title=resultSet.getString("title");
//            System.out.println(title);
//
//        }
//        psql.close();
//        return null;
//    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn = TTConnection.getConnection();
        if (conn != null) {
            System.out.println("yes");
        }

    }
}
