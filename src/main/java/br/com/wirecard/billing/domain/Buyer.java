package br.com.wirecard.billing.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Buyer {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @CPF
    @NotNull
    private String cpf;

}
