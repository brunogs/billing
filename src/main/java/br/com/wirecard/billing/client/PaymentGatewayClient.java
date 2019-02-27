package br.com.wirecard.billing.client;

import br.com.wirecard.billing.domain.Payment;

import java.util.Optional;

public interface PaymentGatewayClient {

    Optional<Payment> processCardPayment(Payment payment) throws Exception;

}
