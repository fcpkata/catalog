package com.spring.controller.acceptance;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.catalog.CatalogApplication;
import com.spring.catalog.model.Item;
import com.spring.catalog.model.ProductInformation;
import com.spring.catalog.model.ProductInformations;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@AutoConfigureMockMvc
public class GetProductDetailsAcceptanceTest {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private MockRestServiceServer inventoryMockRestServiceServer;
	
	@Autowired
	private RestTemplate inventoryRestTemplate;
	
	@Before
	public void before() throws JsonProcessingException {
		inventoryMockRestServiceServer = MockRestServiceServer.createServer(inventoryRestTemplate);
	}


	private void mockInventoryResponce(int numberOfSellers) throws JsonProcessingException {
		inventoryMockRestServiceServer.expect(org.springframework.test.web.client.ExpectedCount.manyTimes() , 
												MockRestRequestMatchers.requestTo(containsString("/v1/item/")))
		.andRespond(MockRestResponseCreators.withSuccess(
				mapper.writeValueAsString(new ProductInformations(prepareProductInformationsFor(numberOfSellers))), MediaType.APPLICATION_STREAM_JSON));
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
	public void shouldReturnProductsDetailsWhenCalledWithProductIdWithSingleSeller() throws Exception {
		
		mockInventoryResponce(1);
		
		String productId = "PD001";
		mockMvc.perform(get("/catalog/v1/products/" + productId))
			.andDo(print())
			.andExpect(status()
				.isOk())
			.andExpect(jsonPath("$.id").value(productId))
			.andExpect(jsonPath("$.name").value("Sold on a Monday"))
			.andExpect(jsonPath("$.category.id").value("books"))
			.andExpect(jsonPath("$.description").exists())
			.andExpect(jsonPath("$.price").value(100))
			.andExpect(jsonPath("$.shippingPrice").value(0))
			.andExpect(jsonPath("$.rating").value(4))
			.andExpect(jsonPath("$.imageLinks[0]").exists())
			.andExpect(jsonPath("$.imageLinks[1]").exists())
			.andExpect(jsonPath("$.metadata.author").value("Kristina McMorris"))
		;

	}
	
	@Test
	public void shouldReturnProductsDetailsWhenCalledWithProductIdWithTwoSeller() throws Exception {
		
		mockInventoryResponce(2);
		
		String productId = "PD001";
		mockMvc.perform(get("/catalog/v1/products/" + productId))
			.andDo(print())
			.andExpect(status()
				.isOk())
			.andExpect(jsonPath("$.id").value(productId))
			.andExpect(jsonPath("$.name").value("Sold on a Monday"))
			.andExpect(jsonPath("$.category.id").value("books"))
			.andExpect(jsonPath("$.description").exists())
			.andExpect(jsonPath("$.price").value(99))
			.andExpect(jsonPath("$.shippingPrice").value(0))
			.andExpect(jsonPath("$.rating").value(4))
			.andExpect(jsonPath("$.imageLinks[0]").exists())
			.andExpect(jsonPath("$.imageLinks[1]").exists())
			.andExpect(jsonPath("$.metadata.author").value("Kristina McMorris"))
		;

	}
	
	@Test
	public void shouldReturnProductsDetailsWhenCalledWithProductIdWithNoSeller() throws Exception {
		
		mockInventoryResponce(0);
		
		String productId = "PD001";
		mockMvc.perform(get("/catalog/v1/products/" + productId))
			.andDo(print())
			.andExpect(status()
				.isOk())
			.andExpect(jsonPath("$.id").value(productId))
			.andExpect(jsonPath("$.name").value("Sold on a Monday"))
			.andExpect(jsonPath("$.category.id").value("books"))
			.andExpect(jsonPath("$.description").exists())
			.andExpect(jsonPath("$.shippingPrice").value(0))
			.andExpect(jsonPath("$.rating").value(4))
			.andExpect(jsonPath("$.imageLinks[0]").exists())
			.andExpect(jsonPath("$.imageLinks[1]").exists())
			.andExpect(jsonPath("$.metadata.author").value("Kristina McMorris"))
		;

	}
	
	@Test
	public void shouldReturnsDetailsPresentAsFalseWhenCalledWithProductId() throws Exception {
		mockMvc.perform(get("/catalog/v1/products/123"))
		.andDo(print())
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Product Not Found"))
		;
	}
}
