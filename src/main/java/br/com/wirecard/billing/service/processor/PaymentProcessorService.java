package br.com.wirecard.billing.service.processor;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.domain.PaymentType;
import br.com.wirecard.billing.exception.ServiceUnavailableException;
import br.com.wirecard.billing.exception.UnprocessableEntityException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Validator;

import static java.util.stream.Collectors.joining;

@Slf4j
public abstract class PaymentProcessorService {

    private final Validator validator;

    protected PaymentProcessorService(Validator validator) {
        this.validator = validator;
    }

    @HystrixCommand(
            commandKey = "validateAndProcess",
            fallbackMethod = "validateAndProcessFallback",
            ignoreExceptions = UnprocessableEntityException.class,
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
            }
    )
    public Payment validateAndProcess(Payment payment) {
        validate(payment);
        return process(payment);
    }

    protected void validate(Payment payment) {
        var constraintViolations = validator.validate(payment);
        if (!constraintViolations.isEmpty()) {
            var constraints = constraintViolations.stream()
                    .map(it -> it.getPropertyPath() + " " + it.getMessage())
                    .collect(joining(";"));
            throw new UnprocessableEntityException(constraints);
        }
    }

    public Payment validateAndProcessFallback(Payment payment) {
        //TODO here we can direct to another gateway
        log.warn("processing fallback. payment={}", payment);

        var message = "Serviço temporariamente indisponivel tente outro tipo de pagamento";
        throw new ServiceUnavailableException(message);
    }

    protected abstract Payment process(Payment payment);

    public abstract PaymentType getType();

}
