package com.spring.catalog.util;

import java.util.ArrayList;
import java.util.List;

import com.spring.catalog.model.Category;
import com.spring.catalog.model.Price;
import com.spring.catalog.model.Product;

public class ProductUtility {
	
	public static List<Product> products;
	
	static {
		products = new ArrayList<>();
		
		products.add(Product.builder().id("PD001").name("Sold on a Monday").category(Category.Historical_Friction)
	 			.description("An unforgettable historical fiction novel by Kristina McMorris, inspired by a stunning piece of history from Depression-Era America.")
	 			.price(Price.prepareINRPriceFor(1200.99))
	 			.build());
		
		products.add(Product.builder().id("PD002").name("Flight of the Sparrow").category(Category.Historical_Friction)
	 			.description("A compelling, emotionally gripping�* novel of historical fiction�perfect for readers of America�s First Daughter")
	 			.price(Price.prepareINRPriceFor(1000.80))
	 			.build());
		
		products.add(Product.builder().id("PD003").name("The Hideaway").category(Category.Mystry)
	 			.description("In the South, family is always more complicated than it seems.")
	 			.price(Price.prepareINRPriceFor(900.02))
	 			.build());
		
		products.add(Product.builder().id("PD004")
				.name("The Argumentative Indian by Amartya Sen")
				.category(Category.Historical_Friction)
				.price(Price.prepareINRPriceFor(500.02))
				.description("Written by the Nobel Prize winning economist Amartya Sen, "
						+ "this book is essentially a series of poignant essays narrating "
						+ "India�s history and how that history has influenced and shaped its cultural identity.")
				.build());

		products.add(Product.builder().id("PD005")
				.name("The Wonder That Was India by A L Basham")
				.category(Category.Historical_Friction)
				.price(Price.prepareINRPriceFor(400.02))
				.description("This book is considered a useful source of history for aspirants to the Indian civil services. "
						+ "Basham�s popular work covers the period between ancient India and the arrival of the Muslims")
				.build());
	}
	
}
