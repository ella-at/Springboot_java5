package com.example.server;

import com.example.conexao.Connection;
import com.example.models.Brinquedo;
import com.example.services.BrinquedoService;
import com.example.services.UsuarioService;
import com.example.services.VendaService;
import com.example.security.RSAKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;

public class SocketServer {
    private static final int PORT = 65432;

    @Autowired
    private BrinquedoService brinquedoService;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private UsuarioService usuarioService;

    private KeyPair serverKeyPair;

    @EventListener(ContextRefreshedEvent.class)
    public void startSocketServer() throws Exception {
        // Gera as chaves do servidor para a comunicação segura
        KeyPair serverKeyPair = RSAKeyGenerator.generateKeyPair();

        // Inicia o servidor socket
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor socket escutando na porta: " + PORT);


        while (true) {
            // Aceita a conexão do cliente
            Socket clientSocket = serverSocket.accept();
            System.out.println("Conexão aceita de: " + clientSocket.getInetAddress());

            // Cria a conexão com o cliente e faz a troca de mensagens
            Connection connection = new Connection(clientSocket, serverKeyPair.getPublic(), serverKeyPair.getPrivate());

            // Recebe a mensagem criptografada do cliente
            String receivedMessage = connection.receiveMessage();
            System.out.println("Mensagem recebida do cliente: " + receivedMessage);


            String resposta = processarMensagem(receivedMessage);

            // Responde ao cliente
            connection.sendMessage(resposta, connection.getPublicKey());
            connection.closeConnection();
        }
    }

    // Processar a mensagem recebida do cliente e integrar com o sistema da loja
    private String processarMensagem(String mensagem) {
        try {
            if (mensagem.startsWith("LOGIN")) {
                // Processa o login
                String[] parts = mensagem.split(":");
                String username = parts[1];
                String password = parts[2];

                // Validar login com o UsuarioService
                boolean loginValido = usuarioService.validarLogin(username, password);
                if (loginValido) {
                    return "LOGIN SUCCESS";
                } else {
                    return "LOGIN FAILED";
                }
            } else if (mensagem.startsWith("venda")) {
                    // Exemplo de comando: "venda:brinquedoId,quantidade,formaPagamento,funcionario"
                    String[] partes = mensagem.split(":")[1].split(",");
                    Long brinquedoId = Long.parseLong(partes[0]);
                    int quantidade = Integer.parseInt(partes[1]);
                    String formaPagamento = partes[2];
                    String funcionario = partes[3];

                    // Processa a venda com os dados fornecidos
                    vendaService.realizarVenda(brinquedoId, quantidade, formaPagamento, funcionario);
                    return "Venda realizada com sucesso!";
                } else if (mensagem.startsWith("estoque")) {
                    // Exemplo de comando: "estoque:id"
                    Long id = Long.parseLong(mensagem.split(":")[1]);

                    // Busca e retorna os detalhes do brinquedo
                    Brinquedo brinquedo = brinquedoService.buscarPorId(id);
                    return "Estoque do brinquedo: " + brinquedo.getQuantidade();
                } else {
                    return "Comando não reconhecido: " + mensagem;
                }
            } catch (Exception e) {
                return "Erro ao processar a mensagem: " + e.getMessage();
        }
    }
}