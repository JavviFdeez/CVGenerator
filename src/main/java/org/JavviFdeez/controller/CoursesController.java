package org.JavviFdeez.controller;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.CoursesDAO;
import org.JavviFdeez.model.entity.Courses;

import java.sql.SQLException;

public class CoursesController {
    // ============
    // Atributos
    // ============
    private CoursesDAO coursesDAO;

    // ==============
    // Constructor
    // ==============
    public CoursesController() {
        this.coursesDAO = new CoursesDAO(ConnectionMariaDB.getConnection());
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
}
