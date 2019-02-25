package br.com.wirecard.billing.service.processor;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.domain.PaymentType;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class BoletoProcessorService extends PaymentProcessorService {

    protected BoletoProcessorService(Validator validator) {
        super(validator);
    }

    @Override
    public Payment process(Payment payment) {
        //TODO
        return payment;
    }

    @Override
    public PaymentType getType() {
        return PaymentType.BOLETO;
    }

}
