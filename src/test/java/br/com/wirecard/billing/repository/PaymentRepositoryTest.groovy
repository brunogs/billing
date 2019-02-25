package br.com.wirecard.billing.repository

import br.com.wirecard.billing.BillingApplicationTests
import br.com.wirecard.billing.domain.Boleto
import br.com.wirecard.billing.domain.Buyer
import br.com.wirecard.billing.domain.Client
import br.com.wirecard.billing.domain.Payment
import br.com.wirecard.billing.domain.PaymentType
import org.springframework.beans.factory.annotation.Autowired

class PaymentRepositoryTest extends BillingApplicationTests {

    @Autowired
    PaymentRepository paymentRepository

    def "should create a payment"() {
        given:
            def payment = new Payment(
                    client: new Client(id: UUID.randomUUID().toString()),
                    type: PaymentType.BOLETO,
                    buyer: new Buyer(
                            name: 'Buyer Test',
                            email: 'buyer@test.com',
                            cpf: '62745605569'
                    ),
                    boleto: new Boleto(
                            barcode: '12345'
                    )
            )
        when:
            Payment createdPayment = paymentRepository.save(payment)
        then:
            createdPayment.id != null
            paymentRepository.findById(createdPayment.id).get().with {
                assert it.client == payment.client
                assert it.type == payment.type
                assert it.buyer == payment.buyer
                assert it.boleto == payment.boleto
            }
    }
}
