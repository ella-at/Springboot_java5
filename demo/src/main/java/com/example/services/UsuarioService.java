package com.example.services;

import com.example.models.Usuario;
import com.example.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para salvar um usuário no banco de dados
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Verificar se o usuário já existe
    public boolean usuarioJaExiste(String username) {
        return usuarioRepository.findByUsername(username) != null;
    }
}
