package com.example.e_commerce.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> salvarProduto(@RequestBody Produto produto){
        return ResponseEntity.ok().body(produtoService.criarProduto(produto));
    }

    @GetMapping
    public List<Produto> listarProdutos(){
        return produtoService.listarTodosProdutos();
    }
    @GetMapping("/{id}")
    public Optional<Produto> buscarProduto(@PathVariable Long id){
        return produtoService.buscarPorId(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto){
        return ResponseEntity.ok().body(produtoService.atualizarProduto(id,produto));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<Void> adicionarEstoque(@PathVariable Long id, @RequestParam Integer quantidade) {
        produtoService.adicionarEstoque(id, quantidade);
        return ResponseEntity.ok().build();
    }

}
