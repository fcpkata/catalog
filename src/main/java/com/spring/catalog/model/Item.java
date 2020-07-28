package com.spring.catalog.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	
	private String productId;
	private Double price;
	private Integer quantity;
	private Integer shippingPrice;
}
