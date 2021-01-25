package com.tomstry.LendMeApi.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Dates overlap")
public class OverlappingDateException extends RuntimeException {
}
