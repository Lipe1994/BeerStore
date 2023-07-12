package com.hibecode.beerstore.api.configurations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private final int statusCode;
    private final List<ApiError> errors;

    public static ErrorResponse of(HttpStatus status, List<ApiError> errors)
    {
        return new ErrorResponse(status.value(), errors);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @RequiredArgsConstructor(access = AccessLevel.PUBLIC)
    static public class ApiError{
        private final String code;
        private final String message;

    }
}
