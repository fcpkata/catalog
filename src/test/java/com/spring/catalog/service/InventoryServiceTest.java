package com.spring.catalog.service;

import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.spring.catalog.model.ProductInformation;


class InventoryServiceTest {

	private InventoryService inventoryService;
	@Mock
	private RestTemplate mockRestTemplate;

	@Before
	public void setUp() {
		System.setProperty("inventoryService", "url");
	}

	@After
	public void tearDown() {
		System.clearProperty("inventoryService");
	}

	@Test
	public void shouldReturnPriceForProducts() {
		inventoryService = new InventoryService(mockRestTemplate);
		ProductInformation[] body ;
		ResponseEntity<ProductInformation[]> responseEntity = new ResponseEntity<ProductInformation[]>(body, HttpStatus.OK);
		when(mockRestTemplate.getForEntity("url/v1/item/123",Any.class)).thenReturn(responseEntity);
		Double productPrice = inventoryService.fecthInventoryPricefor("");
		Assert.assertEquals(Double.valueOf(100.0), productPrice);
	}

}
