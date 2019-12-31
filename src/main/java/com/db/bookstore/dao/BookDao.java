package com.db.bookstore.dao;

import com.db.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookDao extends JpaRepository<Book, String>{
    Book findByIsbn(String isbn);
    Page<Book> findAll(Pageable pegeable);

}

