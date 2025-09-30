package com.example.e_commerce.produto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProdutoCadastroDTO {
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
    private String descricao;
    @NotBlank(message = "O preço é obrigatório.")
    private Double preco;
    @NotBlank(message = "A quantidade em estoque é obrigatória.")
    private Integer estoque;
}
