package com.spring.controller.acceptance;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.spring.catalog.CatalogApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@AutoConfigureMockMvc
public class GetProductDetailsAcceptanceTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldReturnProductsDetailsWhenCalledWithProductId() throws Exception {
		String productId = "PD001";
		mockMvc.perform(get("/catalog/v1/products/" + productId))
			.andDo(print())
			.andExpect(status()
				.isOk())
			.andExpect(jsonPath("$.id").value(productId))
			.andExpect(jsonPath("$.name").value("Sold on a Monday"))
			.andExpect(jsonPath("$.category.id").value("books"))
			.andExpect(jsonPath("$.description").exists())
			.andExpect(jsonPath("$.price").value(1200))
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
