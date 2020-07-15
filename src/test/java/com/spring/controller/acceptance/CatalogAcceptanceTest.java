package com.spring.controller.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.catalog.CatalogApplication;
import com.spring.catalog.model.Product;
import com.spring.catalog.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@AutoConfigureMockMvc
public class CatalogAcceptanceTest {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void shouldReturnProductsCatalogWhenCalledWithBooksCategory() throws Exception {
		MvcResult result = mockMvc.perform(get("/v1/products?category=books"))
				.andDo(print())
				.andExpect(status().isOk())
		.andReturn();
		
		List<Product> products = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
		for(Product product: products) {
			assertThat(product.getCategory().getId()).isEqualTo(CategoryRepository.BOOKS.getCategory().getId());
			Assertions.assertThat(product.getCategory().getName()).isEqualTo(CategoryRepository.BOOKS.getCategory().getName());
		}
	}
	
	@Test
	public void shouldReturnAllProductsCatalogsWhenCalledWithNoInput() throws Exception {
		MvcResult result = mockMvc.perform(get("/v1/products"))
			.andDo(print())
			.andExpect(status().isOk())
		.andReturn();
		
		List<Product> products = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
		
		assertThat(products).isNotEmpty();
		assertThat(products.size()).isEqualTo(9);
	}
	
	@Test
	public void shouldReturnOnlyHistoricalFrictionProducts() throws Exception {
		String genre = "Historical Fiction";
		MvcResult result = mockMvc.perform(get("/v1/products?category=books&genre=" + genre))
			.andDo(print())
			.andExpect(status().isOk())
		.andReturn();
		
		List<Product> products = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
		assertThat(products).isNotEmpty();
		for(Product product: products) {
			assertThat(product.getMetadata().getMetadata().get("genre")).isEqualTo(genre);
		}
	}
	
	@Test
	public void shouldReturnProductsDetailsWhenCalledWithProductId() throws Exception {
		mockMvc.perform(get("/v1/product/PD001")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value("PD001"))
		.andExpect(jsonPath("$.name").value("Sold on a Monday"))
		.andExpect(jsonPath("$.category.id").value("books"))
		.andExpect(jsonPath("$.description").value("An unforgettable historical fiction novel by Kristina McMorris, inspired by a stunning piece of history from Depression-Era America."))
		.andExpect(jsonPath("$.price.value").value(1200.99))
		.andExpect(jsonPath("$.price.currency").value("INR"));

	}
	
	@Test
	public void shouldReturnsDetailsPresentAsFalseWhenCalledWithProductId() throws Exception {
		mockMvc.perform(get("/v1/product/Product03")).andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldReturns404_WhenCalledWithProductIdIsNull() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get("/v1/product").queryParam("productId", "");
		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isNotFound());
	}
}
