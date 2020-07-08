package com.spring.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private String id;
	private String name;
	private String description;
	private Price price;
//	private ProductType type;
	private Category category;

}