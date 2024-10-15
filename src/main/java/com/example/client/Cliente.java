package com.example.client;

import com.example.conexao.Connection;
import com.example.security.RSAKeyGenerator;
import org.springframework.stereotype.Service;

import java.net.Socket;
import java.security.KeyPair;


public class Cliente {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 65432;
    private Connection connection;
    private KeyPair clientKeyPair;

    public static void main(String[] args) throws Exception {
        // Simular um login do usuário
        String username = "admin";
        String password = "admin123";

        // Gera as chaves do cliente
        KeyPair clientKeyPair = RSAKeyGenerator.generateKeyPair();

        // Conecta ao servidor
        Socket serverSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        System.out.println("Conectado ao servidor da loja de brinquedos!");

        // Cria a conexão e manipula a troca de mensagens
        Connection connection = new Connection(serverSocket, clientKeyPair.getPublic(), clientKeyPair.getPrivate());


        // Enviar as credenciais de login (usuário e senha) para o servidor
        String loginMessage = "LOGIN:" + username + ":" + password;
        connection.sendMessage(loginMessage, connection.getPublicKey());

        // Receber a resposta do servidor (se o login foi bem-sucedido ou falhou)
        String response = connection.receiveMessage();
        System.out.println("Resposta do servidor: " + response);

        // Lógica para continuar ou encerrar a sessão baseado na resposta
        if (response.equals("LOGIN SUCCESS")) {
            System.out.println("Login bem-sucedido! Acessando sistema...");

        } else {
            System.out.println("Login falhou! Verifique suas credenciais.");
        }

        // Fechar a conexão
        connection.closeConnection();
    }

    public void enviarMensagem(String mensagem) throws Exception {
        connection.sendMessage(mensagem, clientKeyPair.getPublic());
    }
    public String receberResposta() throws Exception {
        return connection.receiveMessage();
    }


    // Método para realizar login
    public boolean realizarLogin(String username, String password) throws Exception {
        String loginMessage = "LOGIN:" + username + ":" + password;
        enviarMensagem(loginMessage);

        String resposta = receberResposta();
        System.out.println("Resposta do servidor: " + resposta);

        return "LOGIN SUCCESS".equals(resposta);
    }

    // Método para realizar uma venda
    public void realizarVenda(Long brinquedoId, int quantidade, String formaPagamento, String funcionario) throws Exception {
        String vendaMessage = "venda:" + brinquedoId + "," + quantidade + "," + formaPagamento + "," + funcionario;
        enviarMensagem(vendaMessage);
        String resposta = receberResposta();
        System.out.println("Resposta do servidor sobre a venda: " + resposta);
    }

    // Método para verificar o estoque de um brinquedo
    public void verificarEstoque(Long brinquedoId) throws Exception {
        String estoqueMessage = "estoque:" + brinquedoId;
        enviarMensagem(estoqueMessage);
        String resposta = receberResposta();
        System.out.println("Estoque disponível: " + resposta);
    }

    // Fechar conexão
    public void fecharConexao() throws Exception {
        connection.closeConnection();
    }
}