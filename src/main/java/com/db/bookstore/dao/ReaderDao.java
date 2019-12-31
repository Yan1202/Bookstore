package com.db.bookstore.dao;

import com.db.bookstore.entity.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReaderDao extends JpaRepository<Reader, String>{
    Page<Reader> findAll(Pageable pegeable);
    Optional<Reader> findById(String s);
}
