package br.com.wirecard.billing.client;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.domain.PaymentStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Profile("prod")
@Component
public class PaymentGatewayClientExternal implements PaymentGatewayClient {

    @Override
    public Optional<Payment> processCardPayment(Payment payment) throws Exception {
        //TODO request mock server
        Payment paymentResult = new Payment();
        paymentResult.setStatus(PaymentStatus.AUTHORIZED);
        return Optional.of(paymentResult);
    }

}
