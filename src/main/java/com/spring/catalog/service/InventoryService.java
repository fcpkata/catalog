package com.spring.catalog.service;

public interface InventoryService {
	
	Double fetchInventoryPriceFor(String productId);
	void checkProductAvailablity(String productId);

}
