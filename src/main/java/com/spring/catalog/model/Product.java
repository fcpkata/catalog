package com.spring.catalog.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	private Map<String, String> metadata;
	private int price;
	private int shippingPrice;
	private int rating;
	private Category category;
	private List<String> imageLinks;
	
	public void setMetadata(Map<String, String> metadata) {
		if(metadata == null) {
			this.metadata = Collections.emptyMap();
		}
		this.metadata = Collections.unmodifiableMap(metadata);
	}
	
	public boolean hasMetadata(Map<String, String> metaDataFilters) {
		return containsAll(metaDataFilters);
	}

	private boolean containsAll(Map<String, String> metaDataFilters) {
		if(this.metadata.isEmpty()) {
			return false;
		}
		
		List<String> matchedKeys = metaDataFilters.keySet()
		.stream()
		.filter(key -> {
			String value = metadata.get(key);
			return (value != null && metaDataFilters.get(key).equals(value));
		})
		.collect(Collectors.toList());
		
		return matchedKeys.size() == metaDataFilters.size();
	}
}