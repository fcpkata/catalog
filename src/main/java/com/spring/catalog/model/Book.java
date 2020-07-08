package com.spring.catalog.model;

public class Book implements ProductType {
	
	private Category bookCategory;

	public Book(Category bookCategory) {
		this.bookCategory = bookCategory;
	}

	@Override
	public Category getCategory() {
		return bookCategory;
	}
}
