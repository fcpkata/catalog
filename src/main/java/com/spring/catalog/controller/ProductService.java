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
	
	public List<Product> fetchFilteredProductesFor(String filteredTitle) {
		
		return repository.fetchAllProducts().stream()
				.filter(validateFilter(filteredTitle))
				.collect(Collectors.toList());
				
	}
	
	public List<Product> fetchFilteredProductesFor(String filterTitle, String category) {
		return repository.fetchAllProducts().stream()
				.filter(validateFilterByCategory(filterTitle, category))
				.collect(Collectors.toList());
	}
	
	private Predicate<Product> validateBookCategory(String category) {
			
			return product -> Optional.ofNullable(category)
									  .map(value -> product.getCategory().toString().equals(value))
									  .orElse(true);
		}
	
	private Predicate<Product> validateFilter(String title) {

		return product -> Optional.ofNullable(title)
								  .map(value -> (product.getName().toLowerCase()).contains(value.toLowerCase()))
								  .orElse(true);
	}

	private Predicate<? super Product> validateFilterByCategory(String filterTitle, String category) {
		return product -> Optional.ofNullable(filterTitle)
				  .map(value -> (product.getName().toLowerCase()).contains(value.toLowerCase())
						  && product.getCategory().toString().equals(category))
				  .orElse(true);
	}
}
