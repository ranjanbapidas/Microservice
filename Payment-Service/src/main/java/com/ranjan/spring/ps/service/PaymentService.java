package com.ranjan.spring.ps.service;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranjan.spring.ps.entity.Payment;
import com.ranjan.spring.ps.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	private Logger log = LoggerFactory.getLogger(PaymentService.class);
	
	public Payment doPayment(Payment payment) throws JsonProcessingException {
		payment.setPaymentStatus(paymentProcessing());
		payment.setTransactionID(UUID.randomUUID().toString());
		log.info("PaymentService request: {} ", new ObjectMapper().writeValueAsString(payment));
		return paymentRepository.save(payment);
	}
	 
	public String paymentProcessing() {
		//api should be third party payment gateway (paypal,paytm...)
		return new Random().nextBoolean()?"Success":"Failed";
	}

	public Payment findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
		// TODO Auto-generated method stub
		Payment payment = paymentRepository.findByOrderId(orderId);
		log.info("PaymentService findPaymentHistoryByOrderId request: {} ", new ObjectMapper().writeValueAsString(payment));
		return paymentRepository.findByOrderId(orderId);
	}

}
