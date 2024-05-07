package org.JavviFdeez.controller;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.SkillsDAO;
import org.JavviFdeez.model.entity.Skills;

import java.sql.SQLException;

public class SkillsController {

    // ============
    // Atributos
    // ============
    private SkillsDAO skillsDAO;

    // ==============
    // Constructor
    // ==============
    public SkillsController() {
        this.skillsDAO = new SkillsDAO(ConnectionMariaDB.getConnection());
    }

    /**
     * @param s la habilidad que se va a guardar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de INSERTAR una nueva habilidad en la base de datos
     */
    public void saveSkills(Skills s) {
        try {
            // ==========================================
            // Guardar la habilidad en la base de datos
            // ==========================================
            skillsDAO.save(s);
            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Habilidades guardadas exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al guardar la habilidad: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param s la habilidad que se va a actualizar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ACTUALIZAR una nueva habilidad en la base de datos
     */
    public void updateSkills(Skills s) {
        try {
            // ==========================================
            // Actualizar la habilidad en la base de datos
            // ==========================================
            skillsDAO.update(s);
            // ===========================================================
            // Si la actualizacion es exitosa, mostrar mensaje de exito.
            // ===========================================================
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al actualizar la habilidad: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param s la habilidad que se va a eliminar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ElIMINAR una nueva habilidad en la base de datos
     */
    public void deleteSkills(Skills s) {
        try {
            // ==========================================
            // Eliminar la habilidad de la base de datos
            // ==========================================
            skillsDAO.delete(s);
            // ======================================================
            // Si la eliminación es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Habilidad eliminada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al eliminar la habilidad: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id la habilidad que se va a buscar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de BUSCAR una nueva habilidad en la base de datos
     */
    public void getSkills(int id) {
        try {
            // ==========================================
            // Buscar la habilidad en la base de datos
            // ==========================================
            Skills foundS = skillsDAO.findById(id);
            if (foundS != null) {
                // ======================================================
                // Si la consulta es exitosa, mostrar mensaje de exito.
                // ======================================================
                System.out.println("✅ Habilidad consultada exitosamente.");
            } else {
                // ======================================================
                // Si la consulta es exitosa, mostrar mensaje de exito.
                // ======================================================
                System.out.println("⚠️ No se encontró ninguna habilidad con el ID proporcionado.");
            }
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al buscar la habilidad: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de CONSULTAR todas las habilidades en la base de datos
     */
    public void getAllSkills() {
        try {
            // ==========================================
            // Consultar todas las habilidades en la base de datos
            // ==========================================
            skillsDAO.findAll();
            // ======================================================
            // Si la consulta es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Habilidades consultadas exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al consultar las habilidades: " + e.getMessage());
            e.printStackTrace();
        }
    }
}