package com.hibicode.beerstore.core.exceptions;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException{
    private final String code = "business";

    @NonNull
    private final String Message;
}