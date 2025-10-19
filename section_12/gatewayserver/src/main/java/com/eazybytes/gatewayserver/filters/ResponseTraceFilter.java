package com.eazybytes.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

/*
* Responsible to add trace id or correlation id whenever a response is sent from the gateway server
* to the external client applications, so that the client also is aware about that trace id associated
* with the particular request
* */
@Configuration
public class ResponseTraceFilter { // Acts as a post filter and intercept the response

    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    /*
    * Here we instead of implementing the GlobalFilter interface we are implementing our own custom filter by creating
    * a bean of type GlobalFilter
    * */

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            // ResponseTraceFilter gets executed only after given request is sent to the respective microservice and the
            // response is received from the respective microservice
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = filterUtility.getCorrelationId(requestHeaders);

                // we check whether the correlation id is already present in the outbound headers
                if(!(exchange.getResponse().getHeaders().containsKey(filterUtility.CORRELATION_ID))) {
                    logger.debug("Update the correlationid to the outbound headers: {}",correlationId);
                    exchange.getResponse().getHeaders().add(filterUtility.CORRELATION_ID, correlationId);
                }
            }));
        };
    }
}
