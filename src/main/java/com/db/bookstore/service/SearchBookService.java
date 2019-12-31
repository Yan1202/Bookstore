package com.db.bookstore.service;

import com.db.bookstore.Util.ConstantUtil;
import com.db.bookstore.Util.Result;
import com.db.bookstore.Util.ResultUtil;
import com.db.bookstore.connection.OracleConnection;
import com.db.bookstore.dao.BookDao;
import com.db.bookstore.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("searchBookService")
public class SearchBookService {
    @Autowired
    BookDao bookDao;

    public Result search(String isbn,String title,String author,String publisher,float down,float up,int page) throws SQLException, ClassNotFoundException {
        Connection conn= OracleConnection.getConnection();

        Map<String,Object> map=new HashMap<>();
        ArrayList<Book> books=new ArrayList<>();
        StringBuffer buffer=new StringBuffer();
        int total=-1;
        //buffer.append("select count(*) as TOTAL,ISBN,TITLE,AUTHOR,PRICE,PUBLISHER from oracle.BOOK where 1=1");
        buffer.append("select a.* from (");
        buffer.append("select rownum rn,ISBN,TITLE,AUTHOR,PRICE,PUBLISHER from oracle.BOOK where rownum<= "+ConstantUtil.PAGE_SIZE*(page+1));

        if(title!=null&&title!=""){
            buffer.append(" and TITLE LIKE \'%"+title+"%\' ");
        }
        if(isbn!=null&&isbn!=""){
            buffer.append(" and ISBN= \'"+isbn+"\' ");
        }
        if(author!=null&&author!=""){
            buffer.append(" and AUTHOR LIKE \'%"+author+"%\' ");
        }
        if(publisher!=null&&publisher!=""){
            buffer.append(" and PUBLISHER LIKE \'%"+publisher+"%\' ");
        }
        if(down>0){
            buffer.append(" and PRICE>="+down);
        }
        if(up>0){
            buffer.append(" and PRICE<="+up);
        }
        //page start from zero
        buffer.append(") a where rn>="+(1+page*ConstantUtil.PAGE_SIZE));
        //buffer.append(" ORDER BY isbn DESC LIMIT "+ ConstantUtil.PAGE_SIZE+" OFFFSET "+ConstantUtil.PAGE_SIZE*page+";");
        System.out.println(buffer.toString());
        PreparedStatement psql=conn.prepareStatement(buffer.toString());
        long start=System.currentTimeMillis();
        psql.execute();
        long end=System.currentTimeMillis();

        ResultSet resultSet=psql.getResultSet();
        while(resultSet.next()){
            String book_isbn=resultSet.getString("ISBN");
            String book_title=resultSet.getString("TITLE");
            String book_author=resultSet.getString("AUTHOR");
            //total=resultSet.getInt("TOTAL");
            float book_price=resultSet.getFloat("PRICE");
            String book_publisher=resultSet.getString("PUBLISHER");
            Book book=new Book(book_isbn,book_title,book_author,book_publisher,book_price);
            books.add(book);
        }
        psql.close();
        //map.put("current",page);
        //map.put("total",total);
        map.put("book",books);

        return ResultUtil.success(map,end-start);
    }

    public Result showAll(int page){
        Map<String,Object> map=new HashMap<String, Object>();
        Sort sort = Sort.by(Sort.Direction.DESC,"isbn");
        Page<Book> pg=bookDao.findAll(PageRequest.of(page, ConstantUtil.PAGE_SIZE,sort));
        List<Book> books=pg.getContent();

        map.put("current",page);
        map.put("total",pg.getTotalPages());
        map.put("list",books);

        return ResultUtil.success(map);
    }

    public Result search(String isbn){
        Book book=bookDao.findByIsbn(isbn);

        return ResultUtil.success(book);
    }

}
