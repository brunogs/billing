package br.com.wirecard.billing.service.processor;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.domain.PaymentType;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class CardProcessorService extends PaymentProcessorService {

    protected CardProcessorService(Validator validator) {
        super(validator);
    }

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
