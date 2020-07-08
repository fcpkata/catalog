package com.spring.catalog.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.spring.catalog.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class PrdouctDetailTemplateTest {
	
	PrdouctDetailTemplate prdouctDetailTemplate = new PrdouctDetailTemplate();
	
	@Test
	public void shoulRetrunProductDetailsWhenGetProductDetailsIsPresent() {
		Product response = prdouctDetailTemplate.getProductDetailsFromDb("Product01");
		assertNotNull(response);
		assertThat(response.getId()).isEqualTo("Product01");
		assertThat(response.getName()).isEqualTo("The Argumentative Indian by Amartya Sen");
		assertThat(response.getPrice().getValue()).isEqualTo(500.02);
		assertThat(response.getDescription()).isEqualTo("Written by the Nobel Prize winning economist Amartya Sen, this book is essentially a series of poignant essays narrating India’s history and how that history has influenced and shaped its cultural identity.");
		assertThat(response.isDetailsPresent()).isEqualTo(true);
	}
	
	@Test
	public void shoulRetrunDetailsPresentAsFalseWhenGetProductDetailsIsNotPresent() {
		Product response = prdouctDetailTemplate.getProductDetailsFromDb("product04");
		assertThat(response.isDetailsPresent()).isEqualTo(false);
	}

}
