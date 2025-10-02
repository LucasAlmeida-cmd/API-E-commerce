package com.example.e_commerce.produto;

import com.example.e_commerce.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criarProduto(ProdutoCadastroDTO produto) {
        Produto produtoNovo = new Produto();
        produtoNovo.setEstoque(produto.getEstoque());
        produtoNovo.setDescricao(produto.getDescricao());
        produtoNovo.setNome(produto.getNome());
        produtoNovo.setPreco(produto.getPreco());
        produtoRepository.save(produtoNovo);
        return produtoNovo;
    }

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizarProduto(Long id, Produto produto) {
        Produto produtoAtualizado = produtoRepository.findById(id).orElseThrow(()-> new RuntimeException("Produto com "+ id+ ", não encontrado."));
        produtoAtualizado.setNome(produto.getNome());
        produtoAtualizado.setPreco(produto.getPreco());
        produtoAtualizado.setDescricao(produto.getDescricao());
        return produtoRepository.save(produtoAtualizado);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    @Transactional
    public void adicionarEstoque(Long id, Integer quantidade) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id: " + id + " não encontrado."));
        produto.setEstoque(produto.getEstoque() + quantidade);
    }
}
