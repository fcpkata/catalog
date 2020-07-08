package com.spring.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.spring.catalog.model.FilterCriteria;
import com.spring.catalog.model.Product;
import com.spring.catalog.repository.ProductRepository;

@Service
public class CatalogService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getProducts(FilterCriteria criteria){
		List<Product> allProducts = productRepository.getProducts();
		if(!StringUtils.isEmpty(criteria.getCategoryId())) {
			return filterProducts(criteria.getCategoryId(), allProducts);
		}
		return allProducts;
	}

	private List<Product> filterProducts(String categoryId, List<Product> allProducts) {
		return allProducts.stream()
		.filter(product -> product.getCategory().getId().equals(categoryId))
		.collect(Collectors.toList());
	}

}
