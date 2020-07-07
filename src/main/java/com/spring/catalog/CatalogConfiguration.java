package com.spring.catalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.catalog.repository.MockRepository;
import com.spring.catalog.repository.Repository;

@Configuration
public class CatalogConfiguration {
	
	@Bean
	public Repository getRepository() {
		return new MockRepository();
	}

}
