package com.spring.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ProductNotFoundException extends HttpClientErrorException {
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException() {
		super(HttpStatus.NOT_FOUND, "Product Not Found");
	}
}
