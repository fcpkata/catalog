package com.spring.catalog.model;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FilterCriteria {

	private String categoryId;
	private Map<String,String> metaDataFilters;
	
}
