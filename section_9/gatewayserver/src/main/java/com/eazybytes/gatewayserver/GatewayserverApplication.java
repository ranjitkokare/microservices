package com.eazybytes.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				// allow us to define the custom routing configuration
				.route(p -> p // p indicates path predicate
						// path which external application is going to invoke
						.path("/eazybank/accounts/**")
						// path after prefix assume that as segment
						// using the same segment value/path, request to be forwarded to the actual microservice
						// same configuration is used inside actuator filter
						.filters(f -> f.rewritePath("/eazybank/accounts/(?<segment>.*)", "/${segment}")
								// to which microservice the request is going to be forwarded(microservice name registered in eureka)
								// time at which the response is sent to the client
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						// tell spring cloud gateway to use lb indicates client side load balancer
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						// 1. Check if given request value is matches with the oath predicate value
						.path("/eazybank/loans/**")
						// 2. If it matches then trying to invoke one of the predefined filter
						// available inside the Spring Cloud Gateway Server which is rewritePath
						.filters(f -> f.rewritePath("/eazybank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						// 3. Once the rewritePath filter is executed, try to forward the request to the
						// actual microservice
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/eazybank/cards/**")
						.filters(f -> f.rewritePath("/eazybank/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				// build method creates object of type RouteLocator is created which is converted to Bean
				.build();
	}
}