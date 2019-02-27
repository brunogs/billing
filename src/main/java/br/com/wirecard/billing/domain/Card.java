package br.com.wirecard.billing.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Card {

    @NotNull
    private String holderName;

    @NotNull
    private String number;

    @NotNull
    private String expirationDate;

    private int cvv;

}
