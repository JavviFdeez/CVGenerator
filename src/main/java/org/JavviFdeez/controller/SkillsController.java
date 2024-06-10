package org.JavviFdeez.controller;

import javafx.fxml.Initializable;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.SkillsDAO;
import org.JavviFdeez.model.entity.Contact_Skills;
import org.JavviFdeez.model.entity.Experiences;
import org.JavviFdeez.model.entity.Skills;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SkillsController implements Initializable {

    // ============
    // Atributos
    // ============
    private SkillsDAO skillsDAO;
    private Connection conn;

    // ==============
    // Constructor
    // ==============
    public SkillsController() {
        this.skillsDAO = new SkillsDAO(ConnectionMariaDB.getConnection());
        this.conn = ConnectionMariaDB.getConnection();
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
     * @param updatedSkills la habilidad que se va a actualizar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ACTUALIZAR una nueva habilidad en la base de datos
     */
    public void updateSkills(Skills updatedSkills) {
        try {
            // ==========================================
            // Actualizar la habilidad en la base de datos
            // ==========================================
            skillsDAO.update(updatedSkills.getSkill_id(), updatedSkills);
            // ===========================================================
            // Si la actualizacion es exitosa, mostrar mensaje de exito.
            // ===========================================================
            System.out.println("✅ Skills actualizada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al actualizar la habilidad: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id la habilidad que se va a eliminar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ElIMINAR una nueva habilidad en la base de datos
     */
    public void deleteSkills(int id) {
        try {
            // ==========================================
            // Eliminar la habilidad de la base de datos
            // ==========================================
            skillsDAO.delete(id);
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

    public int getLastInsertedId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID() as last_id";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("last_id");
            } else {
                throw new SQLException("No se pudo obtener el último ID insertado.");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}