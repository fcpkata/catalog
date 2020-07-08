package com.spring.catalog.repository;

import com.spring.catalog.model.Category;

public enum CategoryRepository {
	
	BOOKS("books", "Books"),
	ELECTRONICS("electronics", "Electronics"),
	PHONE("phones", "Cell Phones");
	
	private Category category;
	
	private CategoryRepository(String id, String name) {
		this.category = new Category(id, name);
	}
	
	public Category getCategory() {
		return this.category;
	}
	
}
