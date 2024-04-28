package org.JavviFdeez.controller;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.Contact_SkillsDAO;
import org.JavviFdeez.model.entity.Contact_Skills;

import java.sql.SQLException;

public class Contact_SkillsController {
    // ================
    // Atributos
    // =================
    private Contact_SkillsDAO csDAO;

    // =================
    // Constructor
    // =================
    public Contact_SkillsController() {
        this.csDAO = new Contact_SkillsDAO(ConnectionMariaDB.getConnection());
    }

    /**
     * @param cs la relacion a ser guardada en la base de datos
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de insertar una nueva relacion entre un Contact y una Skill en la base de datos
     */
    public void saveContact_Skill(Contact_Skills cs) {
        try {
            // ===========================================
            // Insertar la relacion en la base de datos
            // ===========================================
            csDAO.save(cs);
            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Relacion guardada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al guardar la relacion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param cs la relacion a ser actualizada en la base de datos
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de actualizar una relacion entre un Contact y una Skill en la base de datos
     */

    public void updateContact_Skill(Contact_Skills cs) {
        try {
            // ===========================================
            // Actualizar la relacion en la base de datos
            // ===========================================
            csDAO.update(cs);
            // ======================================================
            // Si la actualizacion es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Relacion actualizada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al actualizar la relacion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param cs la relacion a ser eliminada de la base de datos
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de eliminar una relacion entre un Contact y una Skill en la base de datos
     */
    public void deleteContact_Skill(Contact_Skills cs) {
        try {
            // =============================================
            // Eliminar la relacion en la base de datos
            // =============================================
            csDAO.delete(cs);
            // ======================================================
            // Si la eliminación es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Relacion eliminada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al eliminar la relacion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id la relacion a ser consultada de la base de datos
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de consultar una relacion entre un Contact y una Skill en la base de datos
     */
    public void getIDContact_Skill(int id) {
        try {
            // =============================================
            // Consultar la relacion en la base de datos
            // =============================================
            csDAO.findById(id);
            // ======================================================
            // Si la consulta es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Relacion consultada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al consultar la relacion: " + e.getMessage());
        }
    }

    /**
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de consultar todas las relaciones entre Contactos y Skills en la base de datos
     */
    public void getAllContact_Skill() {
        try {
            // =============================================
            // Consultar todas las relaciones en la base de datos
            // =============================================
            csDAO.findAll();
            // ======================================================
            // Si la consulta es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Relaciones consultadas exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al consultar las relaciones: " + e.getMessage());
        }
    }
}