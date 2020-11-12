package com.empresa.api;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class FuncionarioApiApplicationTestApi {
	
	@LocalServerPort
	private int port;

	@Before
	public void setUp() {

		enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.port = port;
		RestAssured.basePath = "/funcionarios";
		
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultaTodosOsFuncionarios() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get() //Method GET
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultaUmFuncionarioPorId() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/1") //Method GET
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoConsultaUmFuncionarioQueNaoExiste() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/100") //Method GET
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
}
