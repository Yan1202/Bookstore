package com.db.bookstore.dao;

import com.db.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookDao extends JpaRepository<Book, String>, JpaSpecificationExecutor {
    Book findByIsbn(String isbn);
}

