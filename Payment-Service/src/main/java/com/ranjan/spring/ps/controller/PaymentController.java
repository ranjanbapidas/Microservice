package com.ranjan.spring.ps.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjan.spring.ps.entity.Payment;
import com.ranjan.spring.ps.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService service;
	
	@PostMapping("/doPayment")
	public Payment doPayment(@RequestBody Payment payment) {
		return service.doPayment(payment);
	}
	
	
}
