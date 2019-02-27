package br.com.wirecard.billing.service.processor;

import br.com.wirecard.billing.client.PaymentGatewayClient;
import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.domain.PaymentStatus;
import br.com.wirecard.billing.domain.PaymentType;
import br.com.wirecard.billing.exception.ServiceUnavailableException;
import br.com.wirecard.billing.repository.PaymentRepository;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Slf4j
@Service
public class CardProcessorService extends PaymentProcessorService {

    private final PaymentGatewayClient paymentGatewayClient;
    private final PaymentRepository paymentRepository;

    protected CardProcessorService(Validator validator, PaymentGatewayClient paymentGatewayClient, PaymentRepository paymentRepository) {
        super(validator);
        this.paymentGatewayClient = paymentGatewayClient;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment process(Payment payment) {
        try {
            PaymentStatus resultStatus = paymentGatewayClient.processCardPayment(payment)
                    .map(Payment::getStatus)
                    .orElse(PaymentStatus.DENIED);
            payment.setStatus(resultStatus);
            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public PaymentType getType() {
        return PaymentType.CREDIT_CARD;
    }

}
