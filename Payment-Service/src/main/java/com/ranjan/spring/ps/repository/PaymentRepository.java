package com.ranjan.spring.ps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ranjan.spring.ps.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	Payment findByOrderId(int orderId);

}
