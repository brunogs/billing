package br.com.wirecard.billing.service.processor;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.domain.PaymentType;

import javax.validation.Valid;
import javax.validation.Validator;

import static java.util.stream.Collectors.joining;

public abstract class PaymentProcessorService {

    private final Validator validator;

    protected PaymentProcessorService(Validator validator) {
        this.validator = validator;
    }

    public Payment validateAndProcess(@Valid Payment payment) {
        validate(payment);
        return process(payment);
    }

    protected void validate(Payment payment) {
        var constraintViolations = validator.validate(payment);
        if (!constraintViolations.isEmpty()) {
            var constraints = constraintViolations.stream()
                    .map(it -> it.getPropertyPath() + " " + it.getMessage())
                    .collect(joining(";"));
            throw new IllegalArgumentException(constraints);
        }
    }

    protected abstract Payment process(Payment payment);

    public abstract PaymentType getType();

}
