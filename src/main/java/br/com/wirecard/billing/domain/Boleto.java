package br.com.wirecard.billing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Boleto {

    private String barcode;

}
