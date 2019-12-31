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
import java.util.Optional;

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
        readerDao.delete(reader);
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
        if(isbn==null||isbn==""){
            System.out.println("fail");
            return ResultUtil.error(ResultEnum.FAIL);
        }

        Book b=bookDao.findByIsbn(isbn);
        if(b!=null) {
            return ResultUtil.error(ResultEnum.FAIL);
        }

        Book book=new Book(isbn,title,author,publisher,price,publishDate,introduction,stock);
        bookDao.save(book);
        return ResultUtil.success();
    }

    public Result updateBook(String isbn, String title, String author, String publisher, Date publishDate,String introduction,float price,int stock){
        //error input
        if(isbn==null||isbn==""){
            return ResultUtil.error(ResultEnum.FAIL);
        }

        Book book=bookDao.findByIsbn(isbn);
        if(book==null){
            return ResultUtil.error(ResultEnum.NOT_FOUND);
        }
        //modify infos
        if(title!=null&&title!=""){
            book.setTitle(title);
        }
        if(author!=null&&author!=""){
            book.setAuthor(author);
        }
        if(publisher!=null&&publisher!=""){
            book.setPublisher(publisher);
        }
        if(introduction!=null&&introduction!=""){
            book.setIntroduction(introduction);
        }
        if(publishDate!=null){
            book.setPublish_time(publishDate);
        }
        if(stock!=-1){
            book.setStock(stock);
        }
        if(price!=-1){
            book.setPrice(price);
        }

        //save and flush
        bookDao.saveAndFlush(book);

        return ResultUtil.success();
    }

    public Result login(String id,String pwd) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        PreparedStatement psql;

        psql=conn.prepareStatement("select ADMINISTRATOR.NAME as NAME from oracle.ADMINISTRATOR where ADMIN_ID=? and PASSWORD=?");
        psql.setString(1,id);
        psql.setString(2,pwd);
        long start=System.currentTimeMillis();
        psql.execute();
        long end=System.currentTimeMillis();


        ResultSet resultSet=psql.getResultSet();
        while(resultSet.next()) {
            String name=resultSet.getString("NAME");
            psql.close();
            return ResultUtil.success(name,end-start);
        }

        psql.close();
        return ResultUtil.error(ResultEnum.INVALID_USER,end-start);
    }

    public Result addReader(String id,String name,String gender,String pwd,Date birth,int tel,String email){
        Optional<Reader> optional=readerDao.findById(id);
        if(optional.isPresent()){
            return ResultUtil.error(ResultEnum.OCCUPIED);
        }
        Reader reader=new Reader(id,name,gender,birth,tel,email,pwd);
        readerDao.saveAndFlush(reader);
        return ResultUtil.success();
    }
}
