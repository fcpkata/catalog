package com.spring.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.catalog.exception.ProductNotFoundException;
import com.spring.catalog.model.FilterCriteria;
import com.spring.catalog.model.Product;
import com.spring.catalog.repository.ProductRepository;

@Service
public class CatalogService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getProducts(FilterCriteria criteria){
		List<Product> allProducts = productRepository.getProducts();
		return filterProducts(allProducts, criteria);
	}

	public Product getProduct(String productId) {
		List<Product> allProducts = productRepository.getProducts();
		
		return allProducts.stream()
			.filter(product -> product.getId().equals(productId))
			.findFirst()
			.orElseThrow(ProductNotFoundException::new);
	}

	private List<Product> filterProducts(List<Product> allProducts, FilterCriteria criteria) {
		List<Product> filteredProducts = allProducts;
		if(criteria.getCategoryId() != null) {
			filteredProducts = allProducts.stream()
					.filter(product -> product.getCategory().getId().equals(criteria.getCategoryId()))
					.collect(Collectors.toList());
		}
		
		if(!criteria.getMetaDataFilters().isEmpty()) {
			filteredProducts = filteredProducts.stream()
				.filter(product -> product.hasMetadata(criteria.getMetaDataFilters()))
				.collect(Collectors.toList());
		}
		
		return filteredProducts;
	}

}
