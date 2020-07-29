package com.spring.catalog.util;

import org.springframework.stereotype.Component;

import com.spring.catalog.model.City;

@Component
public class ShippingPriceCalculatorCordinator {
	
	private ShippingChargesService shippingChargesService;
	
	private ShippingChargeCalculator shippingChargeCalculator;

	public ShippingPriceCalculatorCordinator(ShippingChargesService shippingChargesService) {
		this.shippingChargesService = shippingChargesService;
		this.shippingChargeCalculator = new LocalShippingChargeCalculator(
												new InternationalShippingChargeCalculator(
														new DomesticShippingChargeCalculator()));
	}

	public int findShippingPriceBetween(City fromCity, City toCity) {		
		return shippingChargeCalculator.fetchShippingCharges(fromCity, toCity, shippingChargesService);
	}

}
