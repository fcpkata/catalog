package com.spring.catalog.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.spring.catalog.exception.ProductNotFoundException;
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

	@Override
	public void checkProductAvailablity(String productId) {
		String url = System.getenv("inventoryService");
		ResponseEntity<ProductInformations> response;
		try {
			response = restTemplate.getForEntity(url+ "/v1/item/"+productId, ProductInformations.class);
		} catch(HttpClientErrorException ex) {
			throw new ProductNotFoundException();
		}
		getQuantity(response.getBody());

	}

	private void getQuantity(ProductInformations productInformation) {
		boolean isAvailable = productInformation.getProductInformations().stream().anyMatch(product -> product.getItem().getQuantity() > 0);
		if(!isAvailable)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No quantity available of this item");

	}

}
