package com.spring.catalog.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Product {

	private String id;
	private String name;
	private String description;
	private ProductMetadata metadata;
	private int price;
	private int shippingPrice;
	private int rating;
	private Category category;
	private List<String> imageLinks;
	
	public boolean hasMetadata(Map<String, String> metaDataFilters) {
		return metadata.containsAll(metaDataFilters);
	}

}