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
     * @param id la habilidad que se va a actualizar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ACTUALIZAR una nueva habilidad en la base de datos
     */
    public void updateSkills(int id, Skills updatedSkills) {
        try {
            // ==========================================
            // Actualizar la habilidad en la base de datos
            // ==========================================
            skillsDAO.update(id, updatedSkills);
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

    public boolean saveDataToDatabase(String name, Integer value, String name1, Integer value1, String name2, Integer value2) throws SQLException {
        // Guardar los datos en la base de datos
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionMariaDB.getConnection();
            }

            // Preparar las consultas SQL para insertar los datos en ambas tablas
            String queryCvvSkills = "INSERT INTO cvv_skills (name) VALUES (?), (?), (?)";
            String queryContactSkills = "INSERT INTO contact_skills (value) VALUES (?), (?), (?)";

            // Usar transacciones para asegurar que ambas consultas se ejecuten correctamente
            conn.setAutoCommit(false);

            try (PreparedStatement pstCvvSkills = conn.prepareStatement(queryCvvSkills);
                 PreparedStatement pstContactSkills = conn.prepareStatement(queryContactSkills)) {

                // Insertar en cvv_skills
                pstCvvSkills.setString(1, name);
                pstCvvSkills.setString(2, name1);
                pstCvvSkills.setString(3, name2);
                pstCvvSkills.executeUpdate();

                // Insertar en contact_skills
                pstContactSkills.setInt(1, value);
                pstContactSkills.setInt(2, value1);
                pstContactSkills.setInt(3, value2);

                pstCvvSkills.executeUpdate();
                pstContactSkills.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Contact_Skills> getSkillsById(int contactId) throws SQLException {
        List<Contact_Skills> skillsList = new ArrayList<>();
        String query = "SELECT cs.cskill_id, cs.skill_id, s.name, cs.value FROM cvv_contact_skills cs JOIN cvv_skills s ON cs.skill_id = s.skill_id WHERE cs.contact_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, contactId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Obtener los datos de cada Skill
                    int cskill_id = resultSet.getInt("cskill_id");
                    int skill_id = resultSet.getInt("skill_id");
                    String name = resultSet.getString("name");
                    int value = resultSet.getInt("value");

                    // Crear un objeto Contact_Skills con los datos obtenidos y agregarlo a la lista
                    Contact_Skills contactSkills = new Contact_Skills(contactId, skill_id, value);
                    Skills skills = new Skills(name);
                    skillsList.add(contactSkills);
                    skillsList.add(skills);
                }
            }
        }

        return skillsList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}