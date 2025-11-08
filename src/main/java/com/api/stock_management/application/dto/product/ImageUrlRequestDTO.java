package com.api.stock_management.application.dto.product; // Ajuste o pacote conforme necessário

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageUrlRequestDTO {

    @NotBlank(message = "A URL da imagem não pode estar vazia")
    private String imageUrl;
}
