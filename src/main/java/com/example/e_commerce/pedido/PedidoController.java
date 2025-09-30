package com.example.e_commerce.pedido;

import com.example.e_commerce.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;


    @PostMapping
    public ResponseEntity<Pedido> criarPedido(
            @RequestBody @Valid PedidoRequestDTO pedidoRequestDTO,
            Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Pedido novoPedido = pedidoService.criarPedido(pedidoRequestDTO, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Pedido>> buscarPedidoPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }
    @GetMapping
    public ResponseEntity<List<Pedido>> buscarPedidos() {
        return ResponseEntity.ok(pedidoService.buscarTodos());
    }

    @GetMapping("/meus")
    public ResponseEntity<List<Pedido>> buscarPedidosPorUsuario(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        List<Pedido> pedidos = pedidoService.buscarPorUsuario(usuarioLogado);
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pedido> deletarPedidoPorId(@PathVariable Long id) {
        pedidoService.apagarPedido(id);
        return ResponseEntity.noContent().build();
    }

}