package br.com.wirecard.billing.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super(NOT_FOUND.getReasonPhrase());
    }

}
