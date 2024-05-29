package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.dao.interfaces.iUsersDAO;
import org.JavviFdeez.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO implements iUsersDAO {
    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private static final String INSERT = "INSERT INTO cvv_users (email, password, contact_id) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE cvv_users SET email=?, password=? WHERE users_id=?";
    private static final String DELETE = "DELETE FROM cvv_users WHERE users_id=?";
    private static final String FIND_BY_ID = "SELECT * FROM cvv_users WHERE users_id=?";
    private static final String FIND_ALL = "SELECT * FROM cvv_users";

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // ================================================
    // Constructor para inicializar la base de datos
    // ================================================
    public UsersDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param user el usuario a ser guardado
     * @return el usuario guardado, incluyendo su ID generado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para GUARDAR un usuario en la base de datos.
     */
    public User save(User user) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());
            pst.setInt(3, user.getContactId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se guardó ningún usuario.");
            }

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setContactId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("❌ Error al insertar, no se guardó ningún usuario.");
                }
            }

            return user;
        }
    }

    /**
     * @param id el ID del usuario a ser actualizado
     * @param id el usuario a ser actualizado
     * @return el usuario actualizado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ACTUALIZAR un usuario en la base de datos.
     */
    @Override
    public User update(int id, User updateUser) throws SQLException {
        // ===========================
        // Habilitar la transacción
        // ===========================
        conn.setAutoCommit(false);

        // ===========================================
        // Actualizar el usuario en la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setString(1, updateUser.getEmail());
            pst.setString(2, updateUser.getPassword());
            pst.setInt(3, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se actualizo ningun usuario, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al actualizar, no se actualizo ningun usuario.");
            }

            // ==================
            // Realizar commit
            // ==================
            conn.commit();

        } catch (SQLException e) {
            // ====================================
            // En caso de error, hacer rollback
            // ====================================
            conn.rollback();
            throw new SQLException("Error al actualizar el usuario: " + e.getMessage(), e);
        } finally {
            try {
                // ================================
                // Restaurar la autoconfirmación
                // ================================
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new SQLException("Error al restaurar la autoconfirmación: " + ex.getMessage(), ex);
            }
        }

        return updateUser;
    }

    /**
     * @param id el ID del usuario a ser eliminado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ELIMINAR un usuario en la base de datos.
     */
    @Override
    public void delete(int id) throws SQLException {
        // Habilitar la transacción
        conn.setAutoCommit(false);

        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se elimino ningun usuario, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al eliminar, no se elimino ningun usuario.");
            }

            // ==================
            // Realizar commit
            // ==================
            conn.commit();

        } catch (SQLException e) {
            // ====================================
            // En caso de error, hacer rollback
            // ====================================
            conn.rollback();
            throw new SQLException("Error al eliminar el usuario: " + e.getMessage(), e);
        } finally {
            try {
                // ================================
                // Restaurar la autoconfirmación
                // ================================
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new SQLException("Error al restaurar la autoconfirmación: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * @param id el ID del usuario a ser consultado
     * @return el usuario consultado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para CONSULTAR un usuario en la base de datos.
     */
    @Override
    public User findById(int id) throws SQLException {
        // ===========================================
        // Buscar el usuario en la base de datos
        // ===========================================
        User foundUser = null;

        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    foundUser = new User(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("contact_id")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("❌ Error al buscar el usuario: " + e.getMessage(), e);
        }

        return foundUser;
    }

    @Override
    public List<User> findAll() throws SQLException {
        // ==============================
        // Crear una lista de usuarios
        // ==============================
        List<User> userList = new ArrayList<>();

        // ===========================
        // Llamar al método findAll
        // ===========================
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            // =======================
            // Ejecutar la consulta
            // =======================
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    userList.add(new User(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("contact_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("❌ Error al buscar los usuarios: " + e.getMessage(), e);
        }

        return userList;
    }
}
