package com.tomstry.LendMeApi.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass) {
        super("Entity of type" + entityClass.getClass().getSimpleName() + "not found");
    }
}

