package com.example.server;

import com.example.conexao.Connection;
import com.example.security.RSAKeyGenerator;

import java.net.*;
import java.security.*;

public class Server {
    private static final int PORT = 65432;

    public static void main(String[] args) throws Exception {
        // Gera as chaves do servidor
        KeyPair serverKeyPair = RSAKeyGenerator.generateKeyPair();

        // Inicia o servidor
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor escutando na porta: " + PORT);

        while (true) {
            // Aceita a conexão do cliente
            Socket clientSocket = serverSocket.accept();
            System.out.println("Conexão aceita de: " + clientSocket.getInetAddress());

            // Cria a conexão e manipula a troca de mensagens
            Connection connection = new Connection(clientSocket, serverKeyPair.getPublic(), serverKeyPair.getPrivate());

            // Recebe a mensagem criptografada do cliente
            String receivedMessage = connection.receiveMessage();
            System.out.println("Mensagem recebida do cliente: " + receivedMessage);

            // Responde ao cliente
            connection.sendMessage("Olá, cliente!", connection.getPublicKey());
            connection.closeConnection();
        }
    }
}
