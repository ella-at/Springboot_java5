package com.example.controllers;

import com.example.models.Brinquedo;
import com.example.services.BrinquedoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/brinquedos")
public class BrinquedoController {

    @Autowired
    private BrinquedoService brinquedoService;


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("brinquedo", new Brinquedo());
        return "brinquedos/create";
    }

    @PostMapping("/create")
    public String createBrinquedo(Brinquedo brinquedo, RedirectAttributes redirectAttributes) {
        brinquedoService.save(brinquedo);
        redirectAttributes.addFlashAttribute("success", "Brinquedo criado com sucesso!");
        return "redirect:/brinquedos/lista";
    }


    @GetMapping("/lista")
    public String filterBrinquedos(Model model,
                                   @RequestParam(required = false) String nome,
                                   @RequestParam(required = false) String tipo,
                                   @RequestParam(required = false) String estado,
                                   @RequestParam(required = false) Double precoMin,
                                   @RequestParam(required = false) Double precoMax,
                                   @RequestParam(required = false) Long id) {
        List<Brinquedo> brinquedos = brinquedoService.filterBrinquedos(nome, tipo, estado, precoMin, precoMax, id);
        model.addAttribute("brinquedos", brinquedos);
        return "brinquedos/lista";
    }

    @GetMapping("/{id}")
    public String listarBrinquedos(@PathVariable Long id, Model model) {
        try {
            Brinquedo brinquedo = brinquedoService.buscarPorId(id);
            model.addAttribute("brinquedo", brinquedo);
        } catch (Exception e) {
            model.addAttribute("error", "Erro: " + e.getMessage());
        }
        return "brinquedos/detail";
    }


    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Brinquedo brinquedo = brinquedoService.buscarPorId(id);
            model.addAttribute("brinquedo", brinquedo);
        } catch (Exception e) {
            model.addAttribute("error", "Erro: " + e.getMessage());
        }
        return "brinquedos/editar";
    }

    @PostMapping("/editar/{id}")
    public String updateBrinquedo(@PathVariable Long id, Brinquedo brinquedo, RedirectAttributes redirectAttributes) {
        try {
            brinquedo.setId(id);  // Ensure we are updating the correct entity
            brinquedoService.save(brinquedo);
            redirectAttributes.addFlashAttribute("success", "Brinquedo atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro: " + e.getMessage());
        }
        return "redirect:/brinquedos/lista";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBrinquedo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            brinquedoService.deletarPorId(id);
            redirectAttributes.addFlashAttribute("success", "Brinquedo deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro: " + e.getMessage());
        }
        return "redirect:/brinquedos/lista";
    }
}
