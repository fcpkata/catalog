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
<<<<<<< HEAD
	private boolean detailsPresent;
=======
//	private ProductType type;
>>>>>>> d914af0be681c8e07bff24842fa919f7279d62e6
	private Category category;

}