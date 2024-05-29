package org.JavviFdeez.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.JavviFdeez.model.dao.interfaces.iAcademiesDAO;
import org.JavviFdeez.model.entity.Academies;

public class AcademiesDAO implements iAcademiesDAO {
    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private static final String INSERT = "INSERT INTO cvv_academies (contact_id, name, entity, location, year) VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE cvv_academies SET name=?, entity=?, location=?, year=? WHERE academies_id=?";
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
     * @param a la academia a ser guardada
     * @return la academia guardada, incluyendo su ID generado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para GUARDAR una academia en la base de datos.
     */
    @Override
    public Academies save(Academies a) throws SQLException {
        // ===========================================
        // Insertar la academia en la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, a.getContact_id());
            pst.setString(2, a.getName());
            pst.setString(3, a.getEntity());
            pst.setString(4, a.getLocation());
            pst.setString(5, a.getYear());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ===================
            // Realizar commit
            // ===================
            conn.commit();

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
     * @param id la academia con la información actualizada
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @author: JavviFdeez
     * Método para ACTUALIZAR la información de una academia en la base de datos.
     */
    public Academies update(int id, Academies updatedAcademies) throws SQLException {
        // Habilitar la transacción
        conn.setAutoCommit(false);

        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            // Establecer los valores de los parámetros en la consulta SQL
            pst.setString(1, updatedAcademies.getName());
            pst.setString(2, updatedAcademies.getEntity());
            pst.setString(3, updatedAcademies.getLocation());
            pst.setString(4, updatedAcademies.getYear());
            pst.setInt(5, id);

            // Ejecutar la consulta SQL de actualización
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                // Si no se afectaron filas, lanzar una excepción
                throw new SQLException("No se pudo actualizar la academia con ID: " + id);
            }

            // Realizar commit
            conn.commit();
        } catch (SQLException e) {
            // En caso de error, hacer rollback
            conn.rollback();
            throw new SQLException("Error al actualizar la academia: " + e.getMessage(), e);
        } finally {
            try {
                // Restaurar la autoconfirmación
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new SQLException("Error al restaurar la autoconfirmación: " + ex.getMessage(), ex);
            }
        }

        return updatedAcademies;
    }

    /**
     * @param id la academia que se va a eliminar
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ELIMINAR una academia de la base de datos y retorna true si la operación es exitosa.
     */
    @Override
    public void delete(int id) throws SQLException {
        // Habilitar la transacción
        conn.setAutoCommit(false);

        // ============================================
        // Eliminar la academia de la base de datos
        // ============================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se elimino ninguna academia, mostrar mensaje de error
            // ==============================================================
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
                            res.getInt("contact_id"),
                            res.getString("name"),
                            res.getString("entity"),
                            res.getString("location"),
                            res.getString("year")
                    );
                }
            } catch (SQLException e) {
                throw new SQLException("❌ Error al buscar una academia por su ID.", e);
            }
        }

        return foundAcademy;
    }

    /**
     * @return una lista de academias encontradas, o una lista vacía si no se encuentran
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @author: JavviFdeez
     * Método para BUSCAR todas las academias de la base de datos.
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
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // ==============================
                    // Crear un objeto de academia
                    // ==============================
                    Academies academies = new Academies(
                            rs.getInt("contact_id"),
                            rs.getString("name"),
                            rs.getString("entity"),
                            rs.getString("location"),
                            rs.getString("year")
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

    public Academies getIDContact(int contactId) throws SQLException {
        // Declarar una variable para almacenar la academia encontrada
        Academies academies = null;

        // Definir la consulta SQL para buscar las academias por contact_id
        String query = "SELECT * FROM cvv_academies WHERE contact_id = ?";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            // Establecer el parámetro contact_id en la consulta SQL
            pst.setInt(1, contactId);

            // Ejecutar la consulta SQL y obtener el resultado
            try (ResultSet rs = pst.executeQuery()) {
                // Verificar si se encontró una academia para el contact_id dado
                if (rs.next()) {
                    // Mapear los resultados a un objeto Academies
                    academies = new Academies(
                            rs.getInt("contact_id"),
                            rs.getString("name"),
                            rs.getString("entity"),
                            rs.getString("location"),
                            rs.getString("year")
                    );
                }
            }
        }

        return academies;
    }
}

