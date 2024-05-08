package org.JavviFdeez.controller;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.AcademiesDAO;
import org.JavviFdeez.model.entity.Academies;

import java.sql.SQLException;
import java.util.List;

public class AcademiesController {
    private AcademiesDAO academiesDAO;

    // ==============
    // Constructor
    // ==============
    public AcademiesController() {
        this.academiesDAO = new AcademiesDAO(ConnectionMariaDB.getConnection());
    }

    /**
     * @param academies la academia que se va a guardar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de INSERTAR una nueva academia en la base de datos
     */
    public void saveAcademies(Academies academies) {
        try {
            // ==========================================
            // Guardar la academia en la base de datos
            // ==========================================
            academiesDAO.save(academies);
            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de éxito.
            // ======================================================
            System.out.println("✅ Academia guardada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al guardar la academia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id la academia que se va a actualizar
     * @Author: JavviFdeez
     * Método que ACTUALIZAR una academia en la base de datos y muestra un mensaje de éxito o error.
     */
    public void updateAcademies(int id, Academies updatedAcademies) {
        try {
            // Intentar actualizar la academia
            academiesDAO.update(id, updatedAcademies);

            // Mostrar mensaje de actualización exitosa
            System.out.println("✅ Academia actualizada exitosamente.");
        } catch (SQLException e) {
            // En caso de error SQL, registrar el error y mostrar un mensaje al usuario
            System.err.println("❌ Error al actualizar la academia: " + e.getMessage());
            e.printStackTrace();

            // En caso de cualquier otro tipo de error, mostrar un mensaje de error
            System.out.println("❌ Ocurrió un error al actualizar la academia. Por favor, inténtelo de nuevo más tarde.");
        }
    }

    /**
     * @param id la academia que se va a eliminar
     * @Author: JavviFdeez
     * Método para ELIMINAR una academia de la base de datos y muestra un mensaje de éxito o error.
     */
    public void deleteAcademies(int id) {
        try {
            // =========================================================
            // Eliminar la academia de la base de datos
            // =========================================================
            academiesDAO.delete(id);
            // =========================================================
            // Si la eliminación es exitosa, mostrar mensaje de éxito
            // =========================================================
            System.out.println("✅ Academia eliminada exitosamente.");
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al eliminar la academia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el ID de la academia que se va a buscar
     * @Author: JavviFdeez
     * Método para BUSCAR una academia por su ID en la base de datos y muestra un mensaje de éxito o error.
     */
    public void findAcademiesById(int id) {
        try {
            // ===================================================
            // Buscar la academia por su ID en la base de datos
            // ===================================================
            Academies foundAcademies = academiesDAO.findById(id);
            if (foundAcademies != null) {
                // ==================================================
                // Si se encontró la academia, mostrar los detalles
                // ==================================================
                System.out.println("✅ Academia encontrada:");
                System.out.println(foundAcademies);
            } else {
                // ================================================================
                // Si no se encontró la academia, mostrar mensaje de advertencia
                // ================================================================
                System.out.println("⚠️ No se encontró ninguna academia con el ID " + id + ".");
            }
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al buscar la academia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Author: JavviFdeez
     * Método para BUSCAR todas las academias de la base de datos y muestra un mensaje de exito o error.
     */
    public void findAllAcademies() {
        try {
            List<Academies> academiesList = academiesDAO.findAll();
            if (!academiesList.isEmpty()) {
                // =============================================================
                // Si se encontró al menos una academia, mostrar sus detalles
                // =============================================================
                System.out.println("Academias encontradas:");
                for (Academies academies : academiesList) {
                    System.out.println(academies);
                }
            } else {
                // =====================================================================
                // Si no se encontró ninguna academia, mostrar mensaje de advertencia
                // =====================================================================
                System.out.println("⚠️ No se encontró ninguna academia.");
            }
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al buscar las academias: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
