package com.example.controllers;

import com.example.models.Usuario;
import com.example.services.UsuarioService;
import com.example.services.ComunicacaoService;
import com.example.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/templates")
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunicacaoService comunicacaoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Exibir a página de login
    @GetMapping
    public String exibirPaginaLogin() {
        return "entrar";
    }


    @PostMapping
    public String login(@RequestParam String username, @RequestParam String password, Model model) {

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario != null && passwordEncoder.matches(password, usuario.getSenha())) {

            // Se o login for bem-sucedido, redireciona para a página inicial
            if (usuario.getRole().equals("ADMIN")) {
                return "redirect:/index";  // Redirecionar para a página inicial do sistema
            } else if (usuario.getRole().equals("USER")) {
                return "redirect:/templates/lista";  // Redirecionar para a lista de brinquedos
            }
        } else {
            // Se o login falhar, retorna para a página de login com uma mensagem de erro
            model.addAttribute("erro", "Nome de usuário ou senha incorretos.");
            return "entrar";
        }

        return "entrar";
    }
}