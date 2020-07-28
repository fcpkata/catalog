package com.spring.controller.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.catalog.CatalogApplication;
import com.spring.catalog.model.Item;
import com.spring.catalog.model.Product;
import com.spring.catalog.model.ProductInformation;
import com.spring.catalog.model.ProductInformations;
import com.spring.catalog.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@AutoConfigureMockMvc
public class GetProductsAcceptanceTest {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private MockRestServiceServer inventoryMockRestServiceServer;
	
	@Autowired
	private RestTemplate inventoryRestTemplate;
	
	@Before
	public void before() throws JsonProcessingException {
		inventoryMockRestServiceServer = MockRestServiceServer.createServer(inventoryRestTemplate);
		
		inventoryMockRestServiceServer.expect(org.springframework.test.web.client.ExpectedCount.manyTimes() , 
												MockRestRequestMatchers.requestTo(containsString("/v1/item/")))
		.andRespond(MockRestResponseCreators.withSuccess(
				mapper.writeValueAsString(new ProductInformations(prepareProductInformationsFor(1))), MediaType.APPLICATION_STREAM_JSON));
	}
	

	private List<ProductInformation> prepareProductInformationsFor(int numberOfItems) {
		
		List<ProductInformation> productInformations = new ArrayList<>();
		
		IntStream.range(0, numberOfItems).forEach(num -> {
			ProductInformation productInformation = new ProductInformation("Seller_Id_"+num, Item.builder()
																								.productId("Product_Id_"+num)
																								.price(100.0-num)
																								.quantity(num)
																								.shippingPrice(20)
																								.build() );
			productInformations.add(productInformation);
		});
		return productInformations;
	}

	@Test
	public void shouldReturnProductsCatalogWhenCalledWithBooksCategory() throws Exception {
		MvcResult result = mockMvc.perform(get("/catalog/v1/products?category=books"))
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
		MvcResult result = mockMvc.perform(get("/catalog/v1/products"))
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
		MvcResult result = mockMvc.perform(get("/catalog/v1/products?category=books&genre=" + genre))
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
		MvcResult result = mockMvc.perform(get("/catalog/v1/products?name=" + name))
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
		MvcResult result = mockMvc.perform(get("/catalog/v1/products?name=" + name))
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
		MvcResult result = mockMvc.perform(get("/catalog/v1/products?name=" + name))
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
		MvcResult result = mockMvc.perform(get("/catalog/v1/products?name=" + name))
			.andDo(print())
			.andExpect(status().isOk())
		.andReturn();
		
		List<Product> products = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
		assertThat(products).isEmpty();
	}
	
	
	
}
