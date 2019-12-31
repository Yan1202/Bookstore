package com.db.bookstore.controller;

import com.db.bookstore.Util.Result;
import com.db.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/showAll")
    public Result showAll(@RequestParam(value="page",required = false,defaultValue = "0")int page){
        return orderService.showAll(page);
    }

    @RequestMapping("/modify")
    public Result modify(HttpServletRequest request) throws ParseException {
        String user=request.getParameter("user");
        String id=request.getParameter("id");
        String isbn=request.getParameter("isbn");
        String content=request.getParameter("content");
        Date date=null;
        int count=-1;
        float score=-1;
        if(request.getParameter("date")!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date=sdf.parse(request.getParameter("date"));
        }
        if(request.getParameter("count")!=null){
            count=Integer.parseInt(request.getParameter("count"));
        }
        if(request.getParameter("score")!=null){
            score=Float.parseFloat(request.getParameter("score"));
        }
        return orderService.updateOrder(id,isbn,user,date,count,score,content);
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam(value = "id")String id){
        return orderService.deleteOrder(id);
    }
}
