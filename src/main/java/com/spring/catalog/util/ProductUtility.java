package com.spring.catalog.util;

import java.util.ArrayList;
import java.util.List;

import com.spring.catalog.model.BookCategory;
import com.spring.catalog.model.ElectronicsCategory;
import com.spring.catalog.model.Price;
import com.spring.catalog.model.Product;

public class ProductUtility {
	
	public static List<Product> products;
	
	static {
		
		products = new ArrayList<>();
		
		products.add(Product.builder().id("PD001").name("Sold on a Monday").category(BookCategory.Historical_Friction)
	 			.description("An unforgettable historical fiction novel by Kristina McMorris, inspired by a stunning piece of history from Depression-Era America.")
	 			.price(Price.prepareINRPriceFor(1200.99))
	 			.build());
		
		products.add(Product.builder().id("PD002").name("Flight of the Sparrow").category(BookCategory.Historical_Friction)
	 			.description("A compelling, emotionally gripping/'s novel of historical fiction/'sperfect for readers of America/'s First Daughter")
	 			.price(Price.prepareINRPriceFor(1000.80))
	 			.build());
		
		products.add(Product.builder().id("PD003").name("The Hideaway").category(BookCategory.Mystry)
	 			.description("In the South, family is always more complicated than it seems.")
	 			.price(Price.prepareINRPriceFor(900.02))
	 			.build());
		
		products.add(Product.builder().id("PD004")
				.name("The Argumentative Indian by Amartya Sen")
				.category(BookCategory.Historical_Friction)
				.price(Price.prepareINRPriceFor(500.02))
				.description("Written by the Nobel Prize winning economist Amartya Sen, "
						+ "this book is essentially a series of poignant essays narrating "
						+ "India/'s history and how that history has influenced and shaped its cultural identity.")
				.build());

		products.add(Product.builder().id("PD005")
				.name("The Wonder That Was India by A L Basham")
				.category(BookCategory.Historical_Friction)
				.price(Price.prepareINRPriceFor(400.02))
				.description("This book is considered a useful source of history for aspirants to the Indian civil services. "
						+ "Basham/'s popular work covers the period between ancient India and the arrival of the Muslims")
				.build());

		products.add(Product.builder().id("PD006").name("Acer Swift 3").category(ElectronicsCategory.Laptop)
	 			.description("Acer Swift 3 Thin & Light Laptop, 14 inch Full HD IPS")
	 			.price(Price.prepareINRPriceFor(67900.99))
	 			.build());
		
		products.add(Product.builder().id("PD007").name("SAMSUNG 65-inch").category(ElectronicsCategory.TV)
	 			.description("SAMSUNG 65-inch Class QLED Q70T Series - 4K UHD Dual LED Quantum HDR Smart TV")
	 			.price(Price.prepareINRPriceFor(98600.27))
	 			.build());

		products.add(Product.builder().id("PD008").name("The stationery shop of Tehran").category(BookCategory.Historical_Friction)
				.description("Roya loves nothing better than to while away the hours in the local stationery shop run by Mr. Fakhri.")
				.price(Price.prepareINRPriceFor(1199.00))
				.build());
		
		products.add(Product.builder().id("PD009").name("Mahavat").category(BookCategory.Mystry)
				.description("This is the story of the time when Indian economy and civilization was taking a new turn.")
				.price(Price.prepareINRPriceFor(199.00))
				.build());
		
		products.add(Product.builder().id("PD010").name("Suheldev").category(BookCategory.Historical_Friction)
				.description("Repeated attacks by Mahmud of Ghazni and his barbaric Turkic hordes have weakened India’s northern regions.")
				.price(Price.prepareINRPriceFor(299.00))
				.build());
	}
	
}
