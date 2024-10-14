package com.example.services;

import com.example.models.Usuario;
import com.example.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    // Método para salvar o usuário com a senha criptografada
    public void salvarUsuario(Usuario usuario) {
        // Criptografa a senha antes de salvar no banco de dados
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }

    // Verificar se o usuário já existe
    public boolean usuarioJaExiste(String username) {
        return usuarioRepository.findByUsername(username) != null;
    }
}
