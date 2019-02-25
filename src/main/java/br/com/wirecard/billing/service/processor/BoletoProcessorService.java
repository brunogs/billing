package br.com.wirecard.billing.service.processor;

import br.com.wirecard.billing.domain.Boleto;
import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.domain.PaymentType;
import br.com.wirecard.billing.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.UUID;

@Service
public class BoletoProcessorService extends PaymentProcessorService {

    private final PaymentRepository paymentRepository;

    protected BoletoProcessorService(Validator validator, PaymentRepository paymentRepository) {
        super(validator);
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment process(Payment payment) {
        Boleto boleto = new Boleto(UUID.randomUUID().toString());
        payment.setBoleto(boleto);
        return paymentRepository.save(payment);
    }

    @Override
    public PaymentType getType() {
        return PaymentType.BOLETO;
    }

}
