package com.example.controllers;

import com.example.services.ComunicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comunicacao")
public class ComunicacaoController {
    @Autowired
    private ComunicacaoService comunicacaoService;

    // Endpoint para iniciar o servidor e receber mensagens do cliente
    @GetMapping("/server")
    public String startServer() {
        try {
            return "Mensagem recebida do cliente: " + comunicacaoService.startServer();
        } catch (Exception e) {
            return "Erro ao iniciar o servidor: " + e.getMessage();
        }
    }

    // Endpoint para o cliente enviar uma mensagem ao servidor
    @PostMapping("/client")
    public String startClient(@RequestParam String message) {
        try {
            return "Resposta do servidor: " + comunicacaoService.startClient(message);
        } catch (Exception e) {
            return "Erro ao conectar com o servidor: " + e.getMessage();
        }
    }
}
