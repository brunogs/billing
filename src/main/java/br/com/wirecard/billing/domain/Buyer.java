package br.com.wirecard.billing.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class Buyer {

    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    @CPF
    @NotEmpty
    private String cpf;

}
