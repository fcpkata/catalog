package com.spring.catalog.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				.id("PD001")
				.name("Sold on a Monday")
				.description("An unforgettable historical fiction novel by Kristina McMorris, inspired by a stunning piece of history from Depression-Era America.")
				.price(1200)
				.shippingPrice(0)
				.rating(4)
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						constructBook("Kristina McMorris", "Historical Fiction"))
				.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD001-small.png",
						"https://imagecdn.com/PD001-large.png"
						))
	 			.build());
		products.add(Product.builder()
				.id("PD002")
				.name("Flight of the Sparrow")
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
							constructBook("Amy Belding Brown", "Historical Fiction"))
	 			.description("A compelling, emotionally gripping novel of historical fiction. Perfect for readers of Americas First Daughter")
	 			.price(1000)
	 			.shippingPrice(0)
	 			.rating(4)
	 			.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD002-small.png",
						"https://imagecdn.com/PD002-large.png"
						))
	 			.build());
		products.add(Product.builder()
				.id("PD003")
				.name("The Hideaway")
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
							constructBook("Lauren K. Denton", "Mystery"))
	 			.description("In the South, family is always more complicated than it seems.")
	 			.price(900)
	 			.shippingPrice(0)
	 			.rating(4)
	 			.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD003-small.png",
						"https://imagecdn.com/PD003-large.png"
						))
	 			.build());
		products.add(Product.builder()
				.id("PD004")
				.name("The Argumentative Indian by Amartya Sen")
				.price(500)
				.shippingPrice(0)
				.rating(4)
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						constructBook("Amartya Sen", "Historical Fiction"))
				.description("Written by the Nobel Prize winning economist Amartya Sen, "
						+ "this book is essentially a series of poignant essays narrating "
						+ "Indias history and how that history has influenced and shaped its cultural identity.")
				.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD004-small.png",
						"https://imagecdn.com/PD004-large.png"
						))
				.build());
		products.add(Product.builder()
				.id("PD005")
				.name("The Wonder That Was India by A L Basham")
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						constructBook("A L Basham", "Historical Fiction"))
				.price(400)
				.shippingPrice(0)
				.rating(4)
				.description("This book is considered a useful source of history for aspirants to the Indian civil services. "
						+ "Bashams popular work covers the period between ancient India and the arrival of the Muslims")
				.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD005-small.png",
						"https://imagecdn.com/PD005-large.png"
						))
				.build());
		products.add(Product.builder()
				.id("PD006").name("Acer Swift 3")
				.category(CategoryRepository.ELECTRONICS.getCategory())
				.metadata(
						Collections.singletonMap("type", "Laptop"))
	 			.description("Acer Swift 3 Thin & Light Laptop, 14 inch Full HD IPS")
	 			.price(67900)
	 			.shippingPrice(1000)
	 			.rating(3)
	 			.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD006-small.png",
						"https://imagecdn.com/PD006-large.png"
						))
	 			.build());
		products.add(Product.builder()
				.id("PD007")
				.name("SAMSUNG 65-inch")
				.category(CategoryRepository.ELECTRONICS.getCategory())
				.metadata(
						Collections.singletonMap("type", "TV"))
	 			.description("SAMSUNG 65-inch Class QLED Q70T Series - 4K UHD Dual LED Quantum HDR Smart TV")
	 			.price(98600)
	 			.shippingPrice(10000)
	 			.rating(5)
	 			.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD007-small.png",
						"https://imagecdn.com/PD007-large.png"
						))
	 			.build());
		products.add(Product.builder()
				.id("PD008")
				.name("Classmate")
				.description("100-pages")
				.price(50)
				.shippingPrice(0)
				.rating(4)
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						new HashMap<String, String>() {{
							put("type", "Stationery");
							put("pages", "100");
						}})
				.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD008-small.png",
						"https://imagecdn.com/PD008-large.png"
						))
				.build());
		products.add(Product.builder()
				.id("PD009")
				.name("Samsung")
				.description("Model-S10")
				.price(96000)
				.shippingPrice(4999)
				.rating(3)
				.category(CategoryRepository.PHONE.getCategory())
				.metadata(
						new HashMap<String, String>() {{
							put("type", "Smart Phone");
							put("color", "Black");
						}})
				.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD009-small.png",
						"https://imagecdn.com/PD009-large.png"
						))
				.build());
		
		products.add(Product.builder()
				.id("PD010")
				.name("Algorithms")
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						constructBook("Cormen", "Technical"))
				.price(400)
				.shippingPrice(0)
				.rating(4)
				.description("This book is about algorithms.")
				.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD005-small.png",
						"https://imagecdn.com/PD005-large.png"
						))
				.build());
		
		products.add(Product.builder()
				.id("PD011")
				.name("The Pragmatic Programmer")
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						constructBook("Kent Beck", "Technical"))
				.price(400)
				.shippingPrice(0)
				.rating(4)
				.description("This book is about how to be practical while programming.")
				.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD005-small.png",
						"https://imagecdn.com/PD005-large.png"
						))
				.build());
		
		products.add(Product.builder()
				.id("PD012")
				.name("CleanCode")
				.category(CategoryRepository.BOOKS.getCategory())
				.metadata(
						constructBook("Robert C Martin", "Technical"))
				.price(400)
				.shippingPrice(0)
				.rating(4)
				.description("This book is about how to write clean code.")
				.imageLinks(Arrays.asList(
						"https://imagecdn.com/PD005-small.png",
						"https://imagecdn.com/PD005-large.png"
						))
				.build());

	}
	
	private Map<String, String> constructBook(String author, String genre) {
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("author", author);
		hashMap.put("genre", genre);
		return hashMap;
	}
 }