package org.JavviFdeez.controller;

import javafx.fxml.Initializable;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.ContactDAO;
import org.JavviFdeez.model.entity.Contact;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactController implements Initializable {
    // =============
    // Atributos
    // =============

    private ContactDAO contactDAO;

    private Connection conn;

    // ==============
    // Constructor
    // ==============
    public ContactController() {
        this.contactDAO = new ContactDAO(ConnectionMariaDB.getConnection());
        this.conn = ConnectionMariaDB.getConnection();
    }

    /**
     * @param contact el contacto que se va a guardar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de INSERTAR una nuevo contacto en la base de datos
     */
    public void saveContact(Contact contact) {
        try {
            // ==========================================
            // Guardar el contacto en la base de datos
            // ==========================================
            contactDAO.save(contact);
            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de éxito.
            // ======================================================
            System.out.println("✅ Contacto guardada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al guardar el contacto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el contacto que se va a actualizar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ACTUALIZAR un contacto en la base de datos
     */
    public void updateContact(int id, Contact updatedContact) {
        try {
            // ==========================================
            // Actualizar el contacto en la base de datos
            // ==========================================
            contactDAO.update(id, updatedContact);
            // ======================================================
            // Si la actualizacion es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Contacto actualizado exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al actualizar el contacto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el contacto que se va a eliminar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ELIMINAR un contacto en la base de datos
     */
    public void deleteContact(int id) {
        try {
            // ==========================================
            // Eliminar el contacto de la base de datos
            // ==========================================
            contactDAO.delete(id);
            // ======================================================
            // Si la eliminación es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Contacto eliminado exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al eliminar el contacto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el contacto que se va a buscar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de buscar un contacto por ID en la base de datos
     */
    public void getIDContact(int id) {
        try {
            // ==========================================
            // Buscar el contacto en la base de datos
            // ==========================================
            Contact foundContact = contactDAO.findById(id);
            if (foundContact != null) {
                // ======================================================
                // Si la busqueda es exitosa, mostrar mensaje de exito.
                // ======================================================
                System.out.println("✅ Contacto encontrado exitosamente: " + foundContact);
            } else {
                // ======================================================
                // Si la busqueda es exitosa, mostrar mensaje de exito.
                // ======================================================
                System.out.println("⚠️ No se encontró ningun contacto con el ID proporcionado.");
            }
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al buscar el contacto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de buscar todos los contactos en la base de datos
     */
    public void getAllContact() {
        try {
            // ==========================================
            // Buscar todos los contactos en la base de datos
            // ==========================================
            contactDAO.findAll();
            // ======================================================
            // Si la busqueda es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Contactos encontrados exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al buscar los contactos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean saveDataToDatabase(String name, String lastname, String image, String occupation, String mobile, String email, String linkedin, String location, String extra) throws SQLException {
        // Guardar los datos en la base de datos
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionMariaDB.getConnection();
            }

            // Preparar la consulta SQL para insertar los datos
            String query = "INSERT INTO cvv_contact (name, lastname, image, occupation, mobile, email, linkedin, location, extra) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, name);
                pst.setString(2, lastname);
                pst.setString(3, image);
                pst.setString(4, occupation);
                pst.setString(5, mobile);
                pst.setString(6, email);
                pst.setString(7, linkedin);
                pst.setString(8, location);
                pst.setString(9, extra);
                pst.execute();
                try (ResultSet rs = pst.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
