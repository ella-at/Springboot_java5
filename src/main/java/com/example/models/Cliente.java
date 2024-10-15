package com.example.models;

import com.example.conexao.Connection;
import com.example.security.RSAKeyGenerator;

import java.net.Socket;
import java.security.KeyPair;

public class Cliente {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 65432;
    private Connection connection;
    private KeyPair clientKeyPair;

    public Cliente() throws Exception {
        // Gera as chaves para o cliente
        this.clientKeyPair = RSAKeyGenerator.generateKeyPair();
    }

    // Conectar ao servidor
    public void conectar() throws Exception {
        Socket serverSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        System.out.println("Conectado ao servidor da loja de brinquedos!");
        connection = new Connection(serverSocket, clientKeyPair.getPublic(), clientKeyPair.getPrivate());
    }

    // Enviar mensagem ao servidor
    public void enviarMensagem(String mensagem) throws Exception {
        connection.sendMessage(mensagem, clientKeyPair.getPublic());
    }

    // Receber resposta do servidor
    public String receberResposta() throws Exception {
        return connection.receiveMessage();
    }

    // Fechar conexão
    public void fecharConexao() throws Exception {
        connection.closeConnection();
    }

    // Método principal para testar o cliente
    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente();
            cliente.conectar();

            // Simular login do usuário
            String username = "admin";
            String password = "admin123";
            String loginMessage = "LOGIN:" + username + ":" + password;
            cliente.enviarMensagem(loginMessage);

            // Receber a resposta do servidor
            String resposta = cliente.receberResposta();
            System.out.println("Resposta do servidor: " + resposta);

            cliente.fecharConexao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
