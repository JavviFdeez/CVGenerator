package org.JavviFdeez.controller;

import javafx.fxml.Initializable;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.Contact_SkillsDAO;
import org.JavviFdeez.model.entity.Contact_Skills;
import org.JavviFdeez.model.entity.Skills;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Contact_SkillsController extends Contact_SkillsControllerAbstract implements Initializable {
    // ================
    // Atributos
    // =================
    private Contact_SkillsDAO csDAO;
    private Connection conn;

    // =================
    // Constructor
    // =================
    public Contact_SkillsController() {
        this.csDAO = new Contact_SkillsDAO(ConnectionMariaDB.getConnection());
        this.conn = ConnectionMariaDB.getConnection();
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
            csDAO.update(cs.getSkill_id(), cs);
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
     * @param id la relacion a ser eliminada de la base de datos
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de eliminar una relacion entre un Contact y una Skill en la base de datos
     */
    public void deleteContact_Skill(int id) {
        try {
            // =============================================
            // Eliminar la relacion en la base de datos
            // =============================================
            csDAO.delete(id);
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
            // Buscar la relacion en la base de datos
            // =============================================
            Contact_Skills foundCs = csDAO.findById(id);
            if (foundCs != null) {
                // ======================================================
                // Si la consulta es exitosa, mostrar mensaje de exito.
                // ======================================================
                System.out.println("✅ Relacion consultada exitosamente.");
            } else {
                // ======================================================
                // Si la consulta es exitosa, mostrar mensaje de exito.
                // ======================================================
                System.out.println("⚠️ No se encontró ninguna relacion con el ID proporcionado.");
            }
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al consultar la relacion: " + e.getMessage());
            e.printStackTrace();
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

    public List<Object> getSkillsById(int contactId) throws SQLException {
        List<Object> skillsList = new ArrayList<>();
        String query = "SELECT cs.cskill_id, cs.skill_id, s.name, cs.value " +
                "FROM cvv_contact_skills cs " +
                "JOIN cvv_skills s ON cs.skill_id = s.skill_id " +
                "WHERE cs.contact_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, contactId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Obtener los datos de cada Skill
                    int cskill_id = resultSet.getInt("cskill_id");
                    int skill_id = resultSet.getInt("skill_id");
                    String name = resultSet.getString("name");
                    int value = resultSet.getInt("value");

                    // Crear un objeto Contact_Skills con los datos obtenidos
                    Contact_Skills contactSkills = new Contact_Skills(contactId, skill_id, value);
                    contactSkills.setCskill_id(cskill_id); // Establecer el cskill_id después de la creación
                    skillsList.add(contactSkills);

                    // Crear un objeto Skills con el nombre de la habilidad
                    Skills skills = new Skills(name);
                    skills.setSkill_id(skill_id); // Establecer el skill_id si es necesario
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