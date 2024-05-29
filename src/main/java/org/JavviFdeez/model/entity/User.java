package org.JavviFdeez.model.entity;

import org.JavviFdeez.utils.EmailValidator;
import org.JavviFdeez.utils.PasswordHasher;
import org.JavviFdeez.utils.PasswordValidator;

public class User {
    // =============
    // Attributes
    // =============
    private int users_id;
    private int contact_id;
    private String email;
    private String password;

    // ===============
    // Constructors
    // ===============
    public User(String email, String password, int contact_id) {
        this.users_id = users_id;
        this.contact_id = contact_id;

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
    public int getContactId() {
        return contact_id;
    }

    public void setContactId(int contactId) {
        this.contact_id = contactId;
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

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    // ===========
    // ToString
    // ===========
    @Override
    public String toString() {
        return "Users: {" +
                "users_id" + users_id +
                ", contact_id=" + contact_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}