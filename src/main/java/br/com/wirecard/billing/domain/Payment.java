package br.com.wirecard.billing.domain;

import br.com.wirecard.billing.domain.group.CreditCard;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Document("payments")
public class Payment {

    @Setter(AccessLevel.NONE)
    private String id;

    @NotNull
    private PaymentType type;

    @Valid
    @NotNull
    private Client client;

    @Valid
    @NotNull
    private Buyer buyer;

    @NotNull(groups = CreditCard.class)
    private Card card;

    private Boleto boleto;

}
