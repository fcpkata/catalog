package com.spring.catalog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.catalog.model.FilterCriteria;
import com.spring.catalog.model.Product;
import com.spring.catalog.service.CatalogService;
import com.spring.catalog.service.InventoryService;

@RestController
@RequestMapping(value = "/catalog/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatalogController {
	
	private CatalogService catalogService;
	private InventoryService inventoryService;
	
	@Autowired
	public CatalogController(CatalogService catalogService, @Qualifier("productinventory") InventoryService inventoryService) {
		this.catalogService = catalogService;
		this.inventoryService = inventoryService;
	}

	@GetMapping(path = "/products" )
	public ResponseEntity<List<Product>> getCatalog(
			@RequestParam(name= "category", required = false) String categoryId,
			@RequestParam(name= "genre", required = false) String genre,
			@RequestParam(name= "name", required = false) String name) {
		Map<String, String> metadata = new HashMap<>();
		if(!StringUtils.isEmpty(genre)) {
			metadata.put("genre", genre);
		}
		FilterCriteria criteria = FilterCriteria.builder()
				.categoryId(categoryId)
				.metaDataFilters(metadata)
				.name(name)
				.build();
		
		List<Product> products = catalogService.getProducts(criteria);
		
		ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(path = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProductDetailsById(@PathVariable(value = "productId") String productId) {

		Product response = catalogService.getProduct(productId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}/quantity")
	public ResponseEntity<String> getQuantity(@PathParam("productId") String productName) {
		
		inventoryService.checkProductAvailablity(productName);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
