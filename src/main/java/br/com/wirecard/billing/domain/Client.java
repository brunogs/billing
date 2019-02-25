package br.com.wirecard.billing.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Client {

    @NotNull
    private String id;

}
