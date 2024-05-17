package org.JavviFdeez.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHasher {

    /**
     * @param password
     * @return El hash SHA-256 de la contraseña pasada como parámetro
     * @Author: JavviFdeez
     * Método para obtener el hash SHA-256 de una contraseña
     */
    public static String hashPassword(String password) {
        try {
            // Obtener una instancia de MessageDigest para SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Aplicar el algoritmo de hash a la contraseña
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convertir los bytes del hash a una representación en base64
            String hashedPassword = Base64.getEncoder().encodeToString(hashBytes);

            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}