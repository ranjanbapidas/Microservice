package com.ranjan.spring.os.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjan.spring.os.entity.Order;
import com.ranjan.spring.os.entity.common.Payment;
import com.ranjan.spring.os.entity.common.TransactionRequest;
import com.ranjan.spring.os.entity.common.TransactionResponse;
import com.ranjan.spring.os.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/bookOrder")
	public TransactionResponse bookOrder(@RequestBody TransactionRequest request) {
		//return null;
		return orderService.saveOrder(request);
		
		
		//do a rest call to Payment-Service for doing payment and pass the order id
	}

}
