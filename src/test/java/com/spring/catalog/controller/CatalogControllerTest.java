package com.spring.catalog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.any;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.spring.catalog.exception.ProductNotFoundException;
import com.spring.catalog.model.FilterCriteria;
import com.spring.catalog.model.Product;
import com.spring.catalog.service.CatalogService;
import com.spring.catalog.service.InventoryService;

@RunWith(MockitoJUnitRunner.class)
public class CatalogControllerTest {

	@InjectMocks
	private CatalogController catlogController;
	@Mock
	private CatalogService catalogService;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	@Mock
	private InventoryService mockInventoryService;

	@Test
	public void shouldReturnCatalogWhenGetCatalogIsCalled() throws Exception {
		
		Product product = Product.builder().build();
		Mockito.when(catalogService.getProducts(Mockito.any(FilterCriteria.class))).thenReturn(Arrays.asList(product));
		
		ResponseEntity<List<Product>> response = catlogController.getCatalog(null, null, null);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().get(0)).isEqualTo(product);
	}
	
	@Test
	public void shouldReturnSingleProduct() {
		Product product = Product.builder().build();
		Mockito.when(catalogService.getProduct("PD001"))
			.thenReturn(product);
		
		ResponseEntity<Product> response = catlogController.getProductDetailsById("PD001");
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(product);
	}
	
	@Test
	public void shouldReturnShippingPriceForA() throws Exception {
		
	}
	
	@Test
	public void shouldReturn200_whenQuantityIsAvailable() {
		doNothing().when(mockInventoryService).checkProductAvailablity(any());
		ResponseEntity<String> response = catlogController.getQuantity("The sparrow");
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void shouldReturn404_whenProductIdIsInvalid() {
		expectedException.equals(ProductNotFoundException.class);
		expectedException.expectMessage("Product Not Found");
		doThrow(new ProductNotFoundException()).when(mockInventoryService).checkProductAvailablity(any());
		catlogController.getQuantity("The sparrow");
	}
	
	@Test
	public void shouldReturn404_whenQuantityIsZero() {
		expectedException.equals(ResponseStatusException.class);
		expectedException.expectMessage("No quantity available of this item");
		doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No quantity available of this item")).when(mockInventoryService).checkProductAvailablity(any());
		catlogController.getQuantity("The sparrow");
	}
}