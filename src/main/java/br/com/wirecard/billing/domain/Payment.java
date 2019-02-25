package br.com.wirecard.billing.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("payments")
public class Payment {

    @Setter(AccessLevel.NONE)
    private String id;
    private PaymentType type;

}
