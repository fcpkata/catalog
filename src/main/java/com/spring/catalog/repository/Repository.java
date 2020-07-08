package com.spring.catalog.repository;

import java.util.List;

import com.spring.catalog.model.Product;

public interface Repository {

	List<Product> fetchAllProducts();

}
