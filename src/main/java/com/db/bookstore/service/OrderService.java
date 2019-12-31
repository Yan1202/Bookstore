package com.db.bookstore.service;

import com.db.bookstore.Util.ConstantUtil;
import com.db.bookstore.Util.Result;
import com.db.bookstore.Util.ResultEnum;
import com.db.bookstore.Util.ResultUtil;
import com.db.bookstore.dao.OrderDao;
import com.db.bookstore.entity.Book;
import com.db.bookstore.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("orderService")
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public Result showAll(int page){
        Map<String,Object> map=new HashMap<String, Object>();
        System.out.println("2");

        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        System.out.println("3");

        Page<Order> pg=orderDao.findAll(PageRequest.of(page, ConstantUtil.PAGE_SIZE,sort));
        System.out.println("4");

        List<Order> orders=pg.getContent();
        System.out.println("5");

        map.put("current",page);
        map.put("total",pg.getTotalPages());
        map.put("item",orders);

        return ResultUtil.success(map);
    }

    public Result search(String id){
        Optional<Order> optional=orderDao.findById(id);

        return ResultUtil.success(optional.get());
    }

    public Result updateOrder(String id, String isbn, String user, Date date, int num, float score, String content){
        //error input
        if(id==null||isbn==""){
            return ResultUtil.error(ResultEnum.FAIL);
        }

        Order order=orderDao.findById(id).get();
        if(order==null){
            return ResultUtil.error(ResultEnum.NOT_FOUND);
        }
        //modify infos
        if(isbn!=null&&isbn!=""){
            order.setIsbn(isbn);
        }
        if(user!=null&&user!=""){
            order.setUserId(user);
        }
        if(content!=null&&content!=""){
            order.setComment(content);
        }
        if(date!=null){
            order.setOrderDate(date);
        }
        if(num!=-1){
            order.setNum(num);
        }
        if(score!=-1){
            order.setScore(score);
        }

        //save and flush
        orderDao.saveAndFlush(order);

        return ResultUtil.success();
    }

    public Result deleteOrder(String id){
        Optional<Order> optional=orderDao.findById(id);
        if(!optional.isPresent()){
            return ResultUtil.error(ResultEnum.NOT_FOUND);
        }
        orderDao.delete(optional.get());
        return ResultUtil.success();

    }
}
