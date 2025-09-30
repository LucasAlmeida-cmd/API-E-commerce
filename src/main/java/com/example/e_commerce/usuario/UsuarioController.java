package com.example.e_commerce.usuario;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO dados) {
        Usuario novoUsuario = usuarioService.AdicionarUsuario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
    @GetMapping()
    public ResponseEntity<List<Usuario>> getUsuarios(){
        List<Usuario> usuarios =  usuarioService.findAll();
        return ResponseEntity.ok().body(usuarios);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> getUsuario(@PathVariable Long id){
        Optional<Usuario> user = usuarioService.findById(id);
        return ResponseEntity.ok().body(user);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Usuario usuario){
        usuarioService.AtualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioService.AtualizarUsuario(id, usuario));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        usuarioService.RemoverUsuario(id);
        return ResponseEntity.noContent().build();
    }


}
