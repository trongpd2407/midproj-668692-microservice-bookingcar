package org.example.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringCloudConfig {

    private static final String ALLOWED_HEADERS = "Access-Control-Allow-Headers";
    private static final String ALLOWED_METHODS = "Access-Control-Allow-Methods";
    private static final String ALLOWED_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("bookingModule", r -> r.path("/booking/**")
                        .uri("lb://BOOKING-SERVICE"))
                .route("billModule", r -> r.path("/bill/**")
                        .uri("lb://BILL-SERVICE"))
                .route("mailSenderModule", r -> r.path("/mailsender/**")
                        .uri("lb://MAILSENDER-SERVICE"))
                .route("locationModule", r -> r.path("/location/**")
                        .uri("lb://LOCATION-SERVICE"))
                .route("clientModule", r -> r.path("/client/**")
                        .filters(f -> f.filter(corsFilter()))
                        .uri("lb://CLIENT-SERVICE"))
                .route("tripModule", r -> r.path("/trip/**")
                        .uri("lb://TRIP-SERVICE"))
                .build();
    }

    @Bean
    public GatewayFilter corsFilter() {
        return (exchange, chain) -> {
            ServerWebExchange.Builder mutatedExchange = exchange.mutate();
            mutatedExchange.request(mutatedRequest -> mutatedRequest.headers(httpHeaders -> {
                httpHeaders.add(ALLOWED_ORIGIN, "*");
                httpHeaders.add(ALLOWED_METHODS, "GET, PUT, POST, DELETE, OPTIONS");
                httpHeaders.add(ALLOWED_HEADERS, "Content-Type, Authorization");
                httpHeaders.add(ALLOW_CREDENTIALS, "true");
            }));
            return chain.filter(mutatedExchange.build());
        };
    }
}