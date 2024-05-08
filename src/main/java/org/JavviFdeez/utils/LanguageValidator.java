package org.JavviFdeez.utils;

public class LanguageValidator {
    // ==============================================================================
    // Método para validar que un valor de idioma esté dentro del rango permitido
    // ==============================================================================
    public static boolean isValidLanguageRange(int value) {
        return value >= 0 && value <= 5;
    }
}