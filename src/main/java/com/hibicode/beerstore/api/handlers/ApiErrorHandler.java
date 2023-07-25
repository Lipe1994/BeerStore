package com.hibicode.beerstore.api.handlers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hibicode.beerstore.api.configurations.ErrorResponse;
import com.hibicode.beerstore.core.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiErrorHandler {
    private static final String NO_MESSAGE_AVAILABLE = "No message available";
    private static final Logger log = LoggerFactory.getLogger(ApiErrorHandler.class);

    private final MessageSource apiErrorMessageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception, Locale locale)
    {
        final Stream<ObjectError> errors = exception
                .getAllErrors()
                .stream();

        final List<ErrorResponse.ApiError> apiErrors = errors
                .map(ObjectError::getDefaultMessage)
                .map(code -> toApiError(code, locale))
                .collect(Collectors.toList());

        final var errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> invalidFormatException(InvalidFormatException exception, Locale locale)
    {
        final var code = "invalid-argument";
        final var apiError = toApiError(code, locale, exception.getValue());
        final var errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, Arrays.asList(apiError) );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> invalidFormatException(BusinessException exception, Locale locale)
    {
        final var apiError = toApiError(exception.getCode(), locale, exception.getMessage());
        final var errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, Arrays.asList(apiError) );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    public ErrorResponse.ApiError toApiError(String code, Locale locale, Object... args)
    {
        String message = "";

        try{
            message = apiErrorMessageSource.getMessage(code, args, locale);
        }catch(NoSuchMessageException e)
        {
            log.error("Could not find any message for {} code under {} locale", code, locale);
            message = NO_MESSAGE_AVAILABLE;
        }

        return new ErrorResponse.ApiError(code, message);
    }
}
