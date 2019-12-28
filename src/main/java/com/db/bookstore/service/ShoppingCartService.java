package com.db.bookstore.service;

import com.db.bookstore.Util.Result;
import com.db.bookstore.Util.ResultEnum;
import com.db.bookstore.Util.ResultUtil;
import com.db.bookstore.connection.OracleConnection;
import com.db.bookstore.entity.CartItem;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

@Service("shoppingCartService")
public class ShoppingCartService {

    //check all the books in my shopping cart
    public Result myCart(String id) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        PreparedStatement psql;
        ArrayList<CartItem> items=new ArrayList<>();


        psql=conn.prepareStatement("select isbn,title,price,stock,book_num from BOOK join SHOPPINGCART " +
                "where  SHOPPINGCART.USER_ID= ?");
        psql.setString(1,id);
        long start=System.currentTimeMillis();
        psql.execute();
        long end=System.currentTimeMillis();

        ResultSet resultSet=psql.getResultSet();
        while(resultSet.next()){
            String book_isbn=resultSet.getString("isbn");
            String title=resultSet.getString("title");
            float price=resultSet.getFloat("price");
            int stock=resultSet.getInt("stock");
            int num=resultSet.getInt("book_num");

            CartItem item=new CartItem(num,book_isbn,title,price,stock);
            items.add(item);
        }
        psql.close();

        return ResultUtil.success(items,end-start);
    }

    //commit order
    public Result  commitOrder(String id,String isbn,int count)throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        CallableStatement psql;

        psql=conn.prepareCall("call COMMIT_ORDER(?,?,?)");
        psql.setString(1,id);
        psql.setString(2,isbn);
        psql.setInt(3,count);
        long start=System.currentTimeMillis();
        boolean result=psql.execute();
        long end=System.currentTimeMillis();

        if(result==false){
            return ResultUtil.error(ResultEnum.FAIL);
        }

        return ResultUtil.success(end-start);
    }

    //add item
    public Result addItem(String id,String isbn,int count)throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        CallableStatement psql;

        psql=conn.prepareCall("call ADD_ITEM(?,?,?)");
        psql.setString(1,id);
        psql.setString(2,isbn);
        psql.setInt(3,count);
        long start=System.currentTimeMillis();
        boolean result=psql.execute();
        long end=System.currentTimeMillis();

        if(result==false){
            return ResultUtil.error(ResultEnum.FAIL);
        }

        return ResultUtil.success(end-start);
    }

    //delete item
    public Result deleteItem(String id,String isbn)throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        PreparedStatement psql;

        psql=conn.prepareStatement("delete from SHOPPINGCART " +
                "where ISBN=? and USER_ID=?");
        psql.setString(1,isbn);
        psql.setString(2,id);
        long start=System.currentTimeMillis();
        boolean result=psql.execute();
        long end=System.currentTimeMillis();

        if(result==false){
            return ResultUtil.error(ResultEnum.FAIL);
        }
        return ResultUtil.success(end-start);
    }

    //reduce item
    public Result reduceItem(String id,String isbn,int count)throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        PreparedStatement psql;

        if(count<1){
            return ResultUtil.error(ResultEnum.FAIL);
        }

        psql=conn.prepareStatement("update SHOPPINGCART set BOOK_NUM=?" +
                "where USER_ID=? and ISBN=?");
        psql.setInt(1,count);
        psql.setString(2,id);
        psql.setString(3,isbn);
        long start=System.currentTimeMillis();
        boolean result=psql.execute();
        long end=System.currentTimeMillis();

        if(result==false){
            return ResultUtil.error(ResultEnum.FAIL);
        }

        return ResultUtil.success(end-start);
    }

}
