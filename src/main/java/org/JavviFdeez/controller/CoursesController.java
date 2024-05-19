package org.JavviFdeez.controller;

import javafx.fxml.Initializable;
import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.CoursesDAO;
import org.JavviFdeez.model.entity.Courses;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {
    // ============
    // Atributos
    // ============
    private CoursesDAO coursesDAO;
    private Connection conn;

    // ==============
    // Constructor
    // ==============
    public CoursesController() {
        this.coursesDAO = new CoursesDAO(ConnectionMariaDB.getConnection());
        this.conn = ConnectionMariaDB.getConnection();
    }

    /**
     * @param c el curso que se va a guardar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de INSERTAR un nuevo curso en la base de datos
     */
    public void saveCourses(Courses c) {
        try {
            // ==========================================
            // Guardar el curso en la base de datos
            // ==========================================
            coursesDAO.save(c);
            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Cursos guardados exitosamente.");
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al guardar el curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el curso que se va a actualizar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ACTUALIZAR un nuevo curso en la base de datos
     */
    public void updateCourses(int id, Courses updatedCourses) {
        try {
            // ==========================================
            // Actualizar el curso en la base de datos
            // ==========================================
            coursesDAO.update(id, updatedCourses);
            // ===========================================================
            // Si la actualizacion es exitosa, mostrar mensaje de exito.
            // ===========================================================
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al actualizar el curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el curso que se va a eliminar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de ELIMINAR un nuevo curso en la base de datos
     */
    public void deleteCourses(int id) {
        try {
            // ==========================================
            // Eliminar el curso de la base de datos
            // ==========================================
            coursesDAO.delete(id);
            // ======================================================
            // Si la eliminación es exitosa, mostrar mensaje de exito.
            // ======================================================
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al eliminar el curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el curso que se va a buscar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de BUSCAR un nuevo curso en la base de datos
     */
    public void getCourses(int id) {
        try {
            // ==========================================
            // Buscar el curso en la base de datos
            // ==========================================
            Courses foundCourses = coursesDAO.findById(id);
            if (foundCourses != null) {
                // ======================================================
                // Si la busqueda es exitosa, mostrar mensaje de exito.
                // ======================================================
                System.out.println("✅ Cursos encontrado exitosamente.");
            } else {
                // ======================================================
                // Si la busqueda es exitosa, mostrar mensaje de exito.
                // ======================================================
                System.out.println("⚠️ No se encontró ningun curso con el ID proporcionado.");
            }
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al buscar el curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de BUSCAR todos los cursos en la base de datos
     */
    public void getAllCourses() {
        try {
            // ==========================================
            // Buscar todos los cursos en la base de datos
            // ==========================================
            coursesDAO.findAll();
            // ======================================================
            // Si la busqueda es exitosa, mostrar mensaje de exito.
            // ======================================================
        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al buscar los cursos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean saveDataToDatabase(String name, String duration, String name1, String duration1, String name2, String duration2) throws SQLException {
        // Guardar los datos en la base de datos
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionMariaDB.getConnection();
            }

            // Preparar la consulta SQL para insertar los datos
            String query = "INSERT INTO cvv_academies (name, entity,) VALUES (?, ?,) (?, ?), (?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, name);
                pst.setString(2, duration);
                pst.setString(5, name1);
                pst.setString(6, duration1);
                pst.setString(9, name2);
                pst.setString(10, duration2);

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
