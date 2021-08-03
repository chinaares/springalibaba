package com.example.nacosgateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 将表单参数转换为AuthenticationToken
 *
 */
@Component
public class AuthenticationConverter extends ServerFormLoginAuthenticationConverter {

    private String usernameParameter = "username";

    private String passwordParameter = "password";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String tenant = headers.getFirst("_tenant");
        String host = headers.getHost().getHostName();
        return exchange.getFormData()
                .map(data -> {
                    String username = data.getFirst(this.usernameParameter);
                    String password = data.getFirst(this.passwordParameter);
                    return new AuthenticationToken(username, password, tenant, host);
                });
    }

}