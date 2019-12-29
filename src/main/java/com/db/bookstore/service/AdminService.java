package com.db.bookstore.service;

import com.db.bookstore.Util.Result;
import com.db.bookstore.Util.ResultEnum;
import com.db.bookstore.Util.ResultUtil;
import com.db.bookstore.connection.OracleConnection;
import com.db.bookstore.dao.BookDao;
import com.db.bookstore.dao.ReaderDao;
import com.db.bookstore.entity.Book;
import com.db.bookstore.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Service("adminService")
public class AdminService {

    @Autowired
    private ReaderDao readerDao;
    @Autowired
    private BookDao bookDao;

    public Result deleteReader(String id){
        Reader reader=readerDao.findById(id).get();
        if(reader==null){
            return ResultUtil.error(ResultEnum.NOT_FOUND);
        }

        return ResultUtil.success();
    }

    public Result deleteBook(String isbn){
        Book book=bookDao.findByIsbn(isbn);

        if(book==null){
            return ResultUtil.error(ResultEnum.FAIL);
        }

        bookDao.delete(book);
        return ResultUtil.success();
    }

    public Result addBook(String isbn, String title, String author, String publisher, Date publishDate,String introduction,float price,int stock){
        Book book=new Book(isbn,title,author,publisher,price,publishDate,introduction,stock);
        bookDao.save(book);
        return ResultUtil.success();
    }

    public Result login(String id,String pwd) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        PreparedStatement psql;

        psql=conn.prepareStatement("select ADMINISTRATOR.NAME as NAME from ADMINISTRATOR where ADMIN_ID=? and PASSWORD=?");
        psql.setString(1,id);
        psql.setString(2,pwd);
        long start=System.currentTimeMillis();
        boolean result=psql.execute();
        long end=System.currentTimeMillis();
        psql.close();

        if(result==false){
            return ResultUtil.error(ResultEnum.INVALID_USER);
        }
        ResultSet resultSet=psql.getResultSet();
        while(resultSet.next()) {
            return ResultUtil.success(resultSet.getString("NAME"),start-end);
        }

        return ResultUtil.error(ResultEnum.NOT_FOUND);
    }

}
