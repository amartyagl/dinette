package com.apigateway;

import java.util.List; 
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

	public static final List<String> openApiEndPoints = List.of("/v1/api/signIn", "/v1/api/signUp", "/api/v1/addUser", "/v1/api/menu");

	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndPoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));


}
