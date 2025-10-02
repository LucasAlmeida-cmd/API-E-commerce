package com.example.e_commerce.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemPedidoRequestDTO {
    private Long produtoId;
    private Integer quantidade;
}