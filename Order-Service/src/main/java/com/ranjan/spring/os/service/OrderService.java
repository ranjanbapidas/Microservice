package com.ranjan.spring.os.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ranjan.spring.os.entity.Order;
import com.ranjan.spring.os.entity.common.Payment;
import com.ranjan.spring.os.entity.common.TransactionRequest;
import com.ranjan.spring.os.entity.common.TransactionResponse;
import com.ranjan.spring.os.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private RestTemplate template;
	
	public TransactionResponse saveOrder(TransactionRequest request) {
		
		String response="";
		
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());
		
		//do a rest call to Payment-Service for doing payment 
		
		Payment paymentResponse = template.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment,Payment.class );
		
		response = paymentResponse.getPaymentStatus().equalsIgnoreCase("Success")?"Payment Processing Successful and Order Placed":"Payment Failed And Order Added to cart";
		
		repository.save(order);
		return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionID(),response);
	}

}
