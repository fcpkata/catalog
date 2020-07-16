package com.spring.controller.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.catalog.CatalogApplication;
import com.spring.catalog.model.Product;
import com.spring.catalog.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@AutoConfigureMockMvc
public class GetProductsAcceptanceTest {

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
		assertThat(products.size()).isEqualTo(12);
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
			assertThat(product.getMetadata().get("genre")).isEqualTo(genre);
		}
	}
	
	@Test
	public void shouldReturnProductsMatchingTheName() throws Exception {
		String name = "Programmer";
		MvcResult result = mockMvc.perform(get("/v1/products?name=" + name))
			.andDo(print())
			.andExpect(status().isOk())
		.andReturn();
		
		List<Product> products = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
		assertThat(products).isNotEmpty();
		assertThat(products.get(0).getName()).isEqualTo("The Pragmatic Programmer");
	}
	
	@Test
	public void shouldReturnProductsMatchingThePartialName() throws Exception {
		String name = "Code";
		MvcResult result = mockMvc.perform(get("/v1/products?name=" + name))
			.andDo(print())
			.andExpect(status().isOk())
		.andReturn();
		
		List<Product> products = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
		assertThat(products).isNotEmpty();
		assertThat(products.get(0).getName()).isEqualTo("CleanCode");
	}
	
	@Test
	public void shouldReturnProductsMatchingTheCaseInsensitiveName() throws Exception {
		String name = "code";
		MvcResult result = mockMvc.perform(get("/v1/products?name=" + name))
			.andDo(print())
			.andExpect(status().isOk())
		.andReturn();
		
		List<Product> products = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
		assertThat(products).isNotEmpty();
		assertThat(products.get(0).getName()).isEqualTo("CleanCode");
	}
	
	@Test
	public void shouldReturnEmptyProductsMatchingTheInvalidName() throws Exception {
		String name = "water";
		MvcResult result = mockMvc.perform(get("/v1/products?name=" + name))
			.andDo(print())
			.andExpect(status().isOk())
		.andReturn();
		
		List<Product> products = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
		assertThat(products).isEmpty();
	}
	
	
	
}
