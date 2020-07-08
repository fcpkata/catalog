package com.spring.catalog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.spring.catalog.model.Category;
import com.spring.catalog.model.Product;
import com.spring.catalog.util.ProductUtility;

@RunWith(MockitoJUnitRunner.class)
public class CatalogControllerSpec {

	@Mock
	private ProductService mockProductService;
	
	public CatalogController catlogController;
	
	@Before
	public void setup() {		
		catlogController = new CatalogController(mockProductService);
	}
	
	@Test
	public void shouldReturnAllCatalog_WhenInvokedWithoutNullCategory() throws Exception {
		
		mockProductsServiceToSendBackProducts(null);
		
		ResponseEntity<List<Product>> response = catlogController.getCatalog(null);
		
		assertThat(response.getBody().size()).isGreaterThan(0);
	}
	
	
	@Test
	public void shouldReturnSpecificProducts_WhenInvokedWithCategory() throws Exception {
		
		mockProductsServiceToSendBackProducts(Category.Mystry);
		
		ResponseEntity<List<Product>> response = catlogController.getCatalog(Category.Mystry);
		
		assertThat(response.getBody().get(0).getCategory()).isEqualTo(Category.Mystry);
	}

	private void mockProductsServiceToSendBackProducts(Category category) {

		when(mockProductService.fetchProductsFor(category)).thenReturn(
				Optional.ofNullable(category)
						.map(value -> ProductUtility.products.stream()
															 .filter(product -> product.getCategory().equals(value))
															 .collect(Collectors.toList()))
						.orElseGet(() -> ProductUtility.products)
			);
	}
	
	@Test
	public void shoulRetrunProductDetailsWhenGetProductDetailsIsPresent() {
		
		getAvaliableProducts();
		ResponseEntity<Product> response = catlogController.getProductDetailsById("PD001");
		validateResponse(response);
	}


	@Test
	public void shoulRetrunDetailsPresentAsFalseWhenGetProductDetailsIsNotPresent() {
		getAvaliableProducts();
		ResponseEntity<Product> response = catlogController.getProductDetailsById("Product03");
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertNotNull(response.getBody());
		assertThat(response.getBody().isDetailsPresent()).isEqualTo(false);
	}
	
	private void getAvaliableProducts() {
		when(mockProductService.fetchProductsFor(null)).thenReturn(ProductUtility.products.stream()
															 .collect(Collectors.toList()));
	}

	
	private void validateResponse(ResponseEntity<Product> response) {
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertNotNull(response.getBody());
		assertThat(response.getBody().getId()).isEqualTo("PD001");
		assertThat(response.getBody().getName()).isEqualTo("Sold on a Monday");
		assertThat(response.getBody().getPrice().getValue()).isEqualTo(1200.99);
		assertThat(response.getBody().getDescription()).isEqualTo("An unforgettable historical fiction novel by Kristina McMorris, inspired by a stunning piece of history from Depression-Era America.");
		assertThat(response.getBody().isDetailsPresent()).isEqualTo(true);
	}
	
}