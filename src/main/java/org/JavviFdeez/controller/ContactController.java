package org.JavviFdeez.controller;

import javafx.fxml.Initializable;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.AcademiesDAO;
import org.JavviFdeez.model.dao.ContactDAO;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Contact;
import org.JavviFdeez.model.entity.Session;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactController implements Initializable {
    // =============
    // Atributos
    // =============

    private ContactDAO contactDAO;

    private Connection conn;
    private Session session;
    private AcademiesController academiesController;
    private AcademiesDAO academiesDAO;

    // ==============
    // Constructor
    // ==============
    public ContactController() {
        this.contactDAO = new ContactDAO(ConnectionMariaDB.getConnection());
        this.conn = ConnectionMariaDB.getConnection();
        this.academiesDAO = new AcademiesDAO(ConnectionMariaDB.getConnection());
        this.academiesController = new AcademiesController();
        this.session = Session.getInstance();
    }

    /**
     * @param contact el contacto que se va a guardar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de INSERTAR una nuevo contacto en la base de datos
     */
    public void saveContact(Contact contact) {
        try {

            // ==========================================
            // Guardar el contacto en la base de datos
            // ==========================================
            contactDAO.save(contact);

            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de éxito.
            // ======================================================
            System.out.println("✅ Contacto guardada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al guardar el contacto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el contacto que se va a actualizar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ACTUALIZAR un contacto en la base de datos
     */
    public boolean updateContact(int id, Contact updatedContact) {
        try {
            // Actualizar el contacto en la base de datos
            boolean isUpdated = contactDAO.update(id, updatedContact);

            if (isUpdated) {
                // Si la actualización es exitosa, mostrar mensaje de éxito.
                System.out.println("✅ Contacto actualizado exitosamente.");
            } else {
                // Si no se actualizó ninguna fila, mostrar mensaje de error.
                System.err.println("❌ No se encontró el contacto para actualizar.");
            }

            return isUpdated;
        } catch (SQLException e) {
            // En caso de error, mostrar mensaje de error.
            System.err.println("❌ Error al actualizar el contacto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param id el contacto que se va a eliminar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ELIMINAR un contacto en la base de datos
     */
    public void deleteContact(int id) {
        try {
            // ==========================================
            // Eliminar el contacto de la base de datos
            // ==========================================
            contactDAO.delete(id);
            // ======================================================
            // Si la eliminación es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Contacto eliminado exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al eliminar el contacto: " + e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * @param email
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de buscar un contacto por email en la base de datos
     */
    public Integer getContactIdByEmail(String email) throws SQLException {
        try {
            // ==========================================
            // Buscar el contactId en la base de datos
            // ==========================================
            Integer contactId = contactDAO.getContactIdByEmail(email);
            if (contactId != null) {
                // ======================================================
                // Si la búsqueda es exitosa, mostrar mensaje de éxito.
                // ======================================================
                System.out.println("✅ Contacto encontrado exitosamente: contactId = " + contactId);
                return contactId;
            } else {
                // ======================================================
                // Si no se encuentra el contacto, mostrar mensaje de advertencia.
                // ======================================================
                System.out.println("⚠️ No se encontró ningún contacto con el email proporcionado.");
            }
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al buscar el contactId por email: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Error al obtener el contactId del usuario por correo electrónico: " + e.getMessage());
        }
        return null;
    }


    /**
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de buscar todos los contactos en la base de datos
     */
    public void getAllContact() {
        try {
            // ==========================================
            // Buscar todos los contactos en la base de datos
            // ==========================================
            contactDAO.findAll();
            // ======================================================
            // Si la busqueda es exitosa, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Contactos encontrados exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al buscar los contactos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean saveDataToDatabase(Contact contact) throws SQLException {
        // Guardar los datos en la base de datos
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionMariaDB.getConnection();
            }

            // Preparar la consulta SQL para insertar los datos
            String query = "INSERT INTO cvv_contact (name, lastname, image, occupation, mobile, email, linkedin, location, extra) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, contact.getName());
                pst.setString(2, contact.getLastname());
                pst.setString(3, contact.getImage());
                pst.setString(4, contact.getOccupation());
                pst.setString(5, contact.getMobile());
                pst.setString(6, contact.getEmail());
                pst.setString(7, contact.getLinkedin());
                pst.setString(8, contact.getLocation());
                pst.setString(9, contact.getExtra());
                // Ejecutar la inserción
                pst.executeUpdate();

                // Si se ejecuta sin excepciones, devuelve true
                return true;
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción de SQL aquí
            throw e;
        }
    }

    public Contact getContactById(int contactId) throws SQLException {
        Contact contact = null;
        String query = "SELECT * FROM cvv_contact WHERE contact_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, contactId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Obtener los datos del contacto desde el ResultSet
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastname");
                    String image = resultSet.getString("image");
                    String occupation = resultSet.getString("occupation");
                    String mobile = resultSet.getString("mobile");
                    String email = resultSet.getString("email");
                    String linkedin = resultSet.getString("linkedin");
                    String location = resultSet.getString("location");
                    String extra = resultSet.getString("extra");

                    // Crear un objeto Contact con los datos obtenidos
                    contact = new Contact(contactId, name, lastName, image, occupation, mobile, email, linkedin, location, extra);
                }
            }
        }

        return contact;
    }

    public Academies getIDAcademies() {
        try {
            // Obtener el contactId del usuario autenticado de la sesión
            int contactId = Session.getInstance().getContactId();

            // Utilizar el contactId para buscar las academias asociadas
            Academies academies = academiesDAO.getIDContact(contactId);

            // Devolver las academias encontradas
            return academies;
        } catch (SQLException e) {
            // En caso de error, mostrar mensaje de error
            System.err.println("❌ Error al buscar las academias del contacto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
