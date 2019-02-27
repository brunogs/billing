package br.com.wirecard.billing.service.processor

import br.com.wirecard.billing.BillingApplicationTests
import br.com.wirecard.billing.client.PaymentGatewayClient
import br.com.wirecard.billing.config.IntegrationTestMockingConfig
import br.com.wirecard.billing.domain.*
import br.com.wirecard.billing.exception.ServiceUnavailableException
import br.com.wirecard.billing.repository.PaymentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

import static br.com.wirecard.billing.domain.PaymentStatus.AUTHORIZED
import static br.com.wirecard.billing.domain.PaymentStatus.DENIED

class CardProcessorServiceTest extends BillingApplicationTests {

    @Autowired
    private CardProcessorService cardProcessorService

    @Autowired
    private PaymentGatewayClient paymentGatewayClientMock

    @Autowired
    private PaymentRepository paymentRepository

    private Payment payment

    void setup() {
        payment = new Payment(
                client: new Client(id: UUID.randomUUID().toString()),
                amount: BigDecimal.valueOf(42.0),
                type: PaymentType.CREDIT_CARD,
                buyer: new Buyer(
                        name: 'Buyer Test',
                        email: 'buyer@test.com',
                        cpf: '62745605569'
                ),
                card: new Card(
                        holderName: 'test',
                        number: '5555666677778884',
                        expirationDate: "12/18",
                        cvv: 123
                )
        )
    }

    def "should approve a payment"() {
        when:
            Payment processedPayment = cardProcessorService.validateAndProcess(payment)
        then:
            1 * paymentGatewayClientMock.processCardPayment(payment) >> Optional.of(new Payment(status: AUTHORIZED))
            paymentRepository.findById(processedPayment.id).get().with {
                assert it.status == AUTHORIZED
            }
    }

    def "should decline a payment"() {
        when:
            Payment processedPayment = cardProcessorService.validateAndProcess(payment)
        then:
            1 * paymentGatewayClientMock.processCardPayment(payment) >> Optional.of(new Payment(status: DENIED))
            paymentRepository.findById(processedPayment.id).get().with {
                assert it.status == DENIED
            }
    }

    def "when receive external failure should redirect to fallback message"() {
        when:
            cardProcessorService.validateAndProcess(payment)
        then:
            1 * paymentGatewayClientMock.processCardPayment(payment) >> {
                throw new IOException()
            }
            def exception = thrown(ServiceUnavailableException)
            exception.message == "Serviço temporariamente indisponivel tente outro tipo de pagamento"
    }
}
