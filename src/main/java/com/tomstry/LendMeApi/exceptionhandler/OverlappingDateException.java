package com.tomstry.LendMeApi.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class OverlappingDateException extends RuntimeException {
    public OverlappingDateException(Integer id) {
        super(String.format("Item with Id %d has other loans that intersects this one", id));
    }

}
