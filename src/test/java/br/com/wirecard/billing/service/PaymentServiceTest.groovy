package br.com.wirecard.billing.service

import br.com.wirecard.billing.domain.Payment
import br.com.wirecard.billing.domain.PaymentType
import br.com.wirecard.billing.service.processor.BoletoProcessorService
import br.com.wirecard.billing.service.processor.CardProcessorService
import br.com.wirecard.billing.service.processor.PaymentProcessorService
import spock.lang.Specification

import javax.validation.Validator

import static java.util.Collections.emptySet

class PaymentServiceTest extends Specification {

    private PaymentService paymentService
    private PaymentProcessorService boletoProcessorServiceSpy
    private PaymentProcessorService cardProcessorServiceSpy
    private Validator validatorMock

    void setup() {
        validatorMock = Mock(Validator)
        boletoProcessorServiceSpy = Spy(BoletoProcessorService, constructorArgs: [validatorMock])
        cardProcessorServiceSpy = Spy(CardProcessorService, constructorArgs: [validatorMock])
        paymentService = new PaymentService([boletoProcessorServiceSpy, cardProcessorServiceSpy])

        validatorMock.validate(*_) >> emptySet()
    }

    def "given a payment with type BOLETO should process with BOLETO service"() {
        given:
            def payment = new Payment(type: PaymentType.BOLETO)
        when:
            paymentService.process(payment)
        then:
            1 * boletoProcessorServiceSpy.validateAndProcess(payment)
    }

    def "given a payment with type CREDIT_CARD should process with CREDIT_CARD service"() {
        given:
            def payment = new Payment(type: PaymentType.CREDIT_CARD)
        when:
            paymentService.process(payment)
        then:
            1 * cardProcessorServiceSpy.validateAndProcess(payment)
    }

    def "givan an invalid type should do nothing"() {
        given:
            def payment = new Payment()
        when:
            paymentService.process(payment)
        then:
            0 * boletoProcessorServiceSpy.validateAndProcess(payment)
            0 * cardProcessorServiceSpy.validateAndProcess(payment)
            thrown(IllegalArgumentException)
    }
}
