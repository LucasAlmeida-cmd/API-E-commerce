package com.example.e_commerce.produto;


import com.example.e_commerce.Status;
import com.example.e_commerce.exception.ResourceNotFoundException;
import com.example.e_commerce.pedido.*;
import com.example.e_commerce.usuario.Usuario;
import com.example.e_commerce.usuario.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {


    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("Deve criar um pedido com sucesso quando todos os dados são válidos e há estoque suficiente")
    void adicionandoPedido(){

        ItemPedidoRequestDTO item1DTO = new ItemPedidoRequestDTO(1L, 5);
        ItemPedidoRequestDTO item2DTO = new ItemPedidoRequestDTO(2L, 2);

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        pedidoRequestDTO.setItens(List.of(item1DTO, item2DTO));

        Produto produto1 = new Produto(1L, "Produto A", "Desc A", 10.0, 100); // Estoque de 100
        Produto produto2 = new Produto(2L, "Produto B", "Desc B", 25.0, 100); // Estoque de 100

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto1));
        when(produtoRepository.findById(2L)).thenReturn(Optional.of(produto2));

        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Usuario usuario = new Usuario();
        long idUsuario = 1L;
        usuario.setId(idUsuario);

        Pedido pedidoCriado = pedidoService.criarPedido(pedidoRequestDTO, usuario);

        assertThat(pedidoCriado).isNotNull();
        assertThat(pedidoCriado.getUsuario()).isEqualTo(usuario);
        assertThat(pedidoCriado.getStatusDoPedido()).isEqualTo(Status.AGUARDANDO_PAGAMENTO);
        assertThat(pedidoCriado.getItens()).hasSize(2);

        assertThat(pedidoCriado.getValorTotal()).isEqualTo(100.0);

        assertThat(produto1.getEstoque()).isEqualTo(95); // 100 - 5
        assertThat(produto2.getEstoque()).isEqualTo(98); // 100 - 2

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).findById(2L);
        verify(produtoRepository, times(2)).save(any(Produto.class)); // Chamado uma vez para cada produto
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }


    @Test
    @DisplayName("Deve lançar ResourceNotFoundException se um dos produtoId na requisição não existir no banco")
    void erroAoAdicionarPedido() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        ItemPedidoRequestDTO item1DTO = new ItemPedidoRequestDTO(1L, 5);
        ItemPedidoRequestDTO item2DTO = new ItemPedidoRequestDTO(3L, 2);
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        pedidoRequestDTO.setItens(List.of(item1DTO, item2DTO));

        Produto produto1 = new Produto(1L, "Produto A", "Desc A", 10.0, 100);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto1));
        when(produtoRepository.findById(3L)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.criarPedido(pedidoRequestDTO, usuario);
        });

        verify(pedidoRepository, never()).save(any());
    }


}
