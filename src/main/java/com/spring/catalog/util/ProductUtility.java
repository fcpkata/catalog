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
	 			.description("A compelling, emotionally gripping”* novel of historical fiction—perfect for readers of America’s First Daughter")
	 			.price(Price.prepareINRPriceFor(1000.80))
	 			.build());
		
		products.add(Product.builder().id("PD003").name("The Hideaway").category(Category.Mystry)
	 			.description("In the South, family is always more complicated than it seems.")
	 			.price(Price.prepareINRPriceFor(900.02))
	 			.build());
	}
	
}
