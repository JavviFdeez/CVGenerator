package org.JavviFdeez.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.JavviFdeez.model.entity.Academies;

public class AcademiesDAO implements iAcademiesDAO {
    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private static final String INSERT = "INSERT INTO cvv_academies (academies_id, contact_id, name, entity, location, year) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cvv_academies SET contact_id=?, name=?, entity=?, location=?, year=? WHERE academies_id=?";
    private static final String DELETE = "DELETE FROM cvv_academies WHERE academies_id=?";
    private static final String FIND_BY_ID = "SELECT * FROM cvv_academies WHERE academies_id=?";
    private static final String FIND_ALL = "SELECT * FROM cvv_academies";

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // ================================================
    // Constructor para inicializar la base de datos
    // ================================================
    public AcademiesDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * @Author: JavviFdeez
     * Método para GUARDAR una academia en la base de datos.
     *
     * @param a la academia a ser guardada
     * @return la academia guardada, incluyendo su ID generado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     */
    @Override
    public Academies save(Academies a) throws SQLException {
        // ===========================================
        // Insertar la academia en la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, a.getAcademies_id());
            pst.setInt(2, a.getContact_id());
            pst.setString(3, a.getName());
            pst.setString(4, a.getEntity());
            pst.setString(5, a.getLocation());
            pst.setInt(6, a.getYear());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se insertó ninguna academia, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se guardó ninguna academia.");
            }

            // ======================================================================
            // Obtener el identificador generado y asignarlo al objeto de academia
            // ======================================================================
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // ===============================================================
                    // Si se obtuvo el ID generado, asignarlo al objeto de academia
                    // ===============================================================
                    a.setAcademies_id(generatedKeys.getInt(1));
                } else {
                    // ===========================================================
                    // Si no se obtuvo el ID generado, mostrar mensaje de error
                    // ===========================================================
                    throw new SQLException("❌ Error al insertar, no se obtuvo el ID generado.");
                }
            }
        }

        return a;
    }

    /**
     * @param a la academia con la información actualizada
     * @return true si la actualización fue exitosa, false si no se pudo actualizar
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @author: JavviFdeez
     * Método para ACTUALIZAR la información de una academia en la base de datos.
     */
    @Override
    public Academies update(Academies a) throws SQLException {
        // ============================================
        // Actualizar la academia en la base de datos
        // ============================================
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setInt(1, a.getContact_id());
            pst.setString(2, a.getName());
            pst.setString(3, a.getEntity());
            pst.setString(4, a.getLocation());
            pst.setInt(5, a.getYear());
            pst.setInt(6, a.getAcademies_id());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ===============================================
            // Verificar si se actualizó al menos una fila
            // ===============================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al actualizar, no se actualizo ninguna academia.");
            }
        }

        return a;
    }

    /**
     * @Author: JavviFdeez
     * Método para ELIMINAR una academia de la base de datos y retorna true si la operación es exitosa.
     *
     * @param a la academia que se va a eliminar
     * @return true si la academia se elimina correctamente, false en caso contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     */
    @Override
    public Academies delete(Academies a) throws SQLException {
        // ===========================================
        // Eliminar la academia de la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, a.getAcademies_id());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

        }

        return a;
    }

    /**
     * Método para BUSCAR una academia por su ID en la base de datos.
     *
     * @param id el ID de la academia que se va a buscar
     * @return la academia encontrada, o null si no se encuentra
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     */
    @Override
    public Academies findById(int id) throws SQLException {
        // =================================================
        // Academia encontrada, o null si no se encuentra
        // =================================================
        Academies foundAcademy = null;

        // ==================================================
        // Consulta SQL para buscar una academia por su ID
        // ==================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);

            // ==============================================
            // Ejecutar la consulta y obtener el resultado
            // ==============================================
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    // ==========================================================================
                    // Crear un objeto de academia con los datos obtenidos de la base de datos
                    // ==========================================================================
                    foundAcademy = new Academies(
                            res.getInt("academies_id"),
                            res.getInt("contact_id"),
                            res.getString("name"),
                            res.getString("entity"),
                            res.getString("location"),
                            res.getInt("year")
                    );
                }
            } catch (SQLException e) {
                throw new SQLException("❌ Error al buscar una academia por su ID.", e);
            }
        }

        return foundAcademy;
    }

    /**
     * @author: JavviFdeez
     * Método para BUSCAR todas las academias de la base de datos.
     *
     * @return una lista de academias encontradas, o una lista vacía si no se encuentran
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     */
    @Override
    public List<Academies> findAll() throws SQLException {
        // =========================================
        // Lista de academias, o vacía si no hay
        // =========================================
        List<Academies> academiesList = new ArrayList<>();

        // ===============================================
        // Ejecutar la consulta y obtener el resultado
        // ===============================================
        try (PreparedStatement pst = conn.prepareStatement (FIND_ALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // ==============================
                    // Crear un objeto de academia
                    // ==============================
                    Academies academies = new Academies(
                            rs.getInt("academies_id"),
                            rs.getInt("contact_id"),
                            rs.getString("name"),
                            rs.getString("entity"),
                            rs.getString("location"),
                            rs.getInt("year")
                    );
                    // =================================
                    // Agregar la academia a la lista
                    // =================================
                    academiesList.add(academies);
                }
            } catch (SQLException e) {
                throw new SQLException("❌ Error al buscar las academias: " + e.getMessage());
            }
        }
        return academiesList;
    }
}