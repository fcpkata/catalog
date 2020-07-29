package com.spring.catalog.util;

import java.util.Optional;

import com.spring.catalog.model.City;

public class DomesticShippingChargeCalculator implements ShippingChargeCalculator {

	@Override
	public int fetchShippingCharges(City fromCity, City toCity, ShippingChargesService shippingCharges) {
		
		 int price = Optional.ofNullable(shippingCharges.fetchDomesticShippingCharges().get(fromCity+"-"+toCity))
				.orElse(shippingCharges.fetchDomesticShippingCharges().get(toCity+"-"+fromCity));
		 
		 return Optional.ofNullable(price).orElse(-9999);
	}

}
