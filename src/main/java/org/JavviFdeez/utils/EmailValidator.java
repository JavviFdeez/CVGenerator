package org.JavviFdeez.utils;

import java.util.regex.*;

public class EmailValidator {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:gmail\\.com|yahoo\\.com|outlook\\.com)$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    // ==============================
    // Método para validar un correo
    // ==============================
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}