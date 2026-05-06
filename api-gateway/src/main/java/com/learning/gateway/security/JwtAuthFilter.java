package com.learning.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAuthFilter
       extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private final JwtHelper jwtHelper;

    public JwtAuthFilter(JwtHelper jwtHelper) {
        super(Config.class);
        this.jwtHelper = jwtHelper;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String authHeader =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(
                         HttpHeaders.AUTHORIZATION);

            if (authHeader == null ||
                !authHeader.startsWith(
                 "Bearer ")) {

                log.warn("Missing or invalid " +
                         "Authorization header!");

                return onError(exchange,
                    HttpStatus.UNAUTHORIZED);
            }

            String token =
                authHeader.substring(7);

            if (!jwtHelper.validateToken(token)) {

                log.warn("Invalid JWT token!");

                return onError(exchange,
                    HttpStatus.UNAUTHORIZED);
            }

            String username =
                jwtHelper.extractUsername(token);

            String role =
                jwtHelper.extractRole(token);

            log.info("Valid token for: {}",
                     username);

            ServerWebExchange modifiedExchange =
                exchange.mutate()
                        .request(r -> r
                            .header("username",
                                    username)
                            .header("role", role))
                        .build();

            return chain.filter(modifiedExchange);
        };
    }

    private Mono<Void> onError(
            ServerWebExchange exchange,
            HttpStatus status) {

        exchange.getResponse()
                .setStatusCode(status);

        return exchange.getResponse()
                       .setComplete();
    }

    public static class Config {
    }
}