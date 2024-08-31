package com.ranjan.spring.os.entity.common;

import com.ranjan.spring.os.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
	private Order order;
	private double amount;
	private String transactionId;
	String message;

}
