package com.example.e_commerce.pedido;

import lombok.Data;

@Data
public class ItemPedidoRequestDTO {
    private Long produtoId;
    private Integer quantidade;
}