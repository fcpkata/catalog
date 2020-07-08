package com.spring.catalog.controller;
import java.util.List;
<<<<<<< HEAD
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
=======
>>>>>>> d914af0be681c8e07bff24842fa919f7279d62e6

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.catalog.model.BookCategory;
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
<<<<<<< HEAD
	public ResponseEntity<List<Product>> getCatalog(@RequestParam(name = "category", required = false) Category category) {
		
		ResponseEntity<List<Product>> response = new ResponseEntity<>(productService.fetchProductsFor(category), HttpStatus.OK);
=======
	public ResponseEntity<List<Product>> getCatalog(@RequestParam(name = "category", required = false) BookCategory bookCategory) {
		
		ResponseEntity<List<Product>> response = new ResponseEntity<>(productService.fetchProductsFor(bookCategory), HttpStatus.OK);
>>>>>>> d914af0be681c8e07bff24842fa919f7279d62e6
		return response;
	}
	
	@GetMapping(path = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProductDetailsById(@PathVariable(value = "productId") String productId) {

		Product response = getProductDetailsFromDb(productId);

<<<<<<< HEAD
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	public Product getProductDetailsFromDb(String productId) {

		Map<String, List<Product>> productDetails =  productService.fetchProductsFor(null).stream()
				.collect(Collectors.groupingBy(Product :: getId, 
						Collectors.toList()));

		return Optional.ofNullable(productDetails.get(productId)).map( product -> {
			Product value = product.get(0);
			value.setDetailsPresent(true);
			return value;
		}).orElse(new Product());
	}
=======
>>>>>>> d914af0be681c8e07bff24842fa919f7279d62e6
}
