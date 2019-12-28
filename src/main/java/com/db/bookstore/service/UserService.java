package com.db.bookstore.service;

import com.db.bookstore.Util.Result;
import com.db.bookstore.Util.ResultEnum;
import com.db.bookstore.Util.ResultUtil;
import com.db.bookstore.connection.OracleConnection;
import com.db.bookstore.dao.ReaderDao;
import com.db.bookstore.entity.Book;
import com.db.bookstore.entity.CartItem;
import com.db.bookstore.entity.OrderList;
import com.db.bookstore.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Service("userService")
public class UserService {
    @Autowired
    private ReaderDao readerDao;

    public Result getInfos(String id) throws SQLException, ClassNotFoundException {

        Reader reader=readerDao.findByUser_id(id);

        return ResultUtil.success(reader);
    }

   public Result modifyInfos(String id,String name,String gender,Date birth,int tel,String email){

        //search user
        Reader reader=readerDao.findByUser_id(id);
        if(reader==null){
            return ResultUtil.error(ResultEnum.NOT_FOUND);
        }
        //modify infos
        if(name!=null&&name!=""){
            reader.setName(name);
        }
        if(gender!=null&&gender!=""){
            reader.setGender(gender);
        }
        if(birth!=null){
            reader.setBirth_date(birth);
        }
        if(tel!=0){
            reader.setTel(tel);
        }
       if(email!=null&&email!=""){
           reader.setEmail(email);
       }
       //save and flush
        readerDao.saveAndFlush(reader);

        return ResultUtil.success();
    }

   public Result login(String id,String pwd) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        PreparedStatement psql;

        psql=conn.prepareStatement("select READER.NAME as NAME from READER where USER_ID=? and PASSWORD=?");
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


    public Result myOrder(String id) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();
        ArrayList<OrderList> items=new ArrayList<>();


        PreparedStatement psql;

        psql=conn.prepareStatement("select ISBN,ORDER_DATE,ORDER_NUM,PRICE from BOOK_ORDER JOIN ORACLE.BOOK " +
                "ON BOOK.ISBN=BOOK_ORDER.ISBN where USER_ID=? ");
        psql.setString(1,id);
        long start=System.currentTimeMillis();
        boolean result=psql.execute();
        long end=System.currentTimeMillis();
        psql.close();

        ResultSet resultSet=psql.getResultSet();
        while(resultSet.next()) {
            String isbn=resultSet.getString("ISBN");
            Date date=resultSet.getDate("ORDER_DATE");
            int num=resultSet.getInt("ORDER_NUM");
            float price=resultSet.getFloat("PRICE");

            OrderList order=new OrderList(isbn,date,num,price*num);
            items.add(order);
        }

        return ResultUtil.success(items,start-end);
    }

}
