package com.example.controllers;

import com.example.models.Usuario;
import com.example.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        if (usuarioService.usuarioJaExiste(usuario.getUsername())) {
            model.addAttribute("erro", "O nome de usuário já está em uso.");
            return "usuarios/cadastro";
        }
        usuarioService.salvarUsuario(usuario);
        return "redirect:/login"; // Redireciona para a página de login após o cadastro
    }
}
