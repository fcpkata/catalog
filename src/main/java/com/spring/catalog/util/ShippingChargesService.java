package com.spring.catalog.util;

import java.util.Map;

public interface ShippingChargesService {
	
	Map<String, Integer> fetchDomesticShippingCharges();
	
	int fetchIntenationalShippingCharges();

}
