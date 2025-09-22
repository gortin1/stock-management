package com.api.stock_management.application.dto.seller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellerActivateRequestDTO {

    @NotBlank(message = "O campo celular não pode ficar em branco.")
    private String celular;

    @NotBlank(message = "O código de ativação não pode estar em branco.")
    private String codigoAtivacao;
}
