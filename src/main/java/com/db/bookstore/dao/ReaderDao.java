package com.db.bookstore.dao;

import com.db.bookstore.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReaderDao extends JpaRepository<Reader, String>{
    Optional<Reader> findById(String user_id);
}
