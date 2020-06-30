package com.spring.catalog.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.catalog.model.Product;


@RestController
@RequestMapping("/v1")
public class CatalogController {

	@GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getCatalog() {
		List<Product> products = new ArrayList<>(Arrays.asList(new Product("Book", "Classmate", "100-pages", "50"),
				new Product("Smartphone", "Samsung", "Model-S10", "1000")));
		ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.OK);
		return response;
	}

}
