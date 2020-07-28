package com.spring.catalog.service;

import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.catalog.model.ProductInformation;
import com.spring.catalog.model.ProductInformations;

@Service
public class ProductInventoryService implements InventoryService {

	private RestTemplate restTemplate;

	@Autowired
	public ProductInventoryService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Double fetchInventoryPriceFor(String productId) {
		
		ResponseEntity<ProductInformations> response = restTemplate.getForEntity(System.getProperty("inventoryService") + "/v1/item/" + productId, ProductInformations.class);
		ProductInformations productInformations = response.getBody();
		
		Comparator<ProductInformation> priceSorter = (p1, p2) -> p1.getItem().getPrice().compareTo(p2.getItem().getPrice());
		
		Optional<ProductInformation> findFirst = productInformations.getProductInformations().stream().sorted(priceSorter).findFirst();

		return findFirst.map(productInfo -> productInfo.getItem().getPrice()).orElse(null);
	}

}
