package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.dao.interfaces.iSkillsDAO;
import org.JavviFdeez.model.entity.Skills;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillsDAO implements iSkillsDAO {
    // =======================================
    // Sentencias SQL para la base de datos
    // ========================================
    private static final String INSERT = "INSERT INTO cvv_skills (name) VALUES (?)";
    private static final String UPDATE = "UPDATE cvv_skills SET name=? WHERE skill_id=?";
    private static final String DELETE = "DELETE FROM cvv_skills WHERE skill_id=?";
    private static final String FIND_BY_ID = "SELECT * FROM cvv_skills WHERE skill_id=?";
    private static final String FIND_ALL = "SELECT * FROM cvv_skills";

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // ================================================
    // Constructor para inicializar la base de datos
    // ================================================
    public SkillsDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param s la skill a ser guardada
     * @return la skill guardada, incluyendo su ID generado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para GUARDAR una skill en la base de datos.
     */
    public Skills save(Skills s) throws SQLException {
        // ===========================================
        // Insertar la skill en la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, s.getName());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se insertó ninguna skill, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se inserto ninguna skill.");
            } else {
                // =================================================
                // Obtener el ID generado por MySQL para la skill
                // =================================================
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        s.setSkill_id(rs.getInt(1));
                    }
                }
            }
        }

        return s;
    }

    /**
     * @param id la skill a ser actualizada
     * @return la skill actualizada
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ACTUALIZAR una skill en la base de datos.
     */
    public Skills update(int id, Skills updatedSkill) throws SQLException {
        // ===========================================
        // Actualizar la skill en la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setString(1, updatedSkill.getName());
            pst.setInt(2, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ===============================================================
            // Verificar si se actualizó al menos una fila
            // ===============================================================
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar la skill con ID: " + id);
            }
            // Realizar commit
            conn.commit();
        } catch (SQLException e) {
            // En caso de error, hacer rollback
            conn.rollback();
            throw new SQLException("Error al actualizar la skill: " + e.getMessage(), e);
        } finally {
            try {
                // Restaurar la autoconfirmación
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new SQLException("Error al restaurar la autoconfirmación: " + ex.getMessage(), ex);
            }
        }

        return updatedSkill;
    }

    /**
     * @param id la skill a ser eliminada
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ELIMINAR una skill de la base de datos.
     */
    public void delete(int id) throws SQLException {
        // ===========================================
        // Eliminar la skill de la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();
            // ==============================================================
            // Si no se elimino ninguna skill, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("No se eliminó ninguna skill con el ID: " + id);
            }

            // Realizar commit
            conn.commit();
        } catch (SQLException e) {
            // En caso de error, hacer rollback
            conn.rollback();
            throw new SQLException("Error al eliminar la skill: " + e.getMessage(), e);
        } finally {
            try {
                // Restaurar la autoconfirmación
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                // Manejar cualquier excepción al restaurar la autoconfirmación
                throw new SQLException("Error al restaurar la autoconfirmación: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * @param id el ID de la skill a buscar
     * @return la skill con el ID especificado, o null si no se encuentra
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para BUSCAR una skill por su ID en la base de datos.
     */
    public Skills findById(int id) throws SQLException {
        // ==============================================
        // Skill encontrada, o null si no se encuentra
        // ==============================================
        Skills foundSkill = null;

        // ================================================
        // Consulta SQL para buscar una skill por su ID
        // ================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // =================================================
                    // Crear un objeto de skill con los datos obtenidos
                    // =================================================
                    foundSkill = new Skills(
                            rs.getString("name"));
                }
            }
        }

        return foundSkill;
    }

    /**
     * @return una lista de todas las skills en la base de datos
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para BUSCAR todas las skills en la base de datos.
     */
    @Override
    public List<Skills> findAll() throws SQLException {
        // ===============================
        // Lista de skills encontradas
        // ===============================
        List<Skills> foundSkills = new ArrayList<>();

        // ================================
        // Consulta SQL para buscar skills
        // ================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            // =======================
            // Ejecutar la consulta
            // =======================
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // =================================================
                    // Crear un objeto de skill con los datos obtenidos
                    // =================================================
                    int skill_id = rs.getInt("skill_id");
                    String name = rs.getString("name");

                    // =================================================
                    // Crear un objeto de skill con los datos obtenidos
                    // =================================================
                    Skills s = new Skills(name);
                    foundSkills.add(s);
                }
            } catch (SQLException e) {
                throw new SQLException("❌ Error al buscar skills: " + e.getMessage());
            }
        }

        return foundSkills;
    }
}