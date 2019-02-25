package br.com.wirecard.billing.service.processor;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.domain.PaymentType;
import org.springframework.stereotype.Service;

@Service
public class CardProcessorService implements PaymentProcessorService {

    @Override
    public Payment process(Payment payment) {
        //TODO implement
        return null;
    }

    @Override
    public PaymentType getType() {
        return PaymentType.CREDIT_CARD;
    }

}
