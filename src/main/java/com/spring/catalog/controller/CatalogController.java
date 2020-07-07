package com.spring.catalog.controller;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.catalog.model.Category;
import com.spring.catalog.model.Product;


@RestController
@RequestMapping("/v1")
public class CatalogController {

	private ProductService productService;

	@Autowired
	public CatalogController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getCatalog(@RequestParam(name = "category", required = false) Category category) {

		List<Product> products = productService.fetchAllProducts().stream()
																		  .filter(validateCategory(category))
																		  .collect(Collectors.toList());
		
		ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.OK);
		return response;
	}

	private Predicate<Product> validateCategory(Category category) {
		
		return product -> Optional.ofNullable(category)
								  .map(value -> product.getCategory().equals(value))
								  .orElse(true);
	}

}
