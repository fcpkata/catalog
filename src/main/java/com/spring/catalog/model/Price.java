package com.spring.catalog.model;

import java.util.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Price {
	double value;
	Currency currency;
	
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
