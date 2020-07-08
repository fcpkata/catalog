package com.spring.catalog.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.catalog.model.Product;
import com.spring.catalog.repository.Repository;

@Service
public class ProductService {

	private Repository repository;

	@Autowired
	public ProductService(Repository repository) {
		this.repository = repository;
	}

	public List<Product> fetchProductsFor(String category) {
		
		return repository.fetchAllProducts().stream()
				  .filter(validateBookCategory(category))
				  .collect(Collectors.toList());
	}
	
	private Predicate<Product> validateBookCategory(String category) {
			
			return product -> Optional.ofNullable(category)
									  .map(value -> product.getCategory().toString().equals(value))
									  .orElse(true);
		}

}
