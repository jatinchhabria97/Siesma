package com.example.testapp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {
	@Autowired
	private EmployeeController controller;
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
