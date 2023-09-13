package com.samuel.gatewaymeme.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("usuarios", r -> r
						.path("/usuarios/**")
						.uri("http://localhost:8000"))
				.route("categorias", r -> r
						.path("/categorias/**")
						.uri("http://localhost:8200"))
				.build();
	}
}
