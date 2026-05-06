package com.learning.common.config;

import com.learning.common.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignErrorDecoder
       implements ErrorDecoder {

    @Override
    public Exception decode(
           String methodKey,
           Response response) {

        log.error("Feign error: {} {}",
                  response.status(),
                  methodKey);

        switch (response.status()) {
            case 400:
                return new BadApiRequest(
                    "Bad request!");
            case 401:
                return new AccessDeniedException(
                    "Unauthorized!");
            case 403:
                return new AccessDeniedException(
                    "Access denied!");
            case 404:
                return new
                    ResourceNotFoundException(
                    "Resource not found!");
            case 500:
                return new RuntimeException(
                    "External service error!");
            case 503:
                return new RuntimeException(
                    "Service unavailable!");
            default:
                return new RuntimeException(
                    "Unknown error: "
                    + response.status());
        }
    }
}