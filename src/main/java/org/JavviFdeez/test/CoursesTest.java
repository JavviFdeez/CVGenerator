package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.CoursesDAO;
import org.JavviFdeez.model.entity.Courses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CoursesTest {
    public static void main(String[] args) {
        // testSaveCourses();
        // testUpdateCourses();
        // testDeleteCourses();
        // testFindCoursesById();
        // testFindAllCourses();
    }

    private static void testSaveCourses() {
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

    private static void testUpdateCourses() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de CoursesDAO con la conexión establecida
            CoursesDAO cDAO = new CoursesDAO(connection);

            // Obtener un curso existente para actualizar
            int coursesIdToUpdate = 1;
            Courses coursesToUpdate = cDAO.findById(coursesIdToUpdate);

            // Verificar si se encontró el curso
            if (coursesToUpdate != null) {
                // Actualizar los datos del curso
                coursesToUpdate.setName("Nuevo nombre");
                coursesToUpdate.setDuration(3);
                coursesToUpdate.setPosition(2);

                // Llamar al método update y pasarle el ID y el curso actualizado
                cDAO.update(coursesIdToUpdate, coursesToUpdate);
                System.out.println("✅ Cursos actualizados exitosamente: " + coursesToUpdate);
            } else {
                System.out.println("❌ No se encontro el curso con el ID: " + coursesIdToUpdate);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testDeleteCourses() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de CoursesDAO con la aplicación establecida
            CoursesDAO cDAO = new CoursesDAO(connection);

            // Obtener un curso existente para eliminar
            int coursesIdToDelete = 1;
            Courses coursesToDelete = cDAO.findById(coursesIdToDelete);

            // Verificar si se encontró el curso
            if (coursesToDelete != null) {
                // Llamar al método delete y pasarle el ID del curso a eliminar
                cDAO.delete(coursesIdToDelete);
                System.out.println("✅ Cursos eliminados exitosamente: " + coursesToDelete);
            } else {
                System.out.println("❌ No se encontro el curso con el ID: " + coursesIdToDelete);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindCoursesById() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de CoursesDAO con la aplicación establecida
            CoursesDAO cDAO = new CoursesDAO(connection);

            // Obtener un curso por su ID
            int coursesId = 2;
            Courses courses = cDAO.findById(coursesId);

            // Verificar si se encontró el curso
            if (courses != null) {
                System.out.println("✅ Cursos encontrados exitosamente: " + courses);
            } else {
                System.out.println("❌ No se encontro el curso con el ID: " + coursesId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindAllCourses() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de CoursesDAO con la aplicación establecida
            CoursesDAO cDAO = new CoursesDAO(connection);

            // Obtener todos los cursos
            List<Courses> courses = cDAO.findAll();

            // Verificar si se encontró el curso
            if (courses != null) {
                System.out.println("✅ Cursos encontrados exitosamente: " + courses);
            } else {
                System.out.println("❌ No se encontro el curso con el ID: ");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }
}