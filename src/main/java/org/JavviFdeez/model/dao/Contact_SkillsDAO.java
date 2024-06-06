package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.dao.interfaces.iContact_SkillsDAO;
import org.JavviFdeez.model.entity.Contact_Skills;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Contact_SkillsDAO implements iContact_SkillsDAO {

    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private static final String INSERT = "INSERT INTO cvv_contact_skills (contact_id, skill_id, value) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE cvv_contact_skills SET value=? WHERE cskill_id=?";
    private static final String DELETE = "DELETE FROM cvv_contact_skills WHERE cskill_id=?";
    private static final String FIND_BY_ID = "SELECT * FROM cvv_contact_skills WHERE cskill_id=?";
    private static final String FIND_ALL = "SELECT * FROM cvv_contact_skills";

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // ================================================
    // Constructor para inicializar la base de datos
    // ================================================
    public Contact_SkillsDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param cs la relacion a ser guardada en la base de datos
     * @return la relacion guardada, incluyendo su ID generado
     * @throws SQLException
     * @Author JavviFdeez
     * Metodo para GUARDAR una relacion entre un Contact y una Skill en la base de datos.
     */
    @Override
    public Contact_Skills save(Contact_Skills cs) throws SQLException {
        // ===========================================
        // Insertar la relacion en la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(2, cs.getSkill_id());
            pst.setInt(3, cs.getValue());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ===================
            // Realizar commit
            // ===================
            conn.commit();

            // ==============================================================
            // Si no se insertó ninguna relacion, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 1) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        cs.setCskill_id(generatedId);
                    }
                }
            } else {
                throw new SQLException("❌ Error al guardar la relación entre contacto y habilidad");
            }
        } catch (SQLException e) {
            throw new SQLException("❌ Error al guardar la relación entre contacto y habilidad", e);
        }

        return cs;
    }


    /**
     * @param id la relacion a ser actualizada
     * @return true si la actualización fue exitosa, false de lo contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author JavviFdeez
     * Metodo para ACTUALIZAR una relacion entre un Contact y una Skill en la base de datos.
     */
    @Override
    public Contact_Skills update(int id, Contact_Skills updatedCS) throws SQLException {
        // Habilitar la transacción
        conn.setAutoCommit(false);

        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            // Establecer los valores de los parámetros en la consulta SQL
            pst.setInt(1, updatedCS.getValue());
            pst.setInt(2, id);

            // Ejecutar la consulta SQL de actualización
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                // Si no se afectaron filas, lanzar una excepción
                throw new SQLException("No se pudo actualizar la relación con ID: " + id);
            }

            // Realizar commit
            conn.commit();

        } catch (SQLException e) {
            // En caso de error, hacer rollback
            conn.rollback();
            throw new SQLException("Error al actualizar la relación: " + e.getMessage(), e);
        } finally {
            try {
                // Restaurar la autoconfirmación
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                // Manejar cualquier excepción al restaurar la autoconfirmación
                throw new SQLException("Error al restaurar la autoconfirmación: " + ex.getMessage(), ex);
            }
        }

        return updatedCS;
    }

    /**
     * @param id la relacion a ser eliminada
     * @return true si la eliminación fue exitosa, false de lo contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author JavviFdeez
     * Metodo para ELIMINAR una relacion entre un Contact y una Skill en la base de datos.
     */
    @Override
    public void delete(int id) throws SQLException {
        // Habilitar la transacción
        conn.setAutoCommit(false);
        // =============================================
        // Eliminar la relacion en la base de datos
        // =============================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se eliminó ninguna academia con el ID: " + id);
            }

            // Realizar commit
            conn.commit();
        } catch (SQLException e) {
            // En caso de error, hacer rollback
            conn.rollback();
            throw new SQLException("Error al eliminar la academia: " + e.getMessage(), e);
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
     * @param id el ID de la relacion a buscar
     * @return la relacion encontrada, o null si no se encuentra
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author JavviFdeez
     * Metodo para BUSCAR una relacion entre un Contact y una Skill por su ID en la base de datos.
     */
    @Override
    public Contact_Skills findById(int id) throws SQLException {
        Contact_Skills cs = null;

        // ===================================================
        // Buscar la relacion por su ID en la base de datos
        // ===================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // ====================================
                    // Obtener los datos de la consulta
                    // ====================================
                    int contact_id = rs.getInt("contact_id");
                    int skill_id = rs.getInt("skill_id");
                    int value = rs.getInt("value");

                    // =======================
                    // Crear la relacion
                    // =======================
                    cs = new Contact_Skills( contact_id, skill_id, value);
                }
            }
        }
        return cs;
    }

    /**
     * @return una lista con todas las relaciones encontradas
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Metodo para BUSCAR todas las relaciones entre un Contact y una Skill en la base de datos.
     */
    @Override
    public List<Contact_Skills> findAll() throws SQLException {
        List<Contact_Skills> csList = new ArrayList<>();

        // ===================================================
        // Buscar todas las relaciones en la base de datos
        // ===================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // ===========================
                    // Crear una nueva relacion
                    // ===========================
                    int contact_id = rs.getInt("contact_id");
                    int skill_id = rs.getInt("skill_id");
                    int value = rs.getInt("value");

                    // =======================
                    // Crear la relacion
                    // =======================
                    Contact_Skills cs = new Contact_Skills(contact_id, skill_id, value);
                    csList.add(cs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("❌ Error al buscar todas las relaciones entre un Contact y una Skill.", e);
        }

        return csList;
    }
}