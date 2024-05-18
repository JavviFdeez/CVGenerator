package org.JavviFdeez.controller;

import javafx.fxml.Initializable;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.AcademiesDAO;
import org.JavviFdeez.model.entity.Academies;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AcademiesController extends AcademiesControllerAbstract  implements Initializable {
    private AcademiesDAO academiesDAO;
    private Connection conn;

    // ==============
    // Constructor
    // ==============
    public AcademiesController() {
        this.academiesDAO = new AcademiesDAO(ConnectionMariaDB.getConnection());
        this.conn = ConnectionMariaDB.getConnection();
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
            // =========================================
            // Actualizar el academia en la base de datos
            // =========================================
            academiesDAO.update(id, updatedAcademies);

            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de exito.
            // ======================================================
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
            // =================================================
            // Buscar todas las academias en la base de datos
            // =================================================
            List<Academies> academiesList = academiesDAO.findAll();

            if (!academiesList.isEmpty()) {
                // =============================================================
                // Si se encontró al menos una academia, mostrar sus detalles
                // =============================================================
                System.out.println("✅ Lista de academias encontradas:");
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

    public boolean saveDataToDatabase(String name, String entity, String location, String year, String name1, String entity1, String location1, String year1, String name2, String entity2, String location2, String year2, int lastContactID) throws SQLException {
        // Guardar los datos en la base de datos
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionMariaDB.getConnection();
            }

            // Preparar la consulta SQL para insertar los datos
            String query = "INSERT INTO cvv_academies (name, entity, location, year) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, name.isEmpty() ? null : name);
                pst.setString(2, entity.isEmpty() ? null : entity);
                pst.setString(3, location.isEmpty() ? null : location);
                pst.setString(4, year);
                pst.setString(5, name1.isEmpty() ? null : name1);
                pst.setString(6, entity1.isEmpty() ? null : entity1);
                pst.setString(7, location1.isEmpty() ? null : location1);
                pst.setString(8, year1);
                pst.setString(9, name2.isEmpty() ? null : name2);
                pst.setString(10, entity2.isEmpty() ? null : entity2);
                pst.setString(11, location2.isEmpty() ? null : location2);
                pst.setString(12, year2);
                pst.setInt(13, lastContactID);
                pst.executeUpdate();

                // Si se ejecuta sin excepciones, devuelve true
                return true;
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción de SQL aquí
            throw e;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
