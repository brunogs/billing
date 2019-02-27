package br.com.wirecard.billing.config

import br.com.wirecard.billing.client.PaymentGatewayClient
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

@TestConfiguration
class IntegrationTestMockingConfig {
    private DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    PaymentGatewayClient paymentGatewayClient() {
        factory.Mock(PaymentGatewayClient)
    }
}
