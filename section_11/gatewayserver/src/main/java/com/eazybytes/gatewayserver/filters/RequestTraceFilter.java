package com.eazybytes.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/*
    Responsible to generate the trace id or correlation id whenever a new request is received to the gateway server
    from the external client applications.
*/
@Order(1) // order for execution of the filter
@Component // to register the filter as a bean with gateway
public class RequestTraceFilter implements GlobalFilter { // global filter will be executed for every request
    // to the gateway server from the external client applications(all kinds of traffic)

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;


/*
* Spring Cloud Gateway is based upon Reactive model, but not upon traditional servlet model.
* Using ServerWebExchange, we can access the request and response objects associated with the exchange.
* Inside GatewayFilterChain, there can be multiple filters configured(custom/predefined).
* Using GatewayFilterChain, we can execute the next filter in the filter chain in chain manner.
* Mono is a reactive type which is used to represent a single object.
* Flux is a reactive type which is used to represent a sequence of objects.(Collection of objects)
* */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if(isCorrelationIdPresent(requestHeaders)) {
            logger.debug("eazyBank-correlation-id found in RequestTraceFilter : {}",
                    filterUtility.getCorrelationId(requestHeaders));
        }else{
            String correlationID = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationID);
            logger.debug("eazyBank-correlation-id generated in RequestTraceFilter : {}",correlationID);
        }
        // Invoke the next filter
        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if(filterUtility.getCorrelationId(requestHeaders)!=null) {
            return true;
        }else {
            return false;
        }
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

}
