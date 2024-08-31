package com.ranjan.spring.os.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ranjan.spring.os.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
