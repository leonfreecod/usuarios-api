package com.leonardo.usuarios.service;

import com.leonardo.usuarios.model.Usuario;
import com.leonardo.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    // Final garante que a dependência não mude após a criação
    private final UsuarioRepository usuarioRepository;

    // Injeção via construtor (melhor prática)
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        validarEmailUnico(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    // ReadOnly melhora a performance em bancos de dados de produção
    @Transactional(readOnly = true)
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        // Lógica de validação de e-mail isolada para limpeza de código
        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail())) {
            validarEmailUnico(usuarioAtualizado.getEmail());
        }

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setIdade(usuarioAtualizado.getIdade());

        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // metodo auxiliar privado para evitar repetição de código (DRY - Don't Repeat Yourself)
    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já cadastrado: " + email);
        }
    }
}