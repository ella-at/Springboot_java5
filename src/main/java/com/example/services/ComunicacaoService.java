package com.example.services;

import com.example.conexao.Connection;
import com.example.models.Brinquedo;
import com.example.security.RSAKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.net.Socket;
import java.net.ServerSocket;
import java.security.KeyPair;

@Service
public class ComunicacaoService {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 65432;
    private Connection connection;
    private KeyPair serverKeyPair;
    private KeyPair clientKeyPair;

    @Autowired
    private VendaService vendaService;  // Serviço de vendas da loja
    @Autowired
    private BrinquedoService brinquedoService;  // Serviço de brinquedos
    @Autowired
    private UsuarioService usuarioService;  // Serviço de usuários (login)

    public ComunicacaoService() throws Exception {
        // Gera chaves para servidor e cliente
        this.serverKeyPair = RSAKeyGenerator.generateKeyPair();
        this.clientKeyPair = RSAKeyGenerator.generateKeyPair();
    }

    // Inicia o servidor automaticamente quando o sistema sobe
    @PostConstruct
    public void initServer() throws Exception {
        new Thread(() -> {
            try {
                startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Meodo para iniciar o servidor
    public String startServer() throws Exception {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        Socket clientSocket = serverSocket.accept();

        connection = new Connection(clientSocket, serverKeyPair.getPublic(), serverKeyPair.getPrivate());
        String receivedMessage = connection.receiveMessage();

        // Processar a mensagem recebida e conecta
        String response = processarMensagem(receivedMessage);

        // Enviar a resposta de volta ao cliente
        connection.sendMessage(response, connection.getPublicKey());

        serverSocket.close();
        return receivedMessage;  // Retorna a mensagem recebida ou a resposta
    }

    // Processar a mensagem recebida e realizar ações no sistema da loja de brinquedos
    private String processarMensagem(String mensagem) {
        try {
            if (mensagem.startsWith("LOGIN")) {
                // Exemplo de mensagem: "LOGIN:username:password"
                String[] parts = mensagem.split(":");
                String username = parts[1];
                String password = parts[2];

                // Valida o login no sistema usando UsuarioService
                boolean loginValido = usuarioService.validarLogin(username, password);
                if (loginValido) {
                    return "LOGIN SUCCESS";
                } else {
                    return "LOGIN FAILED";
                }
            } else if (mensagem.startsWith("VENDA")) {
                // Exemplo de mensagem: "VENDA:brinquedoId,quantidade,formaPagamento,funcionario"
                String[] parts = mensagem.split(":")[1].split(",");
                Long brinquedoId = Long.parseLong(parts[0]);
                int quantidade = Integer.parseInt(parts[1]);
                String formaPagamento = parts[2];
                String funcionario = parts[3];

                // Realiza a venda no sistema usando VendaService
                vendaService.realizarVenda(brinquedoId, quantidade, formaPagamento, funcionario);
                return "VENDA SUCCESS";
            } else if (mensagem.startsWith("ESTOQUE")) {
                // Exemplo de mensagem: "ESTOQUE:brinquedoId"
                Long brinquedoId = Long.parseLong(mensagem.split(":")[1]);

                // Verifica o estoque no sistema usando BrinquedoService
                Brinquedo brinquedo = brinquedoService.buscarPorId(brinquedoId);
                return "Estoque disponível: " + brinquedo.getQuantidade();
            } else {
                return "Comando não reconhecido: " + mensagem;
            }
        } catch (Exception e) {
            return "Erro ao processar a mensagem: " + e.getMessage();
        }
    }

    // Inicia o cliente e conecta ao servidor
    public String startClient(String message) throws Exception {
        Socket serverSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        connection = new Connection(serverSocket, clientKeyPair.getPublic(), clientKeyPair.getPrivate());

        connection.sendMessage(message, serverKeyPair.getPublic());  // Envia mensagem criptografada
        String response = connection.receiveMessage();  // Recebe a resposta do servidor

        serverSocket.close();
        return response;
    }
}
