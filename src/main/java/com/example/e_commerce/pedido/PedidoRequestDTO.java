package com.example.e_commerce.pedido;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {
    private Long usuarioId;
    private List<ItemPedidoRequestDTO> itens;
}