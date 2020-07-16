package com.spring.catalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.spring.catalog.exception.ProductNotFoundException;
import com.spring.catalog.model.FilterCriteria;
import com.spring.catalog.model.Product;
import com.spring.catalog.repository.ProductRepository;

@Service
public class CatalogService {
	private ProductRepository productRepository;
	
	@Autowired
	public CatalogService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
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
		
		filteredProducts = filterProductsByName(criteria, filteredProducts);
		filteredProducts = filterProductsByCategory(criteria, filteredProducts);
		filteredProducts = filterProductsByMetadata(criteria, filteredProducts);
		
		return filteredProducts;
	}

	private List<Product> filterProductsByMetadata(FilterCriteria criteria, List<Product> filteredProducts) {
		if(!CollectionUtils.isEmpty(criteria.getMetaDataFilters())) {
			filteredProducts = filteredProducts.stream()
				.filter(product -> product.hasMetadata(criteria.getMetaDataFilters()))
				.collect(Collectors.toList());
		}
		return filteredProducts;
	}

	private List<Product> filterProductsByCategory(FilterCriteria criteria, List<Product> filteredProducts) {
		if(criteria.getCategoryId() != null) {
			filteredProducts = filteredProducts.stream()
				.filter(product -> product.getCategory().getId().equals(criteria.getCategoryId()))
				.collect(Collectors.toList());
		}
		return filteredProducts;
	}

	private List<Product> filterProductsByName(FilterCriteria criteria, List<Product> filteredProducts) {
		if(!StringUtils.isEmpty(criteria.getName())) {
			filteredProducts = filteredProducts.stream()
					.filter(product -> {
						return product.getName().toLowerCase().contains(criteria.getName().toLowerCase());
					})
					.collect(Collectors.toList());
		}
		return filteredProducts;
	}

}
