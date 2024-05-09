package org.JavviFdeez.model.entity;

import org.JavviFdeez.utils.EmailValidator;
import org.JavviFdeez.utils.PasswordHasher;
import org.JavviFdeez.utils.PasswordValidator;

public class Users {
    // =============
    // Attributes
    // =============
    private int users_id;
    private String email;
    private String password;

    // ===============
    // Constructors
    // ===============
    public Users(String email, String password) {
        this.users_id = 0;

        if (EmailValidator.isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("❌ Correo electrónico no válido");
        }

        if (PasswordValidator.isValidPassword(password)) {
            this.password = PasswordHasher.hashPassword(password);
        } else {
            throw new IllegalArgumentException("❌ Contraseña no válida. \n" +
                    "⚠️ Debe tener un mínimo de 10 caracteres y que incluya: MINUSCULAS, MAYUSCULAS, NUMEROS");
        }
    }

    // ====================
    // Getters & Setters
    // ====================
    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ===========
    // ToString
    // ===========
    @Override
    public String toString() {
        return "Users: {" +
                "users_id=" + users_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}