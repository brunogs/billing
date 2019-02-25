package br.com.wirecard.billing.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Buyer {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String cpf;

}
