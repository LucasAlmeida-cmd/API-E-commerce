package com.example.e_commerce.produto;

import com.example.e_commerce.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;


    // --- TESTE 1: CAMINHO FELIZ ---
    // ProdutoServiceTest.java

    @Test
    @DisplayName("Deve adicionar estoque a um produto existente com sucesso")
    void deveAdicionarEstoqueComSucesso() {
        Long produtoId = 1L;
        Integer quantidadeParaAdicionar = 10;

        Produto produtoDoBanco = new Produto(produtoId, "Coca-Cola", "Refrigerante", 10.0, 50);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoDoBanco));

        produtoService.adicionarEstoque(produtoId, quantidadeParaAdicionar);

        assertThat(produtoDoBanco.getEstoque()).isEqualTo(60); // 50 + 10 = 60
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar adicionar estoque a um produto inexistente")
    void deveLancarExcecaoAoAdicionarEstoqueProdutoInexistente() {
        Long produtoIdInexistente = 99L;
        Integer quantidadeParaAdicionar = 10;

        when(produtoRepository.findById(produtoIdInexistente)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            produtoService.adicionarEstoque(produtoIdInexistente, quantidadeParaAdicionar);
        });
        verify(produtoRepository, never()).save(any(Produto.class));
    }
}
