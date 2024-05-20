package org.JavviFdeez.controller;

import javafx.fxml.Initializable;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.LanguagesDAO;
import org.JavviFdeez.model.entity.Languages;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LanguagesController implements Initializable {
    private LanguagesDAO languagesDAO;
    private Connection conn;

    // ==============
    // Constructor
    // ==============
    public LanguagesController() {
        this.languagesDAO = new LanguagesDAO(ConnectionMariaDB.getConnection());
        this.conn = ConnectionMariaDB.getConnection();
    }

    /**
     * @param languages la lengua que se va a guardar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de INSERTAR una nueva lengua en la base de datos
     */
    public void saveLanguages(Languages languages) {
        try {
            // ==========================================
            // Guardar la lengua en la base de datos
            // ==========================================
            languagesDAO.save(languages);
            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de إxito
            // ======================================================
            System.out.println("✅ Lengua guardada exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al guardar la lengua: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id la lengua que se va a actualizar
     * @Author: JavviFdeez
     * Método que ACTUALIZAR una lengua en la base de datos y muestra un mensaje de إxito o error.
     */
    public void updateLanguages(int id, Languages updateLanguages) {
        try {
            Languages updatedLanguages = languagesDAO.update(id, updateLanguages);
            if (updatedLanguages != null) {
                // =========================================================
                // La actualización fue exitosa, mostrar mensaje de إxito
                // =========================================================
                System.out.println("✅ Lengua actualizada exitosamente.");
            } else {
                // ===============================================================
                // No se actualizó ninguna fila, mostrar mensaje de advertencia
                // ===============================================================
                System.out.println("⚠️ No se encontró ninguna lengua para actualizar.");
            }
        } catch (SQLException e) {
            // ==================================================================================================
            // Ocurrio un error al actualizar la lengua, mostrar mensaje de error y detalles de la excepción
            // ==================================================================================================
            System.err.println("❌ Error al actualizar la lengua: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id la lengua que se va a eliminar
     * @Author: JavviFdeez
     * Método que ELIMINAR una lengua de la base de datos y muestra un mensaje de exito o error.
     */
    public void deleteLanguages(int id) {
        try {
            languagesDAO.delete(id);
            // =========================================================
            // Si el borrado es exitoso, mostrar mensaje de exito
            // =========================================================
            System.out.println("✅ Lengua eliminada exitosamente.");
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al eliminar la lengua: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id la lengua que se va a buscar
     * @Author: JavviFdeez
     * Método para BUSCAR todas las lenguas  por su ID en la base de datos y muestra un mensaje de exito o error.
     */
    public void findLanguagesById(int id) {
        try {
            // ===================================================
            // Buscar la lengua por su ID en la base de datos
            // ===================================================
            Languages foundLanguages = languagesDAO.findById(id);
            if (foundLanguages != null) {
                // ==================================================
                // Si se encontró la lengua, mostrar los detalles
                // ==================================================
                System.out.println("✅ Lengua encontrada.");
                System.out.println(foundLanguages);
            } else {
                // ================================================================
                // Si no se encontró la lengua, mostrar mensaje de advertencia
                // ================================================================
                System.out.println("⚠️ No se encontró ninguna lengua con el ID " + id + ".");
            }
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al buscar la lengua: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Author: JavviFdeez
     * Método para BUSCAR todas las lenguas de la base de datos y muestra un mensaje de exito o error.
     */
    public void findAllLanguages() {
        try {
            List<Languages> languagesList = languagesDAO.findAll();
            if (!languagesList.isEmpty()) {
                // ==================================================
                // Si se encontró al menos una lengua, mostrarlas
                // ==================================================
                System.out.println("✅ Lenguas encontradas:");
                languagesList.forEach(System.out::println);
            } else {
                // ================================================================
                // Si no se encontró ninguna lengua, mostrar mensaje de advertencia
                // ================================================================
                System.out.println("⚠️ No se encontró ninguna lengua.");
            }
        } catch (SQLException e) {
            // ========================================================================
            // En caso de error, mostrar mensaje de error y detalles de la excepción
            // ========================================================================
            System.err.println("❌ Error al buscar las lenguas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean saveDataToDatabase(String spanish, String english, String french, String spanish1, String english1, String french1, String spanish2, String english2, String french2) throws SQLException {
        // Guardar los datos en la base de datos
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionMariaDB.getConnection();
            }

            // Preparar la consulta SQL para insertar los datos
            String query = "INSERT INTO cvv_languages (spanish, english, french) VALUES (?, ?, ?), (?, ?, ?), (?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, spanish);
                pst.setString(2, english);
                pst.setString(3, french);
                pst.setString(4, spanish1);
                pst.setString(5, english1);
                pst.setString(6, french1);
                pst.setString(7, spanish2);
                pst.setString(8, english2);
                pst.setString(9, french2);

                pst.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
