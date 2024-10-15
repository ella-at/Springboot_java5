package com.example.conexao;

import lombok.Getter;
import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;


public class Connection {

    @Getter
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Connection(Socket socket, PublicKey publicKey, PrivateKey privateKey) throws IOException {
        this.socket = socket;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    // Método para enviar mensagem criptografada
    public void sendMessage(String message, PublicKey recipientPublicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, recipientPublicKey);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());
        outputStream.writeObject(encryptedMessage);
        outputStream.flush();
    }

    // Método para receber mensagem criptografada e descriptografar
    public String receiveMessage() throws Exception {
        byte[] encryptedMessage = (byte[]) inputStream.readObject();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
        return new String(decryptedMessage);
    }



    public void closeConnection() throws IOException {
        socket.close();
    }

}
