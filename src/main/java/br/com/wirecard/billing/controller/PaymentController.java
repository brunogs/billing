package br.com.wirecard.billing.controller;

import br.com.wirecard.billing.domain.Payment;
import br.com.wirecard.billing.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Payment create(@RequestBody @Valid Payment payment) {
        log.info("create payment={}", payment);
        return paymentService.process(payment);
    }

    @GetMapping("/{paymentId}")
    public Payment getById(@PathVariable String paymentId) {
        log.info("getting payment id={}", paymentId);
        return paymentService.getById(paymentId);
    }

}
