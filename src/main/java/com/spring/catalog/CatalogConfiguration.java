package com.spring.catalog;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.spring.catalog.service.InventoryService;
import com.spring.catalog.service.ProductInventoryService;

@Configuration
public class CatalogConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@Qualifier("productinventory")
	public InventoryService createProductInventoryService(RestTemplate restTemplate) {
		return new ProductInventoryService(restTemplate);
	}
}
