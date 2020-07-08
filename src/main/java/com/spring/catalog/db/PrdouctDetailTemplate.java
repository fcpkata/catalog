package com.spring.catalog.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.spring.catalog.model.Price;
import com.spring.catalog.model.Product;

@Component
public class PrdouctDetailTemplate {

	List<Product> productDetailsList = new ArrayList<>();

	public PrdouctDetailTemplate() {
		this.productDetailsList = getListofProductDetails();
	}

	private List<Product> getListofProductDetails() {

		productDetailsList.add(Product.builder().id("Product01")
				.name("The Argumentative Indian by Amartya Sen")
				.price(Price.prepareINRPriceFor(500.02))
				.description("Written by the Nobel Prize winning economist Amartya Sen, "
						+ "this book is essentially a series of poignant essays narrating "
						+ "India’s history and how that history has influenced and shaped its cultural identity.")
				.build());

		productDetailsList.add(Product.builder().id("Product02")
				.name("The Wonder That Was India by A L Basham")
				.price(Price.prepareINRPriceFor(400.02))
				.description("This book is considered a useful source of history for aspirants to the Indian civil services. "
						+ "Basham’s popular work covers the period between ancient India and the arrival of the Muslims")
				.build());

		return productDetailsList;

	}

	public Product getProductDetailsFromDb(String productId) {

		Map<String, List<Product>> productDetails =  this.productDetailsList.stream()
				.collect(Collectors.groupingBy(Product :: getId, 
						Collectors.toList()));

		return Optional.ofNullable(productDetails.get(productId)).map( product -> {
			Product value = product.get(0);
			value.setDetailsPresent(true);
			return value;
		}).orElse(new Product());
	}

}
