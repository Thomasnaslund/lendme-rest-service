package com.tomstry.LendMeApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity not found")
    public class LoanNotFoundException extends RuntimeException {
    }

