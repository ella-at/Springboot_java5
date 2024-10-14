package com.example.controllers;

import com.example.models.Brinquedo;
import com.example.repositories.BrinquedoRepository;
import com.example.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private BrinquedoRepository brinquedoRepository;

    @GetMapping("/{id}")
    public String exibirPaginaVenda(@PathVariable Long id, Model model) {
        Brinquedo brinquedo = brinquedoRepository.findById(id).orElse(null);
        model.addAttribute("brinquedo", brinquedo);
        return "venda";
    }

    @PostMapping("/{id}")
    public String realizarVenda(@PathVariable Long id,
                                @RequestParam int quantidade,
                                @RequestParam String formaPagamento,
                                @RequestParam String funcionario) {
        try {
            vendaService.realizarVenda(id, quantidade, formaPagamento, funcionario);
            return "redirect:/lista"; // Redireciona para a lista de brinquedos após a venda
        } catch (Exception e) {
            return "redirect:/venda/" + id + "?error=" + e.getMessage();
        }
    }
}
