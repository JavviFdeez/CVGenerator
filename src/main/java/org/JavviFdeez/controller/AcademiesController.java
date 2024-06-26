package org.JavviFdeez.controller;

import javafx.fxml.Initializable;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.AcademiesDAO;
import org.JavviFdeez.model.entity.Academies;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AcademiesController extends AcademiesControllerAbstract implements Initializable {
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
     * @param updatedAcademies la academia que se va a actualizar
     * @Author: JavviFdeez
     * Método que ACTUALIZAR una academia en la base de datos y muestra un mensaje de éxito o error.
     */
    public void updateAcademies(Academies updatedAcademies) {
        try {
            // =========================================
            // Actualizar el academia en la base de datos
            // =========================================
            academiesDAO.update(updatedAcademies.getAcademies_id(), updatedAcademies);

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
    public void deleteAcademies(int id){
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
     * @return
     * @Author: JavviFdeez
     * Método para BUSCAR todas las academias de la base de datos y muestra un mensaje de exito o error.
     */
    public Academies findAllAcademies() {
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
            }
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al buscar las academias: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Academies> getAcademiesById(int contactId) throws SQLException {
        List<Academies> academiesList = new ArrayList<>();
        String query = "SELECT * FROM cvv_academies WHERE contact_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, contactId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Obtener los datos de cada academia
                    int academiesId = resultSet.getInt("academies_id");
                    String name = resultSet.getString("name");
                    String entity = resultSet.getString("entity");
                    String location = resultSet.getString("location");
                    String year = resultSet.getString("year");

                    // Crear un objeto Academies con los datos obtenidos y agregarlo a la lista
                    Academies academies = new Academies(academiesId, contactId, name, entity, location, year);
                    academiesList.add(academies);
                }
            }
        }

        return academiesList;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
