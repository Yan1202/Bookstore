package com.db.bookstore.service;

import com.db.bookstore.Util.Result;
import com.db.bookstore.Util.ResultUtil;
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

@Service("searchBookService")
public class SearchBookService {

    public Result search(String isbn,String title,String author,String publisher,float down,float up) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        ArrayList<Book> books=new ArrayList<>();
        StringBuffer buffer=new StringBuffer();
        buffer.append("select ISBN,TITLE,AUTHOR,PRICE,PUBLISHER from oracle.BOOK where 1=1");

        if(title!=null&&title!=""){
            buffer.append(" and TITLE LIKE \'%"+title+"%\' ");
        }
        if(isbn!=null&&isbn!=""){
            buffer.append(" and ISBN= \'"+isbn+"\' ");
        }
        if(author!=null&&author!=""){
            buffer.append(" and AUTHOR LIKE \'%"+author+"%\' ");
        }
        if(publisher!=null&&publisher!=""){
            buffer.append(" and PUBLISHER LIKE \'%"+publisher+"%\' ");
        }
        if(down>0){
            buffer.append(" and PRICE>="+down);
        }
        if(up>0){
            buffer.append(" and PRICE<="+up);
        }
        System.out.println(buffer.toString());
        PreparedStatement psql=conn.prepareStatement(buffer.toString());
        long start=System.currentTimeMillis();
        psql.execute();
        long end=System.currentTimeMillis();

        ResultSet resultSet=psql.getResultSet();
        while(resultSet.next()){
            String book_isbn=resultSet.getString("ISBN");
            String book_title=resultSet.getString("TITLE");
            String book_author=resultSet.getString("AUTHOR");
            float book_price=resultSet.getFloat("PRICE");
            String book_publisher=resultSet.getString("PUBLISHER");
            Book book=new Book(book_isbn,book_title,book_author,book_publisher,book_price);
            books.add(book);
        }
        psql.close();

        return ResultUtil.success(books,end-start);
    }



}
