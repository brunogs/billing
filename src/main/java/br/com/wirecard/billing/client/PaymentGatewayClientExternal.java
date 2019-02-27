package br.com.wirecard.billing.client;

import br.com.wirecard.billing.domain.Payment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Profile("prod")
@Component
public class PaymentGatewayClientExternal implements PaymentGatewayClient {

    private RestTemplate restTemplate;

    public PaymentGatewayClientExternal() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Optional<Payment> processCardPayment(Payment payment) throws Exception {
        Payment paymentResult = restTemplate.postForEntity("http://mockkid:8080/card-payment", payment, Payment.class).getBody();
        paymentResult.setStatus(paymentResult.getStatus());
        return Optional.of(paymentResult);
    }

}
