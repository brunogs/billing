package br.com.wirecard.billing.service.processor

import br.com.wirecard.billing.BillingApplicationTests
import br.com.wirecard.billing.domain.Buyer
import br.com.wirecard.billing.domain.Client
import br.com.wirecard.billing.domain.Payment
import br.com.wirecard.billing.domain.PaymentType
import br.com.wirecard.billing.exception.UnprocessableEntityException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

class PaymentProcessorServiceTest extends BillingApplicationTests {

    @Autowired
    private BoletoProcessorService boletoProcessorService

    private Payment validPayment

    void setup() {
        validPayment = new Payment(
                client: new Client(id: UUID.randomUUID().toString()),
                amount: BigDecimal.valueOf(42.0),
                type: PaymentType.BOLETO,
                buyer: new Buyer(
                        name: 'Buyer Test',
                        email: 'buyer@test.com',
                        cpf: '62745605569'
                )
        )
    }

    def "should validate client field"() {
        given:
            Payment paymentWithoutClient = validPayment.with {
                client = null
                it
            }
        when:
            boletoProcessorService.validate(paymentWithoutClient)
        then:
            def exception = thrown(UnprocessableEntityException)
            exception.message.contains("client must not be null")
    }

    def "should validate type field"() {
        given:
            Payment paymentWithoutType = validPayment.with {
                type = null
                it
            }
        when:
            boletoProcessorService.validate(paymentWithoutType)
        then:
            def exception = thrown(UnprocessableEntityException)
            exception.message.contains("type must not be null")
    }

    def "should validate buyer field"() {
        given:
            Payment paymentWithoutBuyer = validPayment.with {
                buyer = null
                it
            }
        when:
            boletoProcessorService.validate(paymentWithoutBuyer)
        then:
            def exception = thrown(UnprocessableEntityException)
            exception.message.contains("buyer must not be null")
    }

    def "should validate buyer details"() {
        given:
            Buyer invalidBuyer = new Buyer(
                    name: null,
                    email: 'fake',
                    cpf: '123'
            )
            Payment paymentWithInvalidBuyer = validPayment.with {
                buyer = invalidBuyer
                it
            }
        when:
            boletoProcessorService.validate(paymentWithInvalidBuyer)
        then:
            def exception = thrown(UnprocessableEntityException)
            exception.message.contains("buyer.name must not be empty")
            exception.message.contains("buyer.email must be a well-formed email address")
            exception.message.contains("buyer.cpf invalid Brazilian individual taxpayer registry number (CPF)")
    }

    @Unroll
    def "should validate amount field with value #invalidAmount"() {
        given:
            Payment paymentWithoutAmount = validPayment.with {
                amount = invalidAmount
                it
            }
        when:
            boletoProcessorService.validate(paymentWithoutAmount)
        then:
            def exception = thrown(UnprocessableEntityException)
            exception.message.contains("amount must not be null") || exception.message.contains("amount must be greater than or equal to 0.01")
        where:
            invalidAmount << [null, 0, -3.4]
    }
}
