package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.CoursesDAO;
import org.JavviFdeez.model.entity.Courses;

import java.sql.Connection;
import java.sql.SQLException;

public class CoursesTest {
    public static void main(String[] args) {
        // Crear una nueva instancia de curso con los datos necesarios
        Courses c = new Courses(4, "PhotoShop", 2, 1);

        // Obtener una conexión a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de CoursesDAO con la conexión establecida
            CoursesDAO cDAO = new CoursesDAO(connection);

            // Guardar un curso en la base de datos
            try {
                // Llama al método save del CoursesDAO para guardar el contacto
                Courses savedCourses = cDAO.save(c);
                System.out.println("✅ Curso insertado exitosamente: " + savedCourses);
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar el curso: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }
}
