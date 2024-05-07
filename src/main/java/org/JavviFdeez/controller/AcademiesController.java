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
        this.academiesDAO = new AcademiesDAO(ConnectionMariaDB.getConnection() );
    }

    /**
      * @Author: JavviFdeez
      * Metodo para mostrar un mensaje de INSERTAR una nueva academia en la base de datos
      *
      * @param academies la academia que se va a guardar
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
     * @Author: JavviFdeez
     * Método que ACTUALIZAR una academia en la base de datos y muestra un mensaje de éxito o error.
     *
     * @param academies la academia que se va a actualizar
     */
    public void updateAcademies(Academies academies) {
        try {
            Academies updatedAcademies = academiesDAO.update(academies);
            if (updatedAcademies != null) {
                // =========================================================
                // La actualización fue exitosa, mostrar mensaje de éxito
                // =========================================================
                System.out.println("✅ Academia actualizada exitosamente.");
            } else {
                // ===============================================================
                // No se actualizó ninguna fila, mostrar mensaje de advertencia
                // ===============================================================
                System.out.println("⚠️ No se encontró ninguna academia para actualizar.");
            }
        } catch (SQLException e) {
            // ==================================================================================================
            // Ocurrió un error al actualizar la academia, mostrar mensaje de error y detalles de la excepción
            // ==================================================================================================
            System.err.println("❌ Error al actualizar la academia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Author: JavviFdeez
     * Método para ELIMINAR una academia de la base de datos y muestra un mensaje de éxito o error.
     *
     * @param academies la academia que se va a eliminar
     */
    public void deleteAcademies(Academies academies) {
        try {
            Academies deletedAcademies = academiesDAO.delete(academies);
            if (deletedAcademies != null) {
                // =========================================================
                // Si la eliminación es exitosa, mostrar mensaje de éxito
                // =========================================================
                System.out.println("✅ Academia eliminada exitosamente.");
            } else {
                // ================================================================
                // Si no se eliminó ninguna fila, mostrar mensaje de advertencia
                // ================================================================
                System.out.println("⚠️ No se encontró ninguna academia para eliminar.");
            }
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al eliminar la academia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Author: JavviFdeez
     * Método para BUSCAR una academia por su ID en la base de datos y muestra un mensaje de éxito o error.
     *
     * @param id el ID de la academia que se va a buscar
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
