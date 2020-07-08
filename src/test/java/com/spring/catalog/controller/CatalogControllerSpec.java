package com.spring.catalog.controller;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.spring.catalog.model.BookCategory;
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
		
		assertThat(response.getBody().size()).isGreaterThan(2);
	}
	
	
	@Test
	public void shouldReturnSpecificProducts_WhenInvokedWithCategory() throws Exception {
		
		mockProductsServiceToSendBackProducts(BookCategory.Mystry.toString());
		
		ResponseEntity<List<Product>> response = catlogController.getCatalog(BookCategory.Mystry.toString());
		
		assertThat(response.getBody().get(0).getCategory()).isEqualTo(BookCategory.Mystry);
	}

	private void mockProductsServiceToSendBackProducts(String category) {

		when(mockProductService.fetchProductsFor(category)).thenReturn(
				Optional.ofNullable(category)
						.map(value -> ProductUtility.products.stream()
															 .filter(product -> product.getCategory().toString().equals(value))
															 .collect(Collectors.toList()))
						.orElseGet(() -> ProductUtility.products)
			);
	}

}
