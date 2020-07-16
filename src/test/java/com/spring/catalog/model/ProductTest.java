package com.spring.catalog.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ProductTest {

	@Test
	public void shouldReturnFalseWhenProductMetadataIsEmpty() {
		Map<String, String> filterMetadata = new HashMap<String, String>();
		filterMetadata.put("genre", "fiction");
		
		Product product = Product.builder().metadata(Collections.emptyMap()).build();
		
		boolean result = product.hasMetadata(filterMetadata);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void shouldReturnFalseWhenProductMetadataHasCompletelyDifferentValues() {
		Map<String, String> productMetadata = new HashMap<String, String>();
		productMetadata.put("type", "TV");
		Product product = Product.builder().metadata(productMetadata).build();
		
		Map<String, String> filterMetadata = new HashMap<String, String>();
		filterMetadata.put("genre", "fiction");
		
		boolean result = product.hasMetadata(filterMetadata);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void shouldReturnFalseWhenProductMetadataHasAtleastOneMimatch() {
		Map<String, String> productMetadata = new HashMap<String, String>();
		productMetadata.put("genre", "fiction");
		productMetadata.put("pages", "100");
		Product product = Product.builder().metadata(productMetadata).build();
		
		Map<String, String> filterMetadata = new HashMap<String, String>();
		filterMetadata.put("genre", "fiction");
		filterMetadata.put("author", "Kent Beck");
		
		boolean result = product.hasMetadata(filterMetadata);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void shouldReturnTrueWhenProductMetadataHasAllMatches() {
		Map<String, String> productMetadata = new HashMap<String, String>();
		productMetadata.put("genre", "fiction");
		productMetadata.put("pages", "100");
		productMetadata.put("author", "Kent Beck");
		Product product = Product.builder().metadata(productMetadata).build();
		
		Map<String, String> filterMetadata = new HashMap<String, String>();
		filterMetadata.put("genre", "fiction");
		filterMetadata.put("author", "Kent Beck");
		
		boolean result = product.hasMetadata(filterMetadata);
		
		assertThat(result).isTrue();
	}
}
