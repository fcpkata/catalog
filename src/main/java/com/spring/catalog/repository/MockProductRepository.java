package com.spring.catalog.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.catalog.model.Price;
import com.spring.catalog.model.Product;
import com.spring.catalog.model.ProductMetadata;

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
				.id("PD001")
				.name("Sold on a Monday")
				.description("An unforgettable historical fiction novel by Kristina McMorris, inspired by a stunning piece of history from Depression-Era America.")
				.price(Price.prepareINRPriceFor(1200.99))
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						new ProductMetadata()
							.add("genre", "Historical Fiction"))
	 			.build());
		products.add(Product.builder()
				.id("PD002")
				.name("Flight of the Sparrow")
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						new ProductMetadata()
							.add("genre", "Historical Fiction"))
	 			.description("A compelling, emotionally gripping novel of historical fiction. Perfect for readers of Americas First Daughter")
	 			.price(Price.prepareINRPriceFor(1000.80))
	 			.build());
		products.add(Product.builder()
				.id("PD003")
				.name("The Hideaway")
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						new ProductMetadata()
							.add("genre", "Mystery"))
	 			.description("In the South, family is always more complicated than it seems.")
	 			.price(Price.prepareINRPriceFor(900.02))
	 			.build());
		products.add(Product.builder()
				.id("PD004")
				.name("The Argumentative Indian by Amartya Sen")
				.price(Price.prepareINRPriceFor(500.02))
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						new ProductMetadata()
							.add("genre", "Historical Fiction"))
				.description("Written by the Nobel Prize winning economist Amartya Sen, "
						+ "this book is essentially a series of poignant essays narrating "
						+ "Indias history and how that history has influenced and shaped its cultural identity.")
				.build());
		products.add(Product.builder()
				.id("PD005")
				.name("The Wonder That Was India by A L Basham")
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						new ProductMetadata()
							.add("genre", "Historical Fiction"))
				.price(Price.prepareINRPriceFor(400.02))
				.description("This book is considered a useful source of history for aspirants to the Indian civil services. "
						+ "Bashams popular work covers the period between ancient India and the arrival of the Muslims")
				.build());
		products.add(Product.builder()
				.id("PD006").name("Acer Swift 3")
				.category(CategoryRepository.ELECTRONICS.getCategory())
				.metadata(
						new ProductMetadata()
							.add("type", "Laptop"))
	 			.description("Acer Swift 3 Thin & Light Laptop, 14 inch Full HD IPS")
	 			.price(Price.prepareINRPriceFor(67900.99))
	 			.build());
		products.add(Product.builder()
				.id("PD007")
				.name("SAMSUNG 65-inch")
				.category(CategoryRepository.ELECTRONICS.getCategory())
				.metadata(
						new ProductMetadata()
							.add("type", "TV"))
	 			.description("SAMSUNG 65-inch Class QLED Q70T Series - 4K UHD Dual LED Quantum HDR Smart TV")
	 			.price(Price.prepareINRPriceFor(98600.27))
	 			.build());
		products.add(Product.builder()
				.id("PD008")
				.name("Classmate")
				.description("100-pages")
				.price(Price.prepareINRPriceFor(50))
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						new ProductMetadata()
							.add("type", "Stationery")
							.add("pages", "100")
						)
				.build());
		products.add(Product.builder()
				.id("PD009")
				.name("Samsung")
				.description("Model-S10")
				.price(Price.prepareINRPriceFor(1000))
				.category(CategoryRepository.PHONE.getCategory())
				.metadata(
						new ProductMetadata()
							.add("type", "Smart Phone")
							.add("color", "Black")
						)
				.build());

	}
}