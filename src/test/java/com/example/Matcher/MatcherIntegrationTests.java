package com.example.Matcher;

import com.example.Matcher.entities.Order;
import com.example.Matcher.enums.Action;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MatcherIntegrationTests {
	@LocalServerPort
	private int port;
	@Autowired
	private Matcher matcher;

	@BeforeEach
	void setUp() {
		matcher.setToBuy(new ArrayList<>());
		matcher.setToSell(new ArrayList<>());
		matcher.setTradeList(new ArrayList<>());
		matcher.setOneAccount(new ArrayList<>());
	}
	@Autowired
	private TestRestTemplate restTemplate;


	@Test
	public void startUp() throws Exception {
		assertThat(this.restTemplate.getForObject(createURLWithPort("/"),
				String.class)).contains("Hello, World!");
	}

	@Test
	public void startUpNic() throws Exception {
		assertThat(this.restTemplate.getForObject(createURLWithPort("?name=Nic"),
				String.class)).contains("Hello, Nic!");
	}

	@Test
	public void aggregate() throws Exception {
		Order order1 = new Order("Nic", 15, 12, Action.BUY, 1000);
		Order order2 = new Order("George", 14, 8, Action.BUY, 1000);
		matcher.trade(order1);
		matcher.trade(order2);
		matcher.aggregate(5);
		assertThat(this.restTemplate.getForObject(createURLWithPort("/aggregate?agg=5"),
				String.class)).contains("[[{\"price\":15,\"quantity\":20}],[]]");
	}

	@Test
	public void newOrder() throws Exception {
		Order order1 = new Order("Nicholas", 15, 6, Action.BUY, 1000);
		ResponseEntity<String> response = this.restTemplate.postForEntity(createURLWithPort("/neworder?agg=5"), order1, String.class);
//		assertThat(response).contains("[[{\"price\":15,\"quantity\":6}],[],[]]");
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		Assertions.assertEquals(response.getBody(), "[[{\"price\":15,\"quantity\":6}],[],[]]");
	}

	@Test
	public void badPriceOrder() throws Exception {
		Order order1 = new Order("Nicholas", 0, 6, Action.BUY, 1000);
		ResponseEntity<String> response = this.restTemplate.postForEntity(createURLWithPort("/neworder?agg=5"), order1, String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void badQuantityOrder() throws Exception {
		Order order1 = new Order("Nicholas", 6, 0, Action.BUY, 1000);
		ResponseEntity<String> response = this.restTemplate.postForEntity(createURLWithPort("/neworder?agg=5"), order1, String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void badAccountOrder() throws Exception {
		Order order1 = new Order(null, 5, 6, Action.BUY, 1000);
		ResponseEntity<String> response = this.restTemplate.postForEntity(createURLWithPort("/neworder?agg=5"), order1, String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
