package com.example.e_commerce.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario AdicionarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return usuario;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario AtualizarUsuario(Long id, Usuario dadosParaAtualizar) {
        Usuario usuarioDoBanco = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + id + " não encontrado."));
        Optional<Usuario> usuarioComMesmoEmail = usuarioRepository.findByEmail(dadosParaAtualizar.getEmail());
        if (usuarioComMesmoEmail.isPresent() && !usuarioComMesmoEmail.get().getId().equals(usuarioDoBanco.getId())) {
            throw new RuntimeException("O e-mail '" + dadosParaAtualizar.getEmail() + "' já está em uso por outro usuário.");
        }
        usuarioDoBanco.setNome(dadosParaAtualizar.getNome());
        usuarioDoBanco.setEmail(dadosParaAtualizar.getEmail());
        usuarioDoBanco.setEndereco(dadosParaAtualizar.getEndereco());
        return usuarioRepository.save(usuarioDoBanco);
    }

    public void RemoverUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }




}
