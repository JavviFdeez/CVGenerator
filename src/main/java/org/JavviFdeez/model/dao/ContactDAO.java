package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
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
    private static final String INSERT = "INSERT INTO contacts (name, email, phone_number) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE contacts SET name=?, email=?, phone_number=? WHERE id=?";
    private static final String DELETE = "DELETE FROM contacts WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM contacts WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM contacts";

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
            pst.setString(2, c.getEmail());
            pst.setString(3, c.getMobile());

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

            // ====================================
            // Cerrar los objetos de la consulta
            // ====================================
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (generatedKeys != null) {
                generatedKeys.close();
            }
        }
    }

    /**
     * @param c el contacto que se va a actualizar
     * @return true si se actualizo correctamente, false en caso contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @author: JavviFdeez
     * Método para ACTUALIZAR la información de un contacto en la base de datos.
     */
    @Override
    public Contact update(Contact c) throws SQLException {
        // ============================================
        // Actualizar el contacto en la base de datos
        // ============================================
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setString(1, c.getName());
            pst.setString(2, c.getEmail());
            pst.setString(3, c.getMobile());
            pst.setInt(4, c.getContact_id());

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
        }

        return c;
    }

    /**
     * @param c el contacto que se va a eliminar
     * @return true si el contacto se elimina correctamente, false en caso contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ELIMINAR un contacto de la base de datos y retorna true si la operación es exitosa.
     */
    @Override
    public Contact delete(Contact c) throws SQLException {
        // ===========================================
        // Eliminar el contacto de la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            // ========================================================
            // Establecer el parámetro de ID del contacto a eliminar
            // ========================================================
            pst.setInt(1, c.getContact_id());

            // ==========================================
            // Ejecutar la consulta SQL de eliminación
            // ==========================================
            int rowsAffected = pst.executeUpdate();

            // ============================================================
            // Si no se borro ningun contacto, mostrar mensaje de error
            // ============================================================
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el contacto: " + e.getMessage());
            throw e;
        }

        return c;
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
                            rs.getInt("contact_id"),
                            rs.getString("name"),
                            rs.getString("last_name"),
                            rs.getString("image"),
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
                    // ===================================================
                    // Crear un objeto Contacto con los datos obtenidos
                    // ===================================================
                    int contact_id = rs.getInt("contact_id");
                    String name = rs.getString("name");
                    String last_name = rs.getString("last_name");
                    String image = rs.getString("image");
                    String mobile = rs.getString("mobile");
                    String email = rs.getString("email");
                    String linkedin = rs.getString("linkedin");
                    String location = rs.getString("location");
                    String extra = rs.getString("extra");

                    // ===================================================
                    // Crear un objeto Contacto con los datos obtenidos
                    // ===================================================
                    Contact foundContact = new Contact(contact_id, name, last_name, image, mobile, email, linkedin, location, extra);
                    cList.add(foundContact);
                }
            } catch (SQLException e) {
                throw new SQLException("❌ Error al buscar todos los contactos: " + e.getMessage());
            }
        }
        return cList;
    }
}
