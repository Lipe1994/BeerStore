package com.hibicode.beerstore.api.handlers;

import com.hibicode.beerstore.api.configurations.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Locale;

@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiGenericErrorHandler {

    @Autowired
    ApiErrorHandler apiErrorHandler;
    private static final Logger log = LoggerFactory.getLogger(ApiGenericErrorHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> invalidFormatException(Exception exception, Locale locale)
    {
        log.error("Error not Expected", exception);
        final var code = "internal-server-error";
        final var apiError = apiErrorHandler.toApiError(code, locale);
        final var errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, Arrays.asList(apiError) );

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
