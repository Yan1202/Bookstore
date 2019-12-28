package com.db.bookstore.service;

import com.db.bookstore.connection.OracleConnection;
import com.db.bookstore.entity.Book;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("searchBook")
public class SearchBookService {

    Map<String,Object> searchByName(String name) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        Map<String,Object> map=new HashMap<>();
        ArrayList<Book> books=new ArrayList<>();
        PreparedStatement psql;

        psql=conn.prepareStatement("select isbn,title,author,price,publisher from BOOK where BOOK.TITLE like ?");
        psql.setString(1,"%"+name+"%");
        long start=System.currentTimeMillis();
        psql.execute();
        long end=System.currentTimeMillis();

        ResultSet resultSet=psql.getResultSet();
        while(resultSet.next()){
            String isbn=resultSet.getString("isbn");
            String title=resultSet.getString("title");
            String author=resultSet.getString("author");
            float price=resultSet.getFloat("price");
            String publisher=resultSet.getString("publisher");
            Book book=new Book(isbn,title,author,publisher,price);
            books.add(book);
        }
        resultSet.last();
        int rows=resultSet.getRow();
        psql.close();

        map.put("data",books);
        map.put("total",rows);
        map.put("cost",(end-start));

        return map;
    }



}
