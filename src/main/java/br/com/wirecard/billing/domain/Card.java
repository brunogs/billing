package br.com.wirecard.billing.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.YearMonth;

@Data
public class Card {

    @NotNull
    private String holderName;

    @NotNull
    private String number;

    @NotNull
    private YearMonth expirationDate;

    private int cvv;

}
