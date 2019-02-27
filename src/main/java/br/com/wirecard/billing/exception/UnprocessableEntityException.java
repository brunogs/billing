package br.com.wirecard.billing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
    }

    public UnprocessableEntityException(String customMessage) {
        super(customMessage);
    }
}
