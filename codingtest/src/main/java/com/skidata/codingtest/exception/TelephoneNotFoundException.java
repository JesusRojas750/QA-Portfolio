package com.skidata.codingtest.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TelephoneNotFoundException extends RuntimeException {

    public TelephoneNotFoundException(UUID telephoneId) {
        super("Telephone with ID " + telephoneId + " not found.");
    }
}