package com.db.bookstore.controller;

import com.db.bookstore.Util.Result;
import com.db.bookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RequestMapping("/shopping")
public class ShoppingController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @RequestMapping("/shoppingCart")
    public Result myCart(@RequestParam(value="id")String id) throws SQLException, ClassNotFoundException {
        return shoppingCartService.myCart(id);
    }

    @RequestMapping("/commit")
    public Result commitOrder(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String id=request.getParameter("id");
        String isbn=request.getParameter("isbn");
        int count=Integer.parseInt(request.getParameter("count"));

        return shoppingCartService.commitOrder(id,isbn,count);
    }

    @RequestMapping("/add")
    public Result addItem(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String id=request.getParameter("id");
        String isbn=request.getParameter("isbn");
        int count=Integer.parseInt(request.getParameter("count"));

        return shoppingCartService.addItem(id,isbn,count);
    }

    @RequestMapping("/delete")
    public Result deleteItem(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String id=request.getParameter("id");
        String isbn=request.getParameter("isbn");

        return shoppingCartService.deleteItem(id,isbn);
    }

    @RequestMapping("/reduce")
    public Result reduceItem(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String id=request.getParameter("id");
        String isbn=request.getParameter("isbn");
        int count=Integer.parseInt(request.getParameter("count"));

        return shoppingCartService.reduceItem(id,isbn,count);
    }
}
