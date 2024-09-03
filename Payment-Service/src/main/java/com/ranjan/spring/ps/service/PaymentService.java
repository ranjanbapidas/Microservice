package com.ranjan.spring.ps.service;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjan.spring.ps.entity.Payment;
import com.ranjan.spring.ps.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	public Payment doPayment(Payment payment) {
		payment.setPaymentStatus(paymentProcessing());
		payment.setTransactionID(UUID.randomUUID().toString());
		return paymentRepository.save(payment);
	}
	
	public String paymentProcessing() {
		//api should be third party payment gateway (paypal,paytm...)
		return new Random().nextBoolean()?"Success":"Failed";
	}

	public Payment findPaymentHistoryByOrderId(int orderId) {
		// TODO Auto-generated method stub
		//Payment payment = paymentRepository.findByOrderId(orderId);
		return paymentRepository.findByOrderId(orderId);
	}

}
