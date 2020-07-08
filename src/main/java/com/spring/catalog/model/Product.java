package com.spring.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Product {

	private String id;
	private String name;
	private String description;
	private String price;
	//private Optional<ProductMetadata> metadata;
	private Category category;

}