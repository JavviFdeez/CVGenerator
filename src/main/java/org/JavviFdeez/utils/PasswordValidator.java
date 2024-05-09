package org.JavviFdeez.utils;

public class PasswordValidator {

    /**
     * @param password
     * @return true si la contraseña es válida
     * @Author: JavviFdeez
     * Método para validar una contraseña
     */
    public static boolean isValidPassword(String password) {
        // ============================================================
        // Verificar que la contraseña tenga al menos 10 caracteres
        // ============================================================
        if (password.length() < 10) {
            return false;
        }

        // =====================================================================================================
        // Verificar que la contraseña contenga al menos una letra minúscula, una letra mayúscula y un número
        // =====================================================================================================
        // ============
        // Miniscula
        // ============
        boolean containsLowercase = false;
        // ============
        // Mayuscula
        // ============
        boolean containsUpperCase = false;
        // =========
        // Número
        // =========
        boolean containsDigit = false;

        // ===============================================================================================================
        // Recorrer la contraseña y verificar si contiene al menos una letra minúscula, una letra mayúscula y un número
        // ===============================================================================================================
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                containsLowercase = true;
            } else if (Character.isUpperCase(c)) {
                containsUpperCase = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            }
        }

        return containsLowercase && containsUpperCase && containsDigit;
    }
}

