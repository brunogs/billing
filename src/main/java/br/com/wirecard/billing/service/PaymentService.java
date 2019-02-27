package br.com.wirecard.billing.service;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.exception.NotFoundException;
import br.com.wirecard.billing.exception.UnprocessableEntityException;
import br.com.wirecard.billing.repository.PaymentRepository;
import br.com.wirecard.billing.service.processor.PaymentProcessorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final List<PaymentProcessorService> paymentProcessorServices;
    private final PaymentRepository paymentRepository;

    public PaymentService(List<PaymentProcessorService> paymentProcessorServices, PaymentRepository paymentRepository) {
        this.paymentProcessorServices = paymentProcessorServices;
        this.paymentRepository = paymentRepository;
    }

    public Payment process(Payment payment) {
        PaymentProcessorService paymentProcessorService = paymentProcessorServices.stream()
                .filter(it -> it.getType().equals(payment.getType()))
                .findFirst()
                .orElseThrow(UnprocessableEntityException::new);
        return paymentProcessorService.validateAndProcess(payment);
    }

    public Payment getById(String paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(NotFoundException::new);
    }
}
