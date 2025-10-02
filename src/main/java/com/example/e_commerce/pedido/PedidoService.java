package com.example.e_commerce.pedido;

import com.example.e_commerce.Status;
import com.example.e_commerce.exception.ResourceNotFoundException;
import com.example.e_commerce.produto.Produto;
import com.example.e_commerce.produto.ProdutoRepository;
import com.example.e_commerce.usuario.Usuario;
import com.example.e_commerce.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {



    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional
    public Pedido criarPedido(PedidoRequestDTO pedidoRequestDTO, Usuario usuarioLogado) {
        Pedido novoPedido = new Pedido();
        novoPedido.setUsuario(usuarioLogado);
        novoPedido.setInstanteDoPedido(LocalDateTime.now());
        novoPedido.setStatusDoPedido(Status.AGUARDANDO_PAGAMENTO);
        novoPedido.setItens(new ArrayList<>());

        for (ItemPedidoRequestDTO itemDTO : pedidoRequestDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto com id: " + itemDTO.getProdutoId() + ", não encontrado."));

            if (produto.getEstoque() < itemDTO.getQuantidade()) {
                throw new ResourceNotFoundException ("Estoque insuficiente para o produto: " + produto.getNome());
            }

            ItemPedido itemPedido = new ItemPedido(
                    itemDTO.getQuantidade(),
                    novoPedido,
                    produto
            );

            novoPedido.getItens().add(itemPedido);

            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());
            produtoRepository.save(produto);
        }

        return pedidoRepository.save(novoPedido);
    }


    public Optional<Pedido> buscarPorId(Long id) {
       return pedidoRepository.findById(id);
    }

    public List<Pedido> buscarPorUsuario(Usuario usuarioLogado) {
        return pedidoRepository.findByUsuarioId(usuarioLogado.getId());
    }

    public void apagarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }
}
