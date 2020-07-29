package com.spring.catalog.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.spring.catalog.model.City;

@Component
public class ShippingPriceCalculator {
	
	private ShippingChargesService shippingChargesService;

	public ShippingPriceCalculator(ShippingChargesService shippingChargesService) {
		this.shippingChargesService = shippingChargesService;
	}

	public int findShippingPriceBetween(City fromCity, City toCity) {
		if(fromCity.isInternational() || toCity.isInternational()) {
			return shippingChargesService.fetchIntenationalShippingCharges();
		}
		
		if(fromCity.equals(toCity)) {			
			return 0;
		} else {
			return Optional.ofNullable(shippingChargesService.fetchDomesticShippingCharges().get(fromCity+"-"+toCity))
					.orElse(shippingChargesService.fetchDomesticShippingCharges().get(toCity+"-"+fromCity));
		}
	}

}
