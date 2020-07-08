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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.spring.catalog.CatalogApplication;
import com.spring.catalog.model.BookCategory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@AutoConfigureMockMvc
public class CatalogAcceptanceTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnAllProductsCatalogsWhenCalledWithNoInput() throws Exception {
		mockMvc.perform(get("/v1/products")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].id").value("PD001"))
		.andExpect(jsonPath("$.[0].name").value("Sold on a Monday"))
		.andExpect(jsonPath("$.[0].category").value(BookCategory.Historical_Friction.toString()))
		.andExpect(jsonPath("$.[0].description").value("An unforgettable historical fiction novel by Kristina McMorris, inspired by a stunning piece of history from Depression-Era America."))
		.andExpect(jsonPath("$.[0].price.value").value(1200.99))
		.andExpect(jsonPath("$.[0].price.currency").value("INR"));
	}
	
	@Test
	public void shouldReturnOnlyHistoricalFrictionProducts() throws Exception {
		mockMvc.perform(get("/v1/products?category="+BookCategory.Historical_Friction.toString())).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").isNotEmpty())
		.andExpect(jsonPath("$.[0].category").value(BookCategory.Historical_Friction.toString()))
		.andExpect(jsonPath("$.[1].category").value(BookCategory.Historical_Friction.toString()));
	}
	
	@Test
	public void shouldReturnProductsDetailsWhenCalledWithProductId() throws Exception {
		mockMvc.perform(get("/v1/product/PD001")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value("PD001"))
		.andExpect(jsonPath("$.name").value("Sold on a Monday"))
		.andExpect(jsonPath("$.category").value(BookCategory.Historical_Friction.toString()))
		.andExpect(jsonPath("$.description").value("An unforgettable historical fiction novel by Kristina McMorris, inspired by a stunning piece of history from Depression-Era America."))
		.andExpect(jsonPath("$.price.value").value(1200.99))
		.andExpect(jsonPath("$.price.currency").value("INR"))
		.andExpect(jsonPath("$.detailsPresent").value(true));

	}
	
	@Test
	public void shouldReturnsDetailsPresentAsFalseWhenCalledWithProductId() throws Exception {
		mockMvc.perform(get("/v1/product/Product03")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.detailsPresent").value(false));
	}
	
	@Test
	public void shouldReturns404_WhenCalledWithProductIdIsNull() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get("/v1/product").queryParam("productId", "");
		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isNotFound());

	}
	
	@Test
	public void shouldReturnOnlyProductsWithNameContaingTehran() throws Exception {
		mockMvc.perform(get("/v1/filter/Tehran")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(1))
		.andExpect(jsonPath("$.[0].name").value("The stationery shop of Tehran"));
	}
	
	@Test
	public void shouldReturnEmptyListWithNameContaingWater() throws Exception {
		mockMvc.perform(get("/v1/filter/water")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(0));
	}
}
