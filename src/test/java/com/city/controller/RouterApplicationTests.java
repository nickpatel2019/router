package com.city.controller;

import com.city.router.RouterApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration Test to verify the sanity of the app
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouterApplicationTests {

	@LocalServerPort
	int port;

	@Autowired
	RouteController  routeController;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	void contextLoads() {
		Assertions.assertThat(routeController).isNotNull();
	}

	@Test
	void verifyIfRouteExists(){
		String response = this.restTemplate.getForObject("http://localhost:"+port+"/connected?origin=Newyork&destination=Philadelphia",String.class);
		Assertions.assertThat(response).isEqualTo("yes");

	}
}
