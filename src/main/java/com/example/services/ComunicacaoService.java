package com.example.services;

import com.example.conexao.Connection;
import com.example.security.RSAKeyGenerator;
import org.springframework.stereotype.Service;

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

    public ComunicacaoService() throws Exception {
        // Gera chaves para servidor e cliente
        this.serverKeyPair = RSAKeyGenerator.generateKeyPair();
        this.clientKeyPair = RSAKeyGenerator.generateKeyPair();
    }

    // Inicia o servidor e espera uma conexão
    public String startServer() throws Exception {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        Socket clientSocket = serverSocket.accept();  // Aguarda cliente conectar

        connection = new Connection(clientSocket, serverKeyPair.getPublic(), serverKeyPair.getPrivate());
        String receivedMessage = connection.receiveMessage();  // Recebe a mensagem do cliente

        serverSocket.close();
        return receivedMessage;
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
