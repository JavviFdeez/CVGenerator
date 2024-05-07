package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.entity.Courses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO implements iCoursesDAO {

    // =======================================
    // Sentencias SQL para la base de datos
    // =======================================
    private static final String INSERT = "INSERT INTO cvv_courses (course_id, contact_id, name, year) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cvv_courses SET contact_id=?, name=?, year=? WHERE course_id=?";
    private static final String DELETE = "DELETE FROM cvv_courses WHERE course_id=?";
    private static final String FIND_BY_ID = "SELECT * FROM cvv_courses WHERE course_id=?";
    private static final String FIND_ALL = "SELECT * FROM cvv_courses";

    // ===============================================
    // Atributo para la conexión a la base de datos
    // ===============================================
    private Connection conn;

    // ================================================
    // Constructor para inicializar la base de datos
    // ================================================
    public CoursesDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param c el curso a ser guardada
     * @return la academia guardada, incluyendo su ID generado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para GUARDAR un curso en la base de datos.
     */
    @Override
    public Courses save(Courses c) throws SQLException {
        // =========================================
        // Insertar el curso en la base de datos
        // =========================================
        try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, c.getContact_id());
            pst.setString(2, c.getName());
            pst.setInt(3, c.getDuration());
            pst.setInt(4, c.getCourse_id());


            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();

            // ==============================================================
            // Si no se insertó ningun curso, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se guardó ningun curso.");
            }

            // ==============================================================
            // Obtener el ID generado por la base de datos
            // ==============================================================
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // =====================================================================
                    // Obtener el ID generado, asignarlo al objeto de curso y devolverlo
                    // =====================================================================
                    c.setCourse_id(generatedKeys.getInt(1));
                } else {
                    // ===========================================================
                    // Si no se obtuvo el ID generado, mostrar mensaje de error
                    // ===========================================================
                    throw new SQLException("❌ Error al insertar, no se guardó ningun curso.");
                }
            }
            return c;
        }
    }

    /**
     * @param c el curso a ser actualizado
     * @return el curso actualizado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ACTUALIZAR un curso en la base de datos.
     */
    @Override
    public Courses update(Courses c) throws SQLException {
        // ==========================================
        // Actualizar el curso en la base de datos
        // ==========================================
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setInt(1, c.getContact_id());
            pst.setString(2, c.getName());
            pst.setInt(3, c.getDuration());
            pst.setInt(4, c.getPosition());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();
            // ==============================================================
            // Si no se actualizó ningun curso, mostrar mensaje de error
            // ==============================================================
            if (rowsAffected == 0) {
                throw new SQLException("❌ Error al insertar, no se guardó ningun curso.");
            }

        }
        return c;
    }

    /**
     * @param c el curso que se va a eliminar
     * @return true si el curso se elimina correctamente, false en caso contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ELIMINAR un curso de la base de datos y retorna true si la operación es exitosa.
     */
    @Override
    public Courses delete(Courses c) throws SQLException {
        // ===========================================
        // Eliminar el curso de la base de datos
        // ===========================================
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, c.getCourse_id());

            // =======================
            // Ejecutar la consulta
            // =======================
            int rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("❌ Error al insertar, no se guardó ningun curso.");
        }

        return c;
    }

    /**
     * @param id el ID del curso que se va a buscar
     * @return el curso con el ID especificado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para BUSCAR un curso por su ID en la base de datos.
     */
    @Override
    public Courses findById(int id) throws SQLException {
        // =================================================
        // Curso encontrado, o null si no se encuentra
        // =================================================
        Courses foundCourse = null;

        // ==================================================
        // Consulta para buscar un curso por su ID
        // ==================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);

            // ==============================================
            // Ejecutar la consulta y obtener el resultado
            // ==============================================
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    // =====================================================================
                    // Crear un objeto de curso con los datos obtenidos de la base de datos
                    // =====================================================================
                    foundCourse = new Courses(
                            res.getInt("course_id"),
                            res.getInt("contact_id"),
                            res.getString("name"),
                            res.getInt("duration"),
                            res.getInt("position")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("❌ Error al buscar el curso");
        }

        return foundCourse;
    }

    /**
     * @return una lista vacía de cursos
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para BUSCAR todos los cursos en la base de datos.
     */
    @Override
    public List<Courses> findAll() throws SQLException {
        // =================================================
        // Lista de cursos encontrados, o vacía si no hay
        // =================================================
        List<Courses> coursesLIst = new ArrayList<>();
        // ================================================
        // Ejecutar la consulta y obtener el resultado
        // ================================================
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    // ======================================================
                    // Crear un objeto de curso con los datos obtenidos de la base de datos
                    // ======================================================
                    Courses c = new Courses(
                            res.getInt("course_id"),
                            res.getInt("contact_id"),
                            res.getString("name"),
                            res.getInt("duration"),
                            res.getInt("position")
                    );
                    // ======================================================
                    // Agregar los cursos encontrados a la lista de cursos
                    // ======================================================
                    coursesLIst.add(c);
                }
            } catch (SQLException e) {
                throw new SQLException("❌ Error al buscar los cursos");
            }
        }
        return coursesLIst;
    }
}