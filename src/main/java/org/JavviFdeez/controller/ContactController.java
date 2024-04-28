package org.JavviFdeez.controller;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.ContactDAO;
import org.JavviFdeez.model.entity.Contact;

import java.sql.SQLException;

public class ContactController {
    // =============
    // Atributos
    // =============

    private ContactDAO cDAO;

    // ==============
    // Constructor
    // ==============
    public ContactController() {
        this.cDAO = new ContactDAO(ConnectionMariaDB.getConnection());
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
            cDAO.save(contact);
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
     * @param contact el contacto que se va a actualizar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ACTUALIZAR un contacto en la base de datos
     */
    public void updateContact(Contact contact) {
        try {
            // ==========================================
            // Actualizar el contacto en la base de datos
            // ==========================================
            cDAO.update(contact);
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
     * @param contact el contacto que se va a eliminar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ELIMINAR un contacto en la base de datos
     */
    public void deleteContact(Contact contact) {
        try {
            // ==========================================
            // Eliminar el contacto de la base de datos
            // ==========================================
            cDAO.delete(contact);
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
            cDAO.findById(id);
            // ======================================================
            // Si la busqueda es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Contacto encontrado exitosamente.");
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
            cDAO.findAll();
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
}
