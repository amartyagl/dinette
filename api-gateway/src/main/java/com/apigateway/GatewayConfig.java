package com.apigateway;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {

	@Autowired
	AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authentication-service", r -> r.path("/authentication-service/**")
                        .filters(f -> f.rewritePath("/authentication-service(?<segment>/?.*)", "$\\{segment}").filter(filter))
                        .uri("http://localhost:8082/"))

                .route("user-service", r -> r.path("/user-service/**")
                        .filters(f -> f.rewritePath("/user-service(?<segment>/?.*)", "$\\{segment}").filter(filter))
                        .uri("http://localhost:8081/"))
                
                .route("menu-service", r -> r.path("/menu-service/**")
                        .filters(f -> f.rewritePath("/menu-service(?<segment>/?.*)", "$\\{segment}").filter(filter))
                        .uri("http://localhost:9001/"))
                
                .route("notification-service", r -> r.path("/notification-service/**")
                        .filters(f -> f.rewritePath("/notification-service(?<segment>/?.*)", "$\\{segment}").filter(filter))
                        .uri("http://localhost:8083/"))
                
                .route("order-service", r -> r.path("/order-service/**")
                        .filters(f -> f.rewritePath("/order-service(?<segment>/?.*)", "$\\{segment}").filter(filter))
                        .uri("http://localhost:8087/"))
                
                .route("payment-service", r -> r.path("/payment-service/**")
                        .filters(f -> f.rewritePath("/payment-service(?<segment>/?.*)", "$\\{segment}").filter(filter))
                        .uri("http://localhost:9002/"))
                
                .route("subscription-service", r -> r.path("/subscription-service/**")
                        .filters(f -> f.rewritePath("/subscription-service(?<segment>/?.*)", "$\\{segment}").filter(filter))
                        .uri("http://localhost:8086/"))
                .build();
    }
	

}
