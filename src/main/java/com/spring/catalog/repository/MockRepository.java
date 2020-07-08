package com.spring.catalog.repository;

import java.util.List;

import com.spring.catalog.model.Product;
import com.spring.catalog.util.ProductUtility;

public class MockRepository implements Repository {

	@Override
	public List<Product> fetchAllProducts() {
		return ProductUtility.products;
	}

}
