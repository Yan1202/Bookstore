package com.db.bookstore.controller;

import com.db.bookstore.Util.Result;
import com.db.bookstore.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/deleteReader")
    public Result deleteReader(@RequestParam(value="id")String id){

        return adminService.deleteReader(id);
    }

    @RequestMapping("/deleteBook")
    public Result deleteBook(@RequestParam(value="isbn")String isbn){

        return adminService.deleteBook(isbn);
    }

    @RequestMapping("/addBook")
    public Result addBook(HttpServletRequest request) throws ParseException {
        String isbn=request.getParameter("isbn");
        String title=request.getParameter("title");
        String author=request.getParameter("author");
        String publisher=request.getParameter("publisher");
        String introduction=request.getParameter("introduction");

        int stock=0;
        float price=0;
        Date date=null;
        if(request.getParameter("stock")!=null){
            stock=Integer.parseInt(request.getParameter("stock"));
        }
        if(request.getParameter("price")!=null){
            price=Float.parseFloat(request.getParameter("price"));
        }
        if(request.getParameter("date")!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date=sdf.parse(request.getParameter("date"));
        }

        return adminService.addBook(isbn,title,author,publisher,date,introduction,price,stock);

    }

    @RequestMapping("/login")
    public Result login(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String id=request.getParameter("id");
        String pwd=request.getParameter("pwd");
        return adminService.login(id,pwd);
    }

    @RequestMapping("/updateBook")
    public Result updateBook(HttpServletRequest request) throws ParseException {
        String isbn=request.getParameter("isbn");
        String title=request.getParameter("title");
        String author=request.getParameter("author");
        String publisher=request.getParameter("publisher");
        String introduction=request.getParameter("introduction");

        int stock=-1;
        float price=-1;
        Date date=null;
        if(request.getParameter("stock")!=null){
            stock=Integer.parseInt(request.getParameter("stock"));
        }
        if(request.getParameter("price")!=null){
            price=Float.parseFloat(request.getParameter("price"));
        }
        if(request.getParameter("date")!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date=sdf.parse(request.getParameter("date"));
        }

        return adminService.updateBook(isbn,title,author,publisher,date,introduction,price,stock);
    }
}
