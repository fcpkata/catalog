package com.spring.catalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.spring.catalog.exception.ProductNotFoundException;
import com.spring.catalog.model.Item;
import com.spring.catalog.model.ProductInformation;
import com.spring.catalog.model.ProductInformations;

@RunWith(PowerMockRunner.class)
public class ProductInventoryServiceTest {

	private InventoryService inventoryService;
	
	@Mock
	private RestTemplate mockRestTemplate;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private String url = "http://inventory-jx-production.35.224.175.156.nip.io";

	@Before
	public void setUp() {
		PowerMockito.mockStatic(System.class);
		PowerMockito.when(System.getenv(eq("inventoryService"))).thenReturn("url");
		//Assert.assertEquals("url", System.getenv("inventoryService"));
		inventoryService = new ProductInventoryService(mockRestTemplate);
	}

	@After
	public void tearDown() {
		System.clearProperty("inventoryService");
	}
	
	@Test
	public void shouldReturnNullPriceForNoSellerProduct() {
		
		prepareMockResponceWithNumerOfSellerOf(0);
		
		Double productPrice = inventoryService.fetchInventoryPriceFor("Product_Id");
		Assert.assertEquals(null, productPrice);
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
	
	@Test
	@Ignore
	public void shouldThrowProductNotFoundExceptionForInvalidProductId() {
		expectedException.equals(ProductNotFoundException.class);
		expectedException.expectMessage("Product Not Found");
		when(mockRestTemplate.getForEntity(eq(url+"v1/item/Invalid_id"), eq(ProductInformations.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Product Not Found"));
		inventoryService.checkProductAvailablity("Invalid_id");
	}
	
	@Test
	@Ignore
	public void shouldThrowHttpClientErrorExceptionWhenThereIsNoQuantity() {
		
		prepareMockResponseWithNumerOfSellerAndZeroQuantity(4, 0);
		expectedException.equals(HttpClientErrorException.class);
		expectedException.expectMessage("No quantity available of this item");
		inventoryService.checkProductAvailablity("Product_Id");
	}
	
	@Test
	@Ignore
	public void shouldRetunNothingWhenQuantityIsAvailable() {
		prepareMockResponseWithNumerOfSellerAndZeroQuantity(4, 4);
		inventoryService.checkProductAvailablity("Product_Id");
	}
	
	private void prepareMockResponceWithNumerOfSellerOf(int numberOfSellers) {
		ProductInformations body = new ProductInformations(prepareProductInformationsFor(numberOfSellers));
		ResponseEntity<ProductInformations> responseEntity = new ResponseEntity<ProductInformations>(body, HttpStatus.OK);
		
		when(mockRestTemplate.getForEntity(eq("null/v1/item/Product_Id"), eq(ProductInformations.class))).thenReturn(responseEntity);
	}
	

	private List<ProductInformation> prepareProductInformationsFor(int numberOfItems) {
		
		List<ProductInformation> productInformations = new ArrayList<>();
		
		IntStream.range(0, numberOfItems).forEach(num -> {
			ProductInformation productInformation = new ProductInformation("Seller_Id_"+num, Item.builder()
																								.productId("Product_Id_"+num)
																								.price(100.0-num)
																								.quantity(num)
																								.build() );
			productInformations.add(productInformation);
		});
		return productInformations;
	}
	
	private void prepareMockResponseWithNumerOfSellerAndZeroQuantity(int numberOfSellers, int quantity) {
		ProductInformations body = new ProductInformations(prepareProductInformationsForZeroQuantity(numberOfSellers, quantity));
		ResponseEntity<ProductInformations> responseEntity = new ResponseEntity<ProductInformations>(body, HttpStatus.OK);
		
		when(mockRestTemplate.getForEntity(eq(url+"/v1/item/Product_Id"), eq(ProductInformations.class))).thenReturn(responseEntity);
	}
	
	private List<ProductInformation> prepareProductInformationsForZeroQuantity(int numberOfItems, int quantity) {
		
		List<ProductInformation> productInformations = new ArrayList<>();
		
		IntStream.range(0, numberOfItems).forEach(num -> {
			ProductInformation productInformation = new ProductInformation("Seller_Id_"+num, Item.builder()
																								.productId("Product_Id_"+num)
																								.price(100.0-num)
																								.quantity(quantity)
																								.build() );
			productInformations.add(productInformation);
		});
		return productInformations;
	}
	
}
