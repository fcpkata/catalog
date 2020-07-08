package com.spring.catalog.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ProductMetadataTest {

	@Test
	public void shouldReturnFalseWhenProductMetadataIsEmpty() {
		ProductMetadata productMetadata = new ProductMetadata();
		Map<String, String> filterMetadata = new HashMap<String, String>();
		filterMetadata.put("genre", "fiction");
		
		boolean result = productMetadata.containsAll(filterMetadata);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void shouldReturnFalseWhenProductMetadataHasCompletelyDifferentValues() {
		ProductMetadata productMetadata = new ProductMetadata();
		productMetadata.add("type", "TV");
		Map<String, String> filterMetadata = new HashMap<String, String>();
		filterMetadata.put("genre", "fiction");
		
		boolean result = productMetadata.containsAll(filterMetadata);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void shouldReturnFalseWhenProductMetadataHasAtleastOneMimatch() {
		ProductMetadata productMetadata = new ProductMetadata();
		productMetadata.add("genre", "fiction");
		productMetadata.add("pages", "100");
		
		Map<String, String> filterMetadata = new HashMap<String, String>();
		filterMetadata.put("genre", "fiction");
		filterMetadata.put("author", "Kent Beck");
		
		boolean result = productMetadata.containsAll(filterMetadata);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void shouldReturnTrueWhenProductMetadataHasAllMatches() {
		ProductMetadata productMetadata = new ProductMetadata();
		productMetadata.add("genre", "fiction");
		productMetadata.add("pages", "100");
		productMetadata.add("author", "Kent Beck");
		
		Map<String, String> filterMetadata = new HashMap<String, String>();
		filterMetadata.put("genre", "fiction");
		filterMetadata.put("author", "Kent Beck");
		
		boolean result = productMetadata.containsAll(filterMetadata);
		
		assertThat(result).isTrue();
	}
}
