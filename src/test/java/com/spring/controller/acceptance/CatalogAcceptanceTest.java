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

import com.spring.catalog.CatalogApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@AutoConfigureMockMvc
public class CatalogAcceptanceTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnProductsCatalogWhenCalledWithNoInput() throws Exception {
		mockMvc.perform(get("/v1/products")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value("2"))
		.andExpect(jsonPath("$.[0].id").value("Book1"))
		.andExpect(jsonPath("$.[0].name").value("Classmate"))
		.andExpect(jsonPath("$.[0].description").value("100-pages"))
		.andExpect(jsonPath("$.[0].price").value("50"))
		.andExpect(jsonPath("$.[1].id").value("Phone1"))
		.andExpect(jsonPath("$.[1].name").value("Samsung"))
		.andExpect(jsonPath("$.[1].description").value("Model-S10"))
		.andExpect(jsonPath("$.[1].price").value("1000"));
	}
	
	@Test
	public void shouldReturnProductsCatalogWhenCalledWithBooksCategory() throws Exception {
		mockMvc.perform(get("/v1/products?category=books")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value("1"))
		.andExpect(jsonPath("$.[0].id").value("Book1"))
		.andExpect(jsonPath("$.[0].name").value("Classmate"))
		.andExpect(jsonPath("$.[0].description").value("100-pages"))
		.andExpect(jsonPath("$.[0].price").value("50"));
	}
}
