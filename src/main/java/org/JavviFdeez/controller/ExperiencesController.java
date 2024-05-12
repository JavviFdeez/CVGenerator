package org.JavviFdeez.controller;

import javafx.fxml.Initializable;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.ExperiencesDAO;
import org.JavviFdeez.model.entity.Experiences;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ExperiencesController implements Initializable {
    // ================
    // Atributos
    // ================
    private ExperiencesDAO experiencesDAO;

    // ==============
    // Constructor
    // ==============
    public ExperiencesController() {
        this.experiencesDAO = new ExperiencesDAO(ConnectionMariaDB.getConnection());
    }

    /**
     * @param exp
     * @return
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de INSERTAR una nueva experiencia en la base de datos
     */
    public void saveExperiences(Experiences exp) {
        try {
            // ==============================================
            // Guardar la experiencia en la base de datos
            // ==============================================
            experiencesDAO.save(exp);
            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de exito
            // ======================================================
            System.out.println("✅ Experiencia guardada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error
            // =============================================
            System.err.println("❌ Error al guardar la experiencia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param exp
     * @Author: JavviFdeez
     * Metodo para ACTUALIZAR una experiencia en la base de datos y mostrar un mensaje de exito o error
     */
    public void updateExperiences(int id, Experiences exp) {
        try {
            Experiences updatedExperiences = experiencesDAO.update(id, exp);
            if (updatedExperiences != null) {
                // =========================================================
                // La actualización fue exitosa, mostrar mensaje de exito
                // =========================================================
                System.out.println("✅ Experiencia actualizada exitosamente.");
            } else {
                // ===============================================================
                // No se actualizó ninguna fila, mostrar mensaje de advertencia
                // ===============================================================;
                System.out.println("⚠️ No se encontró ninguna experiencia para actualizar.");
            }
        } catch (SQLException e) {
            // ==================================================================================================
            // Ocurrio un error al actualizar la experiencia, mostrar mensaje de error y detalles de la excepción
            // ==================================================================================================
            System.err.println("❌ Error al actualizar la experiencia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @return
     * @Author: JavviFdeez
     * Metodo para ELIMINAR una experiencia de la base de datos y mostrar un mensaje de exito o error
     */
    public void deleteExperiences(int id) {
        try {
            // ==============================================
            // Eliminar la experiencia de la base de datos
            // ==============================================
            experiencesDAO.delete(id);
            // ======================================================
            // Si la eliminación es exitosa, mostrar mensaje de exito
            // ======================================================
            System.out.println("✅ Experiencia eliminada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error
            // =============================================
            System.err.println("❌ Error al eliminar la experiencia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @return
     * @Author: JavviFdeez
     * Metodo para BUSCAR una experiencia en la base de datos
     */
    public void findExperiencesId(int id) {
        try {
            // ===================================================
            // Buscar la experiencia por su ID en la base de datos
            // ===================================================
            Experiences foundExperiences = experiencesDAO.findById(id);
            if (foundExperiences != null) {
                // ==================================================
                // Si se encontró la experiencia, mostrar los detalles
                // ==================================================
                System.out.println("✅ Experiencia encontrada.");
            } else {
                // ==============================================================================================
                // No se encontró ninguna experiencia con el ID proporcionado, mostrar mensaje de advertencia
                // ==============================================================================================
                System.out.println("⚠️ No se encontró ninguna experiencia con el ID proporcionado.");
            }
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al buscar la experiencia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @return
     * @Author: JavviFdeez
     * Metodo para BUSCAR todas las experiencia en la base de datos
     */
    public void findAllExperiences() {
        try {
            List<Experiences> experiencesList = experiencesDAO.findAll();
            if (!experiencesList.isEmpty()) {
                // =============================================================
                // Si se encontró al menos una experiencia, mostrar sus detalles
                // =============================================================
                System.out.println("Experiencias encontradas:");
                for (Experiences exp : experiencesList) {
                    System.out.println(exp);
                }
            } else {
                // =====================================================================
                // Si no se encontró ninguna experiencia, mostrar mensaje de advertencia
                // =====================================================================
                System.out.println("⚠️ No se encontró ninguna experiencia.");
            }
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al buscar las experiencias: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
