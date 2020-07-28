package com.spring.catalog.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.spring.catalog.model.Item;
import com.spring.catalog.model.ProductInformation;
import com.spring.catalog.model.ProductInformations;

@RunWith(MockitoJUnitRunner.class)
public class ProductInventoryServiceTest {

	private InventoryService inventoryService;
	
	@Mock
	private RestTemplate mockRestTemplate;

	@Before
	public void setUp() {
		System.setProperty("inventoryService", "url");
		inventoryService = new ProductInventoryService(mockRestTemplate);
	}

	@After
	public void tearDown() {
		System.clearProperty("inventoryService");
	}
	
	@Test
	public void shouldReturnLeastPriceForNoSellerProduct() {
		
		prepareMockResponceWithNumerOfSellerOf(0);
		
		Double productPrice = inventoryService.fetchInventoryPriceFor("Product_Id");
		Assert.assertEquals(Double.valueOf(0.0), productPrice);
	}

	@Test
	public void shouldReturnLeastPriceForSingleSellerProduct() {
		
		prepareMockResponceWithNumerOfSellerOf(1);
		
		Double productPrice = inventoryService.fetchInventoryPriceFor("Product_Id");
		Assert.assertEquals(Double.valueOf(100.0), productPrice);
	}
	
	@Test
	public void shouldReturnLeastPriceForTwoSellerProduct() {
		
		prepareMockResponceWithNumerOfSellerOf(2);
		
		Double productPrice = inventoryService.fetchInventoryPriceFor("Product_Id");
		Assert.assertEquals(Double.valueOf(99.0), productPrice);
	}
	
	@Test
	public void shouldReturnLeastPriceForThreeMultiSellerProduct() {
		
		prepareMockResponceWithNumerOfSellerOf(3);
		
		Double productPrice = inventoryService.fetchInventoryPriceFor("Product_Id");
		Assert.assertEquals(Double.valueOf(98.0), productPrice);
	}
	
	private void prepareMockResponceWithNumerOfSellerOf(int numberOfSellers) {
		ProductInformations body = new ProductInformations(prepareProductInformationsFor(numberOfSellers));
		ResponseEntity<ProductInformations> responseEntity = new ResponseEntity<ProductInformations>(body, HttpStatus.OK);
		
		when(mockRestTemplate.getForEntity(eq("url/v1/item/Product_Id"), eq(ProductInformations.class))).thenReturn(responseEntity);
	}
	

	private List<ProductInformation> prepareProductInformationsFor(int numberOfItems) {
		
		List<ProductInformation> productInformations = new ArrayList<>();
		
		IntStream.range(0, numberOfItems).forEach(num -> {
			ProductInformation productInformation = new ProductInformation("Seller_Id_"+num, Item.builder()
																								.productId("Product_Id_"+num)
																								.price(100.0-num)
																								.quantity(num)
																								.shippingPrice(20)
																								.build() );
			productInformations.add(productInformation);
		});
		return productInformations;
	}

}
