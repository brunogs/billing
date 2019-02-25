package br.com.wirecard.billing.service;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.service.processor.PaymentProcessorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final List<PaymentProcessorService> paymentProcessorServices;

    public PaymentService(List<PaymentProcessorService> paymentProcessorServices) {
        this.paymentProcessorServices = paymentProcessorServices;
    }

    public Payment proccess(Payment payment) {
        PaymentProcessorService paymentProcessorService = paymentProcessorServices.stream()
                .filter(it -> it.getType().equals(payment.getType()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return paymentProcessorService.process(payment);
    }

}
