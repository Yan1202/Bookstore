package com.db.bookstore.service;

import com.db.bookstore.connection.OracleConnection;
import com.db.bookstore.entity.Book;
import com.db.bookstore.entity.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShoppingcartService {

    //check all the books in my shopping cart
    public Map<String,Object> myCart(String id) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        PreparedStatement psql;
        Map<String,Object> map=new HashMap<>();
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

        map.put("data",items);
        map.put("cost",(end-start));

        return map;
    }

    //commit order
    public Map<String,Object>  commitOrder(String id,String isbn,int count)throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        CallableStatement psql;
        Map<String,Object> map=new HashMap<>();


        psql=conn.prepareCall("call COMMIT_ORDER(?,?,?)");
        psql.setString(1,id);
        psql.setString(2,isbn);
        psql.setInt(3,count);
        long start=System.currentTimeMillis();
        boolean result=psql.execute();
        long end=System.currentTimeMillis();

        map.put("cost",end-start);
        if(result){
            map.put("result","success");
        }else{
            map.put("result","fail");
        }

        return map;
    }

    //add item
    public Map<String,Object> addItem(String id,String isbn,int count)throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        CallableStatement psql;
        Map<String,Object> map=new HashMap<>();


        psql=conn.prepareCall("call add_item(?,?,?)");
        psql.setString(1,id);
        psql.setString(2,isbn);
        psql.setInt(3,count);
        long start=System.currentTimeMillis();
        boolean result=psql.execute();
        long end=System.currentTimeMillis();

        map.put("cost",end-start);
        if(result){
            map.put("result","success");
        }else{
            map.put("result","fail");
        }

        return map;
    }
}
