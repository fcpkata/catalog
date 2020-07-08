package com.spring.catalog.model;

import java.util.HashMap;
import java.util.Map;

public class ProductMetadata {

	private HashMap<String, String> metadata;

	public ProductMetadata() {
		this.metadata = new HashMap<String, String>();
	}

	public void add(String key, String value) {
		this.metadata.put(key, value);
	}

	public Map<String, String> getContents() {
		HashMap<String, String> metadataCopy = new HashMap<String, String>(metadata);
		return metadataCopy;
	}
}
