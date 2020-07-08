package com.spring.catalog.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.catalog.model.FilterCriteria;
import com.spring.catalog.model.Product;
import com.spring.catalog.repository.CategoryRepository;
import com.spring.catalog.service.CatalogService;

@RunWith(MockitoJUnitRunner.class)
public class CatalogControllerTest {

	@InjectMocks
	private CatalogController catlogController;
	@Mock
	private CatalogService catalogService;
	

	@Test
	public void shouldReturnCatalogWhenGetCatalogIsCalled() throws Exception {
		
		Product product = new Product("Book", "Classmate", "100-pages", "50", CategoryRepository.BOOKS.getCategory());
		Mockito.when(catalogService.getProducts(Mockito.any(FilterCriteria.class))).thenReturn(Arrays.asList(product));
		
		ResponseEntity<List<Product>> response = catlogController.getCatalog(null);
		
		assertThat(response.getBody().get(0)).isEqualTo(product);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	

}
