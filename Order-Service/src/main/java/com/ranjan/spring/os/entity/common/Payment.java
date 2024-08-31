package com.ranjan.spring.os.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	private int paymentId;
	private String paymentStatus;
	private String transactionID;
	private int orderId;
	private double amount;

}
