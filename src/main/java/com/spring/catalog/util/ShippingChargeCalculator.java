package com.spring.catalog.util;

import com.spring.catalog.model.City;

public interface ShippingChargeCalculator {
	
	
	int fetchShippingCharges(City fromCity, City toCity, ShippingChargesService shippingCharges);

}
