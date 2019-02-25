package br.com.wirecard.billing.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document("payments")
public class Payment {

    @Setter(AccessLevel.NONE)
    private String id;

    @NotNull
    private PaymentType type;

    @NotNull
    private Client client;

    private Buyer buyer;

    private Card card;

    private Boleto boleto;

}
