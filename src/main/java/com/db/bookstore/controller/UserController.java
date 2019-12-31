package com.db.bookstore.controller;

import com.db.bookstore.Util.Result;
import com.db.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @RequestMapping("/infos")
    public Result getMyInfos(@RequestParam(value="id")String id) throws SQLException, ClassNotFoundException {
        return userService.getInfos(id);
    }

    @RequestMapping("/modify")
    public Result modifyInfos(HttpServletRequest request) throws ParseException {
        String id=request.getParameter("id");
        String gender=request.getParameter("gender");
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        int tel=0;
        Date date=null;
        if(request.getParameter("tel")!=null){
            tel=Integer.parseInt(request.getParameter("tel"));
        }
        if(request.getParameter("birth")!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date=sdf.parse(request.getParameter("birth"));
        }

        return userService.modifyInfos(id,name,gender,date,tel,email);
    }

    @RequestMapping("/login")
    public Result login(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String id=request.getParameter("id");
        String pwd=request.getParameter("pwd");
        return userService.login(id,pwd);
    }

    @RequestMapping("/orders")
    public Result myOrder(@RequestParam(value="id")String id,@RequestParam(value="page",required = false,defaultValue = "0")int page) throws SQLException, ClassNotFoundException {
        return userService.myOrder(id,page);
    }

}
