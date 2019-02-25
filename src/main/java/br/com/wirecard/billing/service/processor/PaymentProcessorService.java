package br.com.wirecard.billing.service.processor;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.domain.PaymentType;

public interface PaymentProcessorService {

    Payment process(Payment payment);

    PaymentType getType();

}
