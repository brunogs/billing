package br.com.wirecard.billing.service.processor

import br.com.wirecard.billing.BillingApplicationTests
import br.com.wirecard.billing.domain.Buyer
import br.com.wirecard.billing.domain.Client
import br.com.wirecard.billing.domain.Payment
import br.com.wirecard.billing.domain.PaymentStatus
import br.com.wirecard.billing.domain.PaymentType
import br.com.wirecard.billing.repository.PaymentRepository
import org.springframework.beans.factory.annotation.Autowired

class BoletoProcessorServiceTest extends BillingApplicationTests {

    @Autowired
    private BoletoProcessorService boletoProcessorService

    @Autowired
    private PaymentRepository paymentRepository

    private Payment payment

    void setup() {
        payment = new Payment(
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

    def "should persist payment with boleto number"() {
        when:
            Payment processedPayment = boletoProcessorService.validateAndProcess(payment)
        then:
            paymentRepository.findById(processedPayment.id).get().with {
                assert it.boleto.barcode != null
                assert it.type == PaymentType.BOLETO
                assert it.status == PaymentStatus.PENDENT
            }
    }
}
