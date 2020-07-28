package com.spring.catalog.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.catalog.model.ProductInformation;

@Service
public class InventoryService {

	private RestTemplate restTemplate;

	@Autowired
	public InventoryService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Double fecthInventoryPricefor(String productId) {

		String inventoryServiceUrl = System.getProperty("inventoryService");
		ResponseEntity<ProductInformation[]> response = restTemplate
				.getForEntity(inventoryServiceUrl + "/v1/item/" + productId, ProductInformation[].class);
		ProductInformation[] productInformations = response.getBody();
		List<ProductInformation> asList = Arrays.asList(productInformations);
		Comparator<ProductInformation> priceSorter = (p1, p2) -> p2.getItem().getPrice()
				.compareTo(p1.getItem().getPrice());
		Optional<ProductInformation> findFirst = asList.stream().sorted(priceSorter).findFirst();

		if (findFirst.isPresent()) {
			return findFirst.get().getItem().getPrice();
		} else {
			return null;
		}
	}

}
