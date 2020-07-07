package com.spring.catalog.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.catalog.model.Product;
import com.spring.catalog.util.ProductUtility;

@Service
public class ProductService {

	public List<Product> fetchAllProducts() {
		return ProductUtility.products;
	}

}
