package com.example.e_commerce.produto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> salvarProduto(@RequestBody ProdutoCadastroDTO produto){
        return ResponseEntity.ok().body(produtoService.criarProduto(produto));
    }

    @GetMapping
    public Page<Produto> listarProdutos(
            @PageableDefault(size = 12, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable){
        return produtoService.listarTodosProdutos(pageable);
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
