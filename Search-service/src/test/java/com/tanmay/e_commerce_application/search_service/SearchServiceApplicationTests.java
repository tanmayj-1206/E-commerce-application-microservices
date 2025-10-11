package com.tanmay.e_commerce_application.search_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tanmay.e_commerce_application.search_service.Repository.ProductRepo;

@SpringBootTest
class SearchServiceApplicationTests {

	@MockBean
    private ProductRepo productRepo;

	@Test
	void contextLoads() {
	}

}
