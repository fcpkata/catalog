package com.spring.catalog.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.spring.catalog.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class CatalogControllerTest {

	public CatalogController catlogController = new CatalogController();

	@Test
	public void shouldReturnCatalogWhenGetCatalogIsCalled() throws Exception {
		Product product = new Product("Book", "Classmate", "100-pages", "50");
		ResponseEntity<List<Product>> response = catlogController.getCatalog();
		assertThat(response.getBody().get(0).equals(product));
	}

}
