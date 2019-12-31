package com.db.bookstore.dao;

import com.db.bookstore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDao extends JpaRepository<Order,String> {
    Page<Order> findAll(Pageable pegeable);
    Optional<Order> findById(String id);
    Page<Order> findByUserId(String user,Pageable pageable);


}
