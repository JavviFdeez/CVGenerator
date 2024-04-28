package org.JavviFdeez.model.dao;

import org.JavviFdeez.model.entity.Courses;

import java.sql.*;
import java.util.List;

public class CoursesDAO implements iCoursesDAO{

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
     * @Author: JavviFdeez
     * Método para GUARDAR un curso en la base de datos.
     *
     * @param c el curso a ser guardada
     * @return la academia guardada, incluyendo su ID generado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
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
     * @Author: JavviFdeez
     * Método para ACTUALIZAR un curso en la base de datos.
     *
     * @param c el curso a ser actualizado
     * @return el curso actualizado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
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
            pst.setInt(4, c.getCourse_id());
            pst.setInt(5, c.getDuration());

        }

        return c;
    }

    @Override
    public Courses delete(Courses course) throws SQLException {
        return null;
    }

    @Override
    public Courses findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Courses> findAll() throws SQLException {
        return List.of();
    }
}
