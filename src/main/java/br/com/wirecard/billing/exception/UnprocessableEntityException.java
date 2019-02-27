package br.com.wirecard.billing.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ResponseStatus(UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException() {
        super(UNPROCESSABLE_ENTITY.getReasonPhrase());
    }

    public UnprocessableEntityException(String customMessage) {
        super(customMessage);
    }
}
