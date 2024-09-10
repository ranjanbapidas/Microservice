package com.ranjan.spring.os.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranjan.spring.os.entity.Order;
import com.ranjan.spring.os.entity.common.Payment;
import com.ranjan.spring.os.entity.common.TransactionRequest;
import com.ranjan.spring.os.entity.common.TransactionResponse;
import com.ranjan.spring.os.repository.OrderRepository;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
@RefreshScope
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	@Lazy
	private RestTemplate template;
	
	@Value("${microservice.payment-service.endpoints.endpoint.uri}")
	private String ENDPOINT_URL;
	
	private Logger log = LoggerFactory.getLogger(OrderService.class);
	
	private static final String ORDER_TO_PAYMENT = "OrderToPayment";
	
	//@CircuitBreaker(name = ORDER_TO_PAYMENT, fallbackMethod = "getPaymentDetailsFallback")
	@Retry(name = ORDER_TO_PAYMENT, fallbackMethod = "getPaymentDetailsFallback")
	@RateLimiter(name = ORDER_TO_PAYMENT)
	@Bulkhead(name = ORDER_TO_PAYMENT)
	//@TimeLimiter(name = ORDER_TO_PAYMENT)
	public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
		
		String response="";
		
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());
		
		//do a rest call to Payment-Service for doing payment 
		
		log.info("OrderService request: {} ", new ObjectMapper().writeValueAsString(request));
		
		Payment paymentResponse = template.postForObject(ENDPOINT_URL, payment,Payment.class );
		
		log.info("PaymentService Response From OrderService Rest Call: {} ", new ObjectMapper().writeValueAsString(paymentResponse));
		
		response = paymentResponse.getPaymentStatus().equalsIgnoreCase("Success")?"Payment Processing Successful and Order Placed":"Payment Failed And Order Added to cart";
		
		repository.save(order);
		return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionID(),response);
	}
	
	public TransactionResponse getPaymentDetailsFallback(Exception e) {
		TransactionResponse tr= new TransactionResponse();
		tr.setOrder(new Order());
		tr.setAmount(0.0);
		tr.setTransactionId("");
		tr.setMessage("Order Failed");
		return tr;
	
	}

}
