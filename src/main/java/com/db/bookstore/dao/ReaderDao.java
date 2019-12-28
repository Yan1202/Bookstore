package com.db.bookstore.dao;

import com.db.bookstore.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ReaderDao extends JpaRepository<Reader, String>, JpaSpecificationExecutor {
    Reader findByUser_id(String id);
}
