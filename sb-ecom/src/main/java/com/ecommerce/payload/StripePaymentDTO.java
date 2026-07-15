package com.ecommerce.payload;

import java.util.Map;

import com.ecommerce.model.Address;

import lombok.Data;

@Data
public class StripePaymentDTO {
	private Long amount;
	private String currency;
	private String email;
	private String name;
	private Address address;
	private String description;
	private Map<String, String> metadata;
}
