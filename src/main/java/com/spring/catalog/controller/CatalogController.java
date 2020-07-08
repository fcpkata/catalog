package com.spring.catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.catalog.model.FilterCriteria;
import com.spring.catalog.model.Product;
import com.spring.catalog.service.CatalogService;


@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatalogController {
	
	@Autowired
	private CatalogService catalogService;

	@GetMapping(path = "/products" )
	public ResponseEntity<List<Product>> getCatalog(@RequestParam(name= "category", required = false) String categoryId) {
		
		FilterCriteria criteria = FilterCriteria.builder().categoryId(categoryId).build();
		List<Product> products = catalogService.getProducts(criteria);
		ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.OK);
		return response;
	}

}
