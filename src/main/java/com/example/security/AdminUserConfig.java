package com.example.security;

import com.example.models.Usuario;
import com.example.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            // Verifica se o usuário admin já existe
            if (usuarioRepository.findByUsername("admin") == null) {
                // Cria o usuário admin com senha e role ADMIN
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setSenha(passwordEncoder.encode("admin123")); // Criptografa a senha
                admin.setRole("ADMIN");

                // Salva o usuário no banco de dados
                usuarioRepository.save(admin);

                System.out.println("Usuário admin criado com sucesso!");
            } else {
                System.out.println("Usuário admin já existe.");
            }

            // Verifica se o usuário user já existe
            if (usuarioRepository.findByUsername("user") == null) {
                // Cria o usuário user com senha e role USER
                Usuario user = new Usuario();
                user.setUsername("user");
                user.setSenha(passwordEncoder.encode("user123"));
                user.setRole("USER");

                // Salva o usuário no banco de dados
                usuarioRepository.save(user);

                System.out.println("Usuário user criado com sucesso!");
            } else {
                System.out.println("Usuário user já existe.");
            }
        };
    }
}
