package com.test.controller;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.test.dto.TestRequestDto;
import com.test.dto.TestResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestOrganization {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testNoEmployeesProvided() throws Exception {
		TestRequestDto request = new TestRequestDto();

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("KO", response.getResponseStatus());
	}

	@Test
	public void testEmployeesEmptyProvided() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("");
		request.setEmployee2("");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("KO", response.getResponseStatus());
	}

	@Test
	public void testOneEmployeeEmptyProvided() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("Geralt");
		request.setEmployee2("");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("KO", response.getResponseStatus());
	}

	@Test
	public void testOneEmployeeNotWorkingInCompany() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("John");
		request.setEmployee2("Alice");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("KO", response.getResponseStatus());
	}

	@Test
	public void testBabyYodaIsBossInCEO() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("Baby Yoda");
		request.setEmployee2("Baby Yoda");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("OK", response.getResponseStatus());
		assertEquals("Baby Yoda", response.getBoss());
		assertEquals("CEO", response.getTeam());
	}

	@Test
	public void testJohnAndSylviaHaveGeraltInManaging() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("John");
		request.setEmployee2("Sylvia");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("OK", response.getResponseStatus());
		assertEquals("Geralt", response.getBoss());
		assertEquals("Managing", response.getTeam());
	}

	@Test
	public void testNamiAndGokuHaveGeraltInManaging() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("Nami");
		request.setEmployee2("Goku");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("OK", response.getResponseStatus());
		assertEquals("Geralt", response.getBoss());
		assertEquals("Managing", response.getTeam());
	}

	@Test
	public void testMarthaAndPaulHaveNamiInTraining() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("Paul");
		request.setEmployee2("Martha");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("OK", response.getResponseStatus());
		assertEquals("Nami", response.getBoss());
		assertEquals("Training", response.getTeam());
	}

	@Test
	public void testArnoldAndArthurHaveZoroInConsulting() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("Arnold");
		request.setEmployee2("Arthur");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("OK", response.getResponseStatus());
		assertEquals("Zoro", response.getBoss());
		assertEquals("Consulting", response.getTeam());
	}

	@Test
	public void testRickAndMortyHaveKulilinInCorporate() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("Rick");
		request.setEmployee2("Morty");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("OK", response.getResponseStatus());
		assertEquals("Kulilin", response.getBoss());
		assertEquals("Corporate", response.getTeam());
	}

	@Test
	public void testZoroAndZoroHaveGeraltInManaging() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("Zoro");
		request.setEmployee2("Zoro");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("OK", response.getResponseStatus());
		assertEquals("Geralt", response.getBoss());
		assertEquals("Managing", response.getTeam());
	}

	@Test
	public void testGeraltAndLuffyHaveBabyYodaInCEO() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("Geralt");
		request.setEmployee2("Luffy");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("OK", response.getResponseStatus());
		assertEquals("Baby Yoda", response.getBoss());
		assertEquals("CEO", response.getTeam());
	}

	@Test
	public void testGeraltAndBabyYodaHaveBabyYodaInCEO() throws Exception {
		TestRequestDto request = new TestRequestDto();
		request.setEmployee1("Geralt");
		request.setEmployee2("Baby Yoda");

		TestResponseDto response = restTemplate.postForObject("http://localhost:" + port + "/lca", request,
				TestResponseDto.class);

		assertEquals("OK", response.getResponseStatus());
		assertEquals("Baby Yoda", response.getBoss());
		assertEquals("CEO", response.getTeam());
	}
}