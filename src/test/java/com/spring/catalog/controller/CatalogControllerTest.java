package com.spring.catalog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.spring.catalog.db.PrdouctDetailTemplate;
import com.spring.catalog.model.Category;
import com.spring.catalog.model.Price;
import com.spring.catalog.model.Product;
import com.spring.catalog.util.ProductUtility;

@RunWith(MockitoJUnitRunner.class)
public class CatalogControllerTest {
	
	@Mock
	private ProductService mockProductService;
	
	public CatalogController catlogController;
	
	@Mock
	private PrdouctDetailTemplate prdouctDetailTemplate;
	
	@Before
	public void setup() {		
		catlogController = new CatalogController(mockProductService, prdouctDetailTemplate);
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

		when(mockProductService.fetchAllProducts()).thenReturn(
				Optional.ofNullable(category)
						.map(value -> ProductUtility.products.stream()
															 .filter(product -> product.getCategory().equals(value))
															 .collect(Collectors.toList()))
						.orElseGet(() -> ProductUtility.products)
			);
	}
	
	public void shoulRetrunProductDetailsWhenGetProductDetailsIsPresent() {
		when(prdouctDetailTemplate.getProductDetailsFromDb(any())).thenReturn(getProductDetails());
		ResponseEntity<Product> response = catlogController.getProductDetailsById("Product01");
		validateResponse(response);
	}

	@Test
	public void shoulRetrunDetailsPresentAsFalseWhenGetProductDetailsIsNotPresent() {
		when(prdouctDetailTemplate.getProductDetailsFromDb(any())).thenReturn(new Product());
		ResponseEntity<Product> response = catlogController.getProductDetailsById("Product03");
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertNotNull(response.getBody());
		assertThat(response.getBody().isDetailsPresent()).isEqualTo(false);
	}
	
	private void validateResponse(ResponseEntity<Product> response) {
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertNotNull(response.getBody());
		assertThat(response.getBody().getId()).isEqualTo("Product01");
		assertThat(response.getBody().getName()).isEqualTo("The Argumentative Indian by Amartya Sen");
		assertThat(response.getBody().getPrice().getValue()).isEqualTo(500.02);
		assertThat(response.getBody().getDescription()).isEqualTo("Written by the Nobel Prize winning economist Amartya Sen, this book is essentially a series of poignant essays narrating India’s history and how that history has influenced and shaped its cultural identity.");
		assertThat(response.getBody().isDetailsPresent()).isEqualTo(true);
	}
	
	private Product getProductDetails() {
		return Product.builder().id("Product01")
				.name("The Argumentative Indian by Amartya Sen")
				.price(Price.prepareINRPriceFor(500.02))
				.description("Written by the Nobel Prize winning economist Amartya Sen, "
						+ "this book is essentially a series of poignant essays narrating "
						+ "India’s history and how that history has influenced and shaped its cultural identity.")
				.detailsPresent(true)
				.build();
	}
	
	
	
	
}