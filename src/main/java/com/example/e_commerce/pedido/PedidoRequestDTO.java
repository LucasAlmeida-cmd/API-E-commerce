package com.example.e_commerce.pedido;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    private List<ItemPedidoRequestDTO> itens;
}