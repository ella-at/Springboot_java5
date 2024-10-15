package com.example.client;

import com.example.conexao.Connection;
import com.example.security.RSAKeyGenerator;

import java.net.*;
import java.security.*;

public class Cliente {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 65432;

    public static void main(String[] args) throws Exception {
        // Gera as chaves do cliente
        KeyPair clientKeyPair = RSAKeyGenerator.generateKeyPair();

        // Conecta ao servidor
        Socket serverSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        System.out.println("Conectado ao servidor!");

        // Cria a conexão e manipula a troca de mensagens
        Connection connection = new Connection(serverSocket, clientKeyPair.getPublic(), clientKeyPair.getPrivate());

        // Envia mensagem criptografada ao servidor
        connection.sendMessage("Olá, servidor!", connection.getPublicKey());  // Lombok gera o getter

        // Recebe a resposta criptografada do servidor
        String response = connection.receiveMessage();
        System.out.println("Resposta do servidor: " + response);

        connection.closeConnection();
    }
}