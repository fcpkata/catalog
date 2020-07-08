package com.spring.catalog.model;

import java.util.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
public class Price {
	private double value;
	private Currency currency;
	
	@Override
	public String toString() {
		return "Price =" + value + " " + currency;
	}
	
	public static Price prepareINRPriceFor(double value) {
		return Price.builder().currency(Currency.getInstance("INR")).value(value).build();
	}
	
	public static Price prepareUSDPriceFor(double value) {
		return Price.builder().currency(Currency.getInstance("USD")).value(value).build();
	}
	
}
