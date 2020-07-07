package com.spring.catalog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.spring.catalog.controller.ProductService;
import com.spring.catalog.model.Category;
import com.spring.catalog.model.Product;
import com.spring.catalog.repository.Repository;
import com.spring.catalog.util.ProductUtility;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceSpec {
	
	@Mock
	private Repository mockRepository;
	
	ProductService productService;
	
	@Before
	public void setup() {
		productService = new ProductService(mockRepository);
		when(mockRepository.fetchAllProducts()).thenReturn(ProductUtility.products);
	}

	@Test
	public void fetchesProductsWhenCategoryIsNull() {
		List<Product> products = productService.fetchProductsFor(null);
		assertThat(products.size()).isGreaterThan(0);
	}

	@Test
	public void fetchesFilteredProductsWhenValidCategoryIsPassed() throws Exception {
		List<Product> products = productService.fetchProductsFor(Category.Mystry);
		assertThat(products.get(0).getCategory()).isEqualTo(Category.Mystry);
	}
}
