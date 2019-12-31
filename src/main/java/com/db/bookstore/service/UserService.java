package com.db.bookstore.service;

import com.db.bookstore.Util.ConstantUtil;
import com.db.bookstore.Util.Result;
import com.db.bookstore.Util.ResultEnum;
import com.db.bookstore.Util.ResultUtil;
import com.db.bookstore.connection.OracleConnection;
import com.db.bookstore.dao.OrderDao;
import com.db.bookstore.dao.ReaderDao;
import com.db.bookstore.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service("userService")
public class UserService {
    @Autowired
    private ReaderDao readerDao;
    @Autowired
    private OrderDao orderDao;

    public Result getInfos(String id) throws SQLException, ClassNotFoundException {

        Reader reader=readerDao.findById(id).get();

        return ResultUtil.success(reader);
    }

   public Result modifyInfos(String id,String name,String gender,Date birth,int tel,String email){

        //search user
        Reader reader=readerDao.findById(id).get();
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

        psql=conn.prepareStatement("select NAME from oracle.READER where USER_ID=? and PASSWORD=?");
        psql.setString(1,id);
        psql.setString(2,pwd);
        long start=System.currentTimeMillis();
        psql.execute();
        long end=System.currentTimeMillis();

        ResultSet resultSet=psql.getResultSet();

       while(resultSet.next()) {
           System.out.println("login success");
           String name=resultSet.getString("NAME");
           psql.close();
           return ResultUtil.success(name,end-start);
        }

       psql.close();
       System.out.println("login fail");
       return ResultUtil.error(ResultEnum.INVALID_USER,end-start);
    }

    public Result myOrder(String id,int page) throws SQLException, ClassNotFoundException {
        Map<String,Object> map=new HashMap<String, Object>();
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        long start=System.currentTimeMillis();
        Page<Order> pg=orderDao.findByUserId(id,PageRequest.of(page, ConstantUtil.PAGE_SIZE,sort));
        List<Order> orders=pg.getContent();
        long end=System.currentTimeMillis();

        map.put("current",page);
        map.put("total",pg.getTotalPages());
        map.put("item",orders);

        return ResultUtil.success(map,end-start);
    }

    public Result showAll(int page){
        Map<String,Object> map=new HashMap<String, Object>();
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        long start=System.currentTimeMillis();
        Page<Reader> pg=readerDao.findAll(PageRequest.of(page, ConstantUtil.PAGE_SIZE,sort));
        List<Reader> readers=pg.getContent();
        long end=System.currentTimeMillis();


        map.put("current",page);
        map.put("total",pg.getTotalPages());
        map.put("list",readers);

        return ResultUtil.success(map,end-start);
    }

}
