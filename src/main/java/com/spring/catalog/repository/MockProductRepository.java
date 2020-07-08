package com.spring.catalog.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.catalog.model.Product;

@Component
public class MockProductRepository implements ProductRepository {
	private List<Product> products;
	

	public MockProductRepository() {
		initializeProducts();

	}

	@Override
	public List<Product> getProducts() {
		return this.products;
	}

	private void initializeProducts() {
		this.products = new ArrayList<Product>();
		products.add(Product.builder()
				.id("Book1")
				.name("Classmate")
				.description("100-pages")
				.price("50")
				.category(CategoryRepository.BOOKS.getCategory())
				.build());
		products.add(Product.builder()
				.id("Phone1")
				.name("Samsung")
				.description("Model-S10")
				.price("1000")
				.category(CategoryRepository.PHONE.getCategory())
				.build());

	}
}