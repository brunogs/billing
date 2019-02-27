package br.com.wirecard.billing.service

import br.com.wirecard.billing.client.PaymentGatewayClient
import br.com.wirecard.billing.domain.Payment
import br.com.wirecard.billing.domain.PaymentType
import br.com.wirecard.billing.exception.UnprocessableEntityException
import br.com.wirecard.billing.repository.PaymentRepository
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
    private PaymentRepository paymentRepositoryMock
    private PaymentGatewayClient paymentGatewayClientMock

    void setup() {
        validatorMock = Mock(Validator)
        paymentRepositoryMock = Mock(PaymentRepository)
        paymentGatewayClientMock = Mock(PaymentGatewayClient)
        boletoProcessorServiceSpy = Spy(BoletoProcessorService, constructorArgs: [validatorMock, paymentRepositoryMock])
        cardProcessorServiceSpy = Spy(CardProcessorService, constructorArgs: [validatorMock, paymentGatewayClientMock, paymentRepositoryMock])
        paymentService = new PaymentService([boletoProcessorServiceSpy, cardProcessorServiceSpy], paymentRepositoryMock)

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
            1 * paymentGatewayClientMock.processCardPayment(_) >> Optional.empty()
            1 * cardProcessorServiceSpy.validateAndProcess(payment)
    }

    def "given an invalid type should do nothing"() {
        given:
            def payment = new Payment()
        when:
            paymentService.process(payment)
        then:
            0 * boletoProcessorServiceSpy.validateAndProcess(payment)
            0 * cardProcessorServiceSpy.validateAndProcess(payment)
            thrown(UnprocessableEntityException)
    }
}
