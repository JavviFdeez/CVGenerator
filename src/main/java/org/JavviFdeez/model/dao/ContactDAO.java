package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.dao.interfaces.iContactDAO;
import org.JavviFdeez.model.entity.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ContactDAO implements iContactDAO {
    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private static final String INSERT = "INSERT INTO cvv_contact (name, lastname, image, occupation, mobile, email, linkedin, location, extra) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE cvv_contact SET name=?, lastname=?, image=?, occupation=?, mobile=?, email=?, linkedin=?, location=?, extra=? WHERE contact_id=?";
    private static final String DELETE = "DELETE FROM cvv_contact WHERE contact_id=?";
    private static final String FIND_BY_ID = "SELECT * FROM cvv_contact WHERE contact_id=?";
    private static final String FIND_ALL = "SELECT * FROM cvv_contact";

    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private Connection conn;

    // ================================================
    // Constructor para inicializar la base de datos
    // ================================================
    public ContactDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param c el contacto a ser guardado
     * @return el contacto guardado, incluyendo su ID generado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para GUARDAR un contacto en la base de datos.
     */
    @Override
    public Contact save(Contact c) throws SQLException {
        PreparedStatement pst = null;
        ResultSet generatedKeys = null;
        // ===========================================
        // Insertar el contacto en la base de datos
        // ===========================================
        try {
            pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, c.getName());
            pst.setString(2, c.getLastname());
            pst.setString(3, c.getImage());
            pst.setString(4, c.getOccupation());
            pst.setString(5, c.getMobile());
            pst.setString(6, c.getEmail());
            pst.setString(7, c.getLinkedin());
            pst.setString(8, c.getLocation());
            pst.setString(9, c.getExtra());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================ç
            // Si no se insertó ningun contacto, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al guardar el contacto, ninguna fila afectada.");
            }

            // ==============================================================
            // Obtener el ID generado para el contacto
            // ==============================================================
            generatedKeys = pst.getGeneratedKeys();

            // ==============================================================
            // Si no se obtuvo el ID, mostrar mensaje de error
            // ==============================================================
            if (generatedKeys.next()) {
                c.setContact_id(generatedKeys.getInt(1));
            } else {
                throw new SQLException("❌ Error al obtener el ID generado para el contacto.");
            }

            return c;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param id el contacto que se va a actualizar
     * @return true si se actualizo correctamente, false en caso contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @author: JavviFdeez
     * Método para ACTUALIZAR la información de un contacto en la base de datos.
     */
    @Override
    public Contact update(int id, Contact updatedContact) throws SQLException {
        // Habilitar la transacción
        conn.setAutoCommit(false);

        // ============================================
        // Actualizar el contacto en la base de datos
        // ============================================
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            // Establecer los valores de los parámetros en la consulta SQL
            pst.setString(1, updatedContact.getName());
            pst.setString(2, updatedContact.getLastname());
            pst.setString(3, updatedContact.getImage());
            pst.setString(4, updatedContact.getOccupation());
            pst.setString(5, updatedContact.getMobile());
            pst.setString(6, updatedContact.getEmail());
            pst.setString(7, updatedContact.getLinkedin());
            pst.setString(8, updatedContact.getLocation());
            pst.setString(9, updatedContact.getExtra());
            pst.setInt(10, id);

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se actualizo ningun contacto, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al actualizar el contacto, ninguna fila afectada.");
            }

            // Realizar commit
            conn.commit();

        } catch (SQLException e) {
            // En caso de error, hacer rollback
            conn.rollback();
            throw new SQLException("Error al actualizar el contacto: " + e.getMessage(), e);
        } finally {
            try {
                // Restaurar la autoconfirmación
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new SQLException("Error al restaurar la autoconfirmación: " + ex.getMessage(), ex);
            }
        }

        return updatedContact;
    }

    /**
     * @param id el contacto que se va a eliminar
     * @return true si el contacto se elimina correctamente, false en caso contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ELIMINAR un contacto de la base de datos y retorna true si la operación es exitosa.
     */
    @Override
    public void delete(int id) throws SQLException {
        // Habilitar la transacción
        conn.setAutoCommit(false);

        // ===========================================
        // Eliminar el contacto de la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            // ========================================================
            // Establecer el parámetro de ID del contacto a eliminar
            // ========================================================
            pst.setInt(1, id);
            // ==========================================
            // Ejecutar la consulta SQL de eliminación
            // ==========================================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se elimino ningun contacto, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("No se eliminó ningun contacto con el ID: " + id);
            }

            // Realizar commit
            conn.commit();
        } catch (SQLException e) {
            // En caso de error, hacer rollback
            conn.rollback();
            throw new SQLException("Error al eliminar el contacto: " + e.getMessage(), e);
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
     * @param id el ID del contacto a buscar
     * @return el contacto encontrado, o null si no se encuentra
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para BUSCAR un contacto por su ID en la base de datos.
     */
    @Override
    public Contact findById(int id) throws SQLException {
        // =================================================
        // Contacto encontrado, o null si no se encuentra
        // =================================================
        Contact foundContact = null;

        // ===================================================
        // Buscar el contacto por su ID en la base de datos
        // ===================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);

            // ========================================================
            // Ejecutar la consulta SQL para buscar el contacto por ID
            // ========================================================
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // ==================================================
                    // Crear un objeto Contacto con los datos obtenidos
                    // ==================================================
                    foundContact = new Contact(
                            rs.getString("name"),
                            rs.getString("lastname"),
                            rs.getString("image"),
                            rs.getString("occupation"),
                            rs.getString("mobile"),
                            rs.getString("email"),
                            rs.getString("linkedin"),
                            rs.getString("location"),
                            rs.getString("extra")
                    );
                }
            } catch (SQLException e) {
                System.err.println("❌ Error al buscar el contacto por ID: " + e.getMessage());
                throw e;
            }
        }
        return foundContact;
    }

    /**
     * @return una lista con todos los contactos encontrados en la base de datos
     * @throws SQLException si ocurre un error de acceso a la base de datos
     * @Author: JavviFdeez
     * Método para BUSCAR todos los contactos en la base de datos.
     */
    @Override
    public List<Contact> findAll() throws SQLException {
        // ====================================================
        // Lista de contactos encontrados, o vacía si no hay
        // ====================================================
        List<Contact> cList = new ArrayList<>();

        // ===================================================
        // Buscar todos los contactos en la base de datos
        // ===================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                   Contact contact = new Contact(
                           rs.getString("name"),
                           rs.getString("lastname"),
                           rs.getString("image"),
                           rs.getString("occupation"),
                           rs.getString("mobile"),
                           rs.getString("email"),
                           rs.getString("linkedin"),
                           rs.getString("location"),
                           rs.getString("extra")
                   );
                   cList.add(contact);
                }
            } catch (SQLException e) {
                throw new SQLException("❌ Error al buscar todos los contactos: " + e.getMessage());
            }
        }
        return cList;
    }
}
