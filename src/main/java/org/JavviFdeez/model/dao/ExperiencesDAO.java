package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.entity.Experiences;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperiencesDAO implements iExperiencesDAO {
    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private static final String INSERT = "INSERT INTO cvv_experiences (contact_id, name, duration, company, location, year, position) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE cvv_experiences SET contact_id=?, name=?, duration=?, company=?, location=?, year=?, position=? WHERE experience_id=?";
    private static final String DELETE = "DELETE FROM cvv_experiences WHERE experience_id=?";
    private static final String FIND_BY_ID = "SELECT * FROM cvv_experiences WHERE experience_id=?";
    private static final String FIND_ALL = "SELECT * FROM cvv_experiences";

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // ================================================
    // Constructor para inicializar la base de datos
    // ================================================
    public ExperiencesDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param exp
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para GUARDAR una experiencia en la base de datos.
     */
    @Override
    public Experiences save(Experiences exp) throws SQLException {
        // ==============================================
        // Insertar la experiencia en la base de datos
        // ==============================================
        try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, exp.getContact_id());
            pst.setString(2, exp.getName());
            pst.setString(3, exp.getDuration());
            pst.setString(4, exp.getCompany());
            pst.setString(5, exp.getLocation());
            pst.setInt(6, exp.getYear());
            pst.setString(7, exp.getPosition());
            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se insertó ninguna experiencia, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se inserto ninguna experiencia.");
            }

            // ==============================================================
            // Obtener el ID generado por la base de datos
            // ==============================================================
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // ===============================================
                    // Asignar el ID generado al objeto experiencia
                    // ===============================================
                    exp.setExperience_id(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("❌ Error al insertar, no se inserto ninguna experiencia.");
                }
            }
        }

        return exp;
    }

    /**
     * @param exp
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR una experiencia en la base de datos.
     */
    @Override
    public Experiences update(Experiences exp) throws SQLException {
        // ==============================================
        // Actualizar la experiencia en la base de datos
        // ==============================================
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setInt(1, exp.getContact_id());
            pst.setString(2, exp.getName());
            pst.setString(3, exp.getDuration());
            pst.setString(4, exp.getCompany());
            pst.setString(5, exp.getLocation());
            pst.setInt(6, exp.getYear());
            pst.setString(7, exp.getPosition());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se insertó ninguna experiencia, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se inserto ninguna experiencia.");
            }

        }

        return exp;
    }

    /**
     * @param exp
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ELIMINAR una experiencia de la base de datos.
     */
    @Override
    public Experiences delete(Experiences exp) throws SQLException {
        // ==============================================
        // Eliminar la experiencia de la base de datos
        // ==============================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, exp.getExperience_id());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();
        }

        return exp;
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR una experiencia por su ID
     */
    @Override
    public Experiences findById(int id) throws SQLException {
        // =====================================================
        // Experiences encontrada, o null si no se encuentra
        // =====================================================
        Experiences foundExperience = null;

        // =================================================
        // Experiencia encontrada, o null si no se encuentra
        // =================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);

            // ===============================
            // Ejecutar la consulta
            // ================================
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    foundExperience = new Experiences(
                            rs.getInt("experience_id"),
                            rs.getInt("contact_id"),
                            rs.getString("name"),
                            rs.getString("duration"),
                            rs.getString("company"),
                            rs.getString("location"),
                            rs.getInt("year"),
                            rs.getString("position")
                    );
                }

            } catch (SQLException e) {
                throw new SQLException("❌ Error al buscar una experiencia por su ID.", e);
            }

            return foundExperience;

        }
    }

    /**
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR todas las experiencias
     */
    @Override
    public List<Experiences> findAll() throws SQLException {
        // ================================================
        // Lista de experiencias encontradas, o una lista vacía
        // ================================================
        List<Experiences> foundExperiences = new ArrayList<>();

        // ===============================================
        // Ejecutar la consulta y obtener el resultado
        // ===============================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    foundExperiences.add(new Experiences(
                            rs.getInt("experience_id"),
                            rs.getInt("contact_id"),
                            rs.getString("name"),
                            rs.getString("duration"),
                            rs.getString("company"),
                            rs.getString("location"),
                            rs.getInt("year"),
                            rs.getString("position")
                    ));
                }

            } catch (SQLException e) {
                throw new SQLException("❌ Error al buscar todas las experiencias.", e);
            }
        }
        return foundExperiences;
    }
}
