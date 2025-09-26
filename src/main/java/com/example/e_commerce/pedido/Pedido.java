package com.example.e_commerce.pedido;

import com.example.e_commerce.Status;
import com.example.e_commerce.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime instanteDoPedido;

    @Enumerated(EnumType.STRING)
    private Status statusDoPedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonManagedReference
    private Usuario usuario;


    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    public Double getValorTotal() {
        return itens.stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();
    }
}