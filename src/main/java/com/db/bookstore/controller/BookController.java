package com.db.bookstore.controller;

import com.db.bookstore.Util.Result;
import com.db.bookstore.service.CheckBookService;
import com.db.bookstore.service.SearchBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private CheckBookService checkBookService;
    @Autowired
    private SearchBookService searchBookService;

    @RequestMapping("/detail")
    public Result checkDetail(@RequestParam(value="isbn")String isbn) throws SQLException, ClassNotFoundException {
        return checkBookService.checkDetail(isbn);
    }

    @RequestMapping("/search")
    public Result search(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String title=request.getParameter("title");
        String isbn=request.getParameter("isbn");
        String author=request.getParameter("author");
        String publisher=request.getParameter("publisher");
        float down=0,up=0;
        int page=0;
        if(request.getParameter("down")!=null){
            down=Float.parseFloat(request.getParameter("down"));
        }
        if(request.getParameter("up")!=null){
            up=Float.parseFloat(request.getParameter("up"));
        }
        if(request.getParameter("page")!=null){
            page=Integer.parseInt(request.getParameter("page"));
        }

        return searchBookService.search(isbn,title,author,publisher,down,up,page);
    }

    @RequestMapping("/buyNow")
    public Result buyNow(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String id=request.getParameter("id");
        String isbn=request.getParameter("isbn");
        int count=Integer.parseInt(request.getParameter("count"));

        return checkBookService.buyNow(id,isbn,count);
    }

    @RequestMapping("/showAll")
    public Result showAll(@RequestParam(value = "page",defaultValue = "0",required =false)int page){
        return searchBookService.showAll(page);
    }
}
