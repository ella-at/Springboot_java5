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

    // Verificar se o usuário já existe
    public boolean usuarioJaExiste(String username) {
        return usuarioRepository.findByUsername(username) != null;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    // Método para salvar o usuário com a senha criptografada
    public void salvarUsuario(Usuario usuario, String role) {
        // Criptografa a senha antes de salvar no banco de dados
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRole(role); // Define a role recebida (ADMIN ou USER)
        usuarioRepository.save(usuario);
    }

    // Validação de login
    public boolean validarLogin(String username, String senha) {
        // Busca o usuário pelo username no banco de dados
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            // Se o usuário não existir, retorna false
            return false;
        }

        // Verifica se a senha fornecida corresponde à senha criptografada no banco de dados
        return passwordEncoder.matches(senha, usuario.getSenha());
    }

}
