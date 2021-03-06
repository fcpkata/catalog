package com.spring.catalog.service;

import static com.spring.catalog.repository.CategoryRepository.BOOKS;
import static com.spring.catalog.repository.CategoryRepository.ELECTRONICS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.spring.catalog.model.FilterCriteria;
import com.spring.catalog.model.Product;
import com.spring.catalog.repository.ProductRepository;
import com.spring.catalog.service.CatalogService;
import com.spring.catalog.service.ProductInventoryService;

@RunWith(MockitoJUnitRunner.class)
public class CatalogServiceTest {
	@Mock
	private ProductRepository productRepository;

	@Mock
	private InventoryService mockInventoryService;

	@InjectMocks
	private CatalogService catalogService;

	@Test
	public void shouldReturnListOfProducts() {
		List<Product> expectedProductList = Arrays.asList(Product.builder().build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		FilterCriteria criteria = FilterCriteria.builder().build();

		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products).isEqualTo(expectedProductList);
	}

	@Test
	public void shouldReturnListOfProductsFilteredWithCategory() {
		List<Product> expectedProductList = Arrays.asList(Product.builder().category(BOOKS.getCategory()).build(),
				Product.builder().category(ELECTRONICS.getCategory()).build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		FilterCriteria criteria = FilterCriteria.builder().categoryId("books").build();

		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products.size()).isEqualTo(1);
		assertThat(products.get(0).getCategory()).isEqualTo(BOOKS.getCategory());
	}

	@Test
	public void shouldReturnListOfProductsFilteredByMetadataGenre() {
		String fictionGenre = "Fiction";
		List<Product> expectedProductList = Arrays.asList(
				Product.builder().metadata(Collections.singletonMap("genre", fictionGenre)).build(),
				Product.builder().metadata(Collections.singletonMap("color", "Black")).build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		FilterCriteria criteria = FilterCriteria.builder()
				.metaDataFilters(Collections.singletonMap("genre", fictionGenre)).build();

		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products.size()).isEqualTo(1);
		assertThat(products.get(0).getMetadata().get("genre")).isEqualTo(fictionGenre);
	}

	@Test
	public void shouldReturnListOfProductsFilteredByCategoryAndMetadataGenre() {
		String fictionGenre = "Fiction";
		List<Product> expectedProductList = Arrays.asList(
				Product.builder().category(BOOKS.getCategory())
						.metadata(Collections.singletonMap("genre", fictionGenre)).build(),
				Product.builder().category(BOOKS.getCategory()).metadata(Collections.singletonMap("genre", "History"))
						.build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		FilterCriteria criteria = FilterCriteria.builder()
				.metaDataFilters(Collections.singletonMap("genre", fictionGenre)).build();

		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products.size()).isEqualTo(1);
		assertThat(products.get(0).getMetadata().get("genre")).isEqualTo(fictionGenre);
	}

	@Test
	public void shouldReturnListOfProductsFilteredByNameExactMatch() {
		List<Product> expectedProductList = Arrays.asList(
				Product.builder().name("Clean Code").category(BOOKS.getCategory()).build(),
				Product.builder().name("The Pragmatic Programmer").category(BOOKS.getCategory()).build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		FilterCriteria criteria = FilterCriteria.builder().name("Clean Code").build();

		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products.size()).isEqualTo(1);
		assertThat(products.get(0).getName()).isEqualTo("Clean Code");
	}

	@Test
	public void shouldReturnListOfProductsFilteredByNameLowerCaseMatch() {
		List<Product> expectedProductList = Arrays.asList(
				Product.builder().name("Clean Code").category(BOOKS.getCategory()).build(),
				Product.builder().name("The Pragmatic Programmer").category(BOOKS.getCategory()).build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		FilterCriteria criteria = FilterCriteria.builder().name("clean code").build();

		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products.size()).isEqualTo(1);
		assertThat(products.get(0).getName()).isEqualTo("Clean Code");
	}

	@Test
	public void shouldReturnListOfProductsFilteredByNamePartialMatch() {
		List<Product> expectedProductList = Arrays.asList(
				Product.builder().name("Clean Code").category(BOOKS.getCategory()).build(),
				Product.builder().name("The Pragmatic Programmer").category(BOOKS.getCategory()).build(),
				Product.builder().name("Clean Coder").category(BOOKS.getCategory()).build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		FilterCriteria criteria = FilterCriteria.builder().name("code").build();

		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products.size()).isEqualTo(2);
		assertThat(products.get(0).getName()).isEqualTo("Clean Code");
		assertThat(products.get(1).getName()).isEqualTo("Clean Coder");
	}

	@Test
	public void shouldReturnEmptyListOfProductsWhenNoNameMatch() {
		List<Product> expectedProductList = Arrays.asList(
				Product.builder().name("Clean Code").category(BOOKS.getCategory()).build(),
				Product.builder().name("The Pragmatic Programmer").category(BOOKS.getCategory()).build(),
				Product.builder().name("Clean Coder").category(BOOKS.getCategory()).build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		FilterCriteria criteria = FilterCriteria.builder().name("Lean Startup").build();

		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products.size()).isEqualTo(0);
	}

	@Test
	public void shouldReturnListOfProductsFilteredByNamePartialMatchAndAuthor() {
		List<Product> expectedProductList = Arrays.asList(
				Product.builder().name("Clean Code").category(BOOKS.getCategory())
						.metadata(Collections.singletonMap("author", "Uncle Bob")).build(),
				Product.builder().name("The Pragmatic Programmer")
						.metadata(Collections.singletonMap("author", "Author 2")).category(BOOKS.getCategory()).build(),
				Product.builder().name("Clean Coder").metadata(Collections.singletonMap("author", "Martin Fowler"))
						.category(BOOKS.getCategory()).build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		FilterCriteria criteria = FilterCriteria.builder().name("code")
				.metaDataFilters(Collections.singletonMap("author", "Uncle Bob")).build();

		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products.size()).isEqualTo(1);
		assertThat(products.get(0).getName()).isEqualTo("Clean Code");
		assertThat(products.get(0).getMetadata().get("author")).isEqualTo("Uncle Bob");
	}

	@Test
	public void shouldReturnProductInfoWithPrice_whenOneOrMoreSellersAreAvailable() {
		List<Product> expectedProductList = Arrays.asList(Product.builder().name("Clean Code").category(BOOKS.getCategory()).id("bookOne")
																		   .metadata(Collections.singletonMap("author", "Uncle Bob")).build());
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		when(mockInventoryService.fetchInventoryPriceFor("bookOne")).thenReturn(100.0);
		FilterCriteria criteria = FilterCriteria.builder().build();
		List<Product> products = catalogService.getProducts(criteria);

		assertThat(products.size()).isEqualTo(1);
		assertThat(products.get(0).getPrice()).isEqualTo(100);
	}
	
	@Test
	public void shouldReturnProductInfoWithoutPrice_whenNoSellersAreAvailable() throws Exception {
		List<Product> expectedProductList = Arrays.asList(Product.builder().name("Clean Code").category(BOOKS.getCategory()).id("bookOne")
				   .metadata(Collections.singletonMap("author", "Uncle Bob")).build());
		
		when(productRepository.getProducts()).thenReturn(expectedProductList);
		when(mockInventoryService.fetchInventoryPriceFor("bookOne")).thenReturn(null);
		
		FilterCriteria criteria = FilterCriteria.builder().build();
		
		List<Product> products = catalogService.getProducts(criteria);
		
		assertThat(products.size()).isEqualTo(1);
		assertThat(products.get(0).getPrice()).isEqualTo(null);
	}

}
