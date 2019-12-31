package com.db.bookstore.service;

import com.db.bookstore.Util.Result;
import com.db.bookstore.Util.ResultUtil;
import com.db.bookstore.connection.OracleConnection;
import com.db.bookstore.entity.Book;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("checkBookService")
public class CheckBookService {
    public Result checkDetail(String isbn) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        PreparedStatement psql;
        ArrayList<Book> books=new ArrayList<>();


        psql=conn.prepareStatement("select * from oracle.BOOK where BOOK.ISBN = ?");
        psql.setString(1,isbn);
        long start=System.currentTimeMillis();
        psql.execute();
        long end=System.currentTimeMillis();

        ResultSet resultSet=psql.getResultSet();
        while(resultSet.next()){
            String book_isbn=resultSet.getString("isbn");
            String title=resultSet.getString("title");
            String author=resultSet.getString("author");
            float price=resultSet.getFloat("price");
            String publisher=resultSet.getString("publisher");
            Date publish_time=resultSet.getDate("publish_time");
            String introduction=resultSet.getString("introduction");
            int stock=resultSet.getInt("stock");

            Book book=new Book(book_isbn,title,author,publisher,price,publish_time,introduction,stock);
            books.add(book);
        }
        psql.close();

        return ResultUtil.success(books,end-start);
    }

    public Result buyNow(String id,String isbn,int count)throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        CallableStatement psql;

        psql=conn.prepareCall("call oracle.BUY_NOW(?,?,?)");
        psql.setString(1,id);
        psql.setString(2,isbn);
        psql.setInt(3,count);
        long start=System.currentTimeMillis();
        boolean result=psql.execute();
        long end=System.currentTimeMillis();


        return ResultUtil.success(end-start);
    }

}
