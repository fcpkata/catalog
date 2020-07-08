package com.spring.catalog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
	
	@Test
	public void shouldReturnOneSpecificProduct_whenFilteredWithNameTehran() {
		mockProductsServiceToSendBackFilteredProducts("Tehran");
		
		ResponseEntity<List<Product>> response = catlogController.getFilteredProducts("Tehran");
		
		assertThat(response.getBody().size()).isEqualTo(1);
		assertThat(response.getBody().get(0).getName()).isEqualTo("The stationery shop of Tehran");
	}
	
	@Test
	public void shouldReturnOneSpecificProduct_whenFilteredWithNameMaha() {
		mockProductsServiceToSendBackFilteredProducts("Maha");
		
		ResponseEntity<List<Product>> response = catlogController.getFilteredProducts("Maha");
		
		assertThat(response.getBody().size()).isEqualTo(1);
		assertThat(response.getBody().get(0).getName()).isEqualTo("Mahavat");
	}
	
	@Test
	public void shouldReturnOneSpecificProduct_whenFilteredWithNameCaseInSensitiveMaha() {
		mockProductsServiceToSendBackFilteredProducts("maha");
		
		ResponseEntity<List<Product>> response = catlogController.getFilteredProducts("maha");
		
		assertThat(response.getBody().size()).isEqualTo(1);
		assertThat(response.getBody().get(0).getName()).isEqualTo("Mahavat");
	}
	
	@Test
	public void shouldReturnOneSpecificProduct_whenFilteredWithNameAndCategoryMaha() {
		mockProductsServiceToSendBackFilteredByCategoryProducts("the", BookCategory.Historical_Friction.toString());
		
		ResponseEntity<List<Product>> response = catlogController.getFilterByCatagoryProducts("the", BookCategory.Historical_Friction.toString());
		
		assertThat(response.getBody().size()).isEqualTo(4);
	}
	
	@Test
	public void shouldReturnEmptyList_whenFilteredWithNameWater() {
		mockProductsServiceToSendBackFilteredProducts("water");
		
		ResponseEntity<List<Product>> response = catlogController.getFilteredProducts("water");
		
		assertThat(response.getBody().size()).isEqualTo(0);
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
	
	private void mockProductsServiceToSendBackFilteredProducts(String filterBookTitle) {
		when(mockProductService.fetchFilteredProductesFor(filterBookTitle)).thenReturn(
				Optional.ofNullable(filterBookTitle)
						.map(value -> ProductUtility.products.stream()
															 .filter(product -> product.getName().toLowerCase().contains(value.toLowerCase()))
															 .collect(Collectors.toList()))
						.orElseGet(() -> new ArrayList<>())
			);
	}
	
	private void mockProductsServiceToSendBackFilteredByCategoryProducts(String filterBookTitle, String category) {
		when(mockProductService.fetchFilteredProductesFor(filterBookTitle, category)).thenReturn(
				Optional.ofNullable(filterBookTitle)
						.map(value -> ProductUtility.products.stream()
															 .filter(product -> product.getName().toLowerCase().contains(value.toLowerCase())
																	 && product.getCategory().toString().equals(category))
															 .collect(Collectors.toList()))
						.orElseGet(() -> new ArrayList<>())
			);
	}

}
