package com.api.stock_management.domain.dto.seller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellerRequestDTO {

    @NotBlank(message = "O nome não pode ficar em branco.")
    private String nome;

    @NotBlank(message = "O cnpj não pode ficar em branco.")
    private String cnpj;

    @NotBlank(message = "O Email não pode ficar em branco.")
    @Email(message = "Formato de email invalido")
    private String email;

    @NotBlank(message = "O campo de celular não deve ficar em branco.")
    private String celular;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotBlank
    private String codigoAtivacao;
}
