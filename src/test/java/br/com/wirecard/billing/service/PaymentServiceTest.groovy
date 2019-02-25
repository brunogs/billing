package br.com.wirecard.billing.service

import br.com.wirecard.billing.domain.Payment
import br.com.wirecard.billing.domain.PaymentType
import br.com.wirecard.billing.service.processor.BoletoProcessorService
import br.com.wirecard.billing.service.processor.CardProcessorService
import br.com.wirecard.billing.service.processor.PaymentProcessorService
import spock.lang.Specification

class PaymentServiceTest extends Specification {

    private PaymentService paymentService
    private PaymentProcessorService boletoProcessorServiceSpy
    private PaymentProcessorService cardProcessorServiceSpy


    void setup() {
        boletoProcessorServiceSpy = Spy(BoletoProcessorService)
        cardProcessorServiceSpy = Spy(CardProcessorService)
        paymentService = new PaymentService([boletoProcessorServiceSpy, cardProcessorServiceSpy])
    }

    def "given a payment with type BOLETO should process with BOLETO service"() {
        given:
            def payment = new Payment(type: PaymentType.BOLETO)
        when:
            paymentService.proccess(payment)
        then:
            1 * boletoProcessorServiceSpy.process(payment)
    }

    def "given a payment with type CREDIT_CARD should process with CREDIT_CARD service"() {
        given:
            def payment = new Payment(type: PaymentType.CREDIT_CARD)
        when:
            paymentService.proccess(payment)
        then:
            1 * cardProcessorServiceSpy.process(payment)
    }

    def "givan an invalid type should do nothing"() {
        given:
            def payment = new Payment()
        when:
            paymentService.proccess(payment)
        then:
            0 * boletoProcessorServiceSpy.process(payment)
            0 * cardProcessorServiceSpy.process(payment)
            thrown(IllegalArgumentException)
    }
}
