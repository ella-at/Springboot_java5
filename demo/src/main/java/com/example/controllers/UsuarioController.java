package com.example.controllers;

import com.example.models.Usuario;
import com.example.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Exibir o formulário de cadastro de usuário
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/cadastro";
    }

    // Salvar o novo usuário no banco de dados
    @PostMapping("/cadastro")
    public String salvarUsuario(@ModelAttribute Usuario usuario, Model model) {
        // Verifica se o nome de usuário já existe no banco
        if (usuarioService.usuarioJaExiste(usuario.getUsername())) {
            model.addAttribute("erro", "O nome de usuário já está em uso.");
            return "usuarios/cadastro";  // Retorna à página de cadastro se houver erro
        }

        // Salva o usuário com a senha criptografada
        usuarioService.salvarUsuario(usuario);

        // Redireciona para a página de login após o cadastro bem-sucedido
        return "redirect:/login"; // Redireciona para a página de login após o cadastro
    }
}
