package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.ExperiencesDAO;
import org.JavviFdeez.model.entity.Experiences;


import java.sql.Connection;
import java.sql.SQLException;

public class ExperienceTest {
    public static void main(String[] args) {
        // testSaveExperiences();
        // testUpdateExperiences();
         testDeleteExperiences();
        // testFindExperiencesById();
        // testFindAllExperiences();
    }

    private static void testSaveExperiences() {
        // Crear una nueva instancia de experiencia con los datos necesarios
        Experiences exp = new Experiences(4, "Musico", "11", "Ejemplo", "Madrid", "2024");

        // Obtener una conexión a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ExperienceDAO con la conexión establecida
            ExperiencesDAO expDAO = new ExperiencesDAO(connection);

            // Guardar una exp en la base de datos
            try {
                // Llama al método save del SkillsDAO para guardar el contacto
                Experiences savedExp = expDAO.save(exp);
                System.out.println("✅ Experiencia insertada exitosamente: " + savedExp);
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar la experiencia: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testUpdateExperiences() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ExperiencesDAO con la aplicación establecida
            ExperiencesDAO expDAO = new ExperiencesDAO(connection);

            // Obtener una experiencia existente para actualizar
            int expIdToUpdate = 3;
            Experiences expToUpdate = expDAO.findById(expIdToUpdate);

            // Verificar si se encontró la experiencia
            if (expToUpdate != null) {
                // Actualizar los datos de la experiencia
                expToUpdate.setName("Nueva experiencia");
                expToUpdate.setDuration("Nueva duración");
                expToUpdate.setCompany("Nueva ubicación");
                expToUpdate.setLocation("Nueva ubicación");
                expToUpdate.setYear("2025");

                // Llamar al método update y pasarle el ID y la experiencia actualizada
                Experiences updatedExp = expDAO.update(expIdToUpdate, expToUpdate);
                if (updatedExp != null) {
                    // =========================================================
                    // La actualización fue exitosa, mostrar mensaje de exito
                    // =========================================================
                    System.out.println("✅ Experiencia actualizada exitosamente.");
                } else {
                    // =========================================================
                    // La actualización fallo, mostrar mensaje de error
                    // =========================================================
                    System.out.println("❌ Error al actualizar la experiencia.");
                }
            } else {
                // =========================================================
                // No se encontró la experiencia, mostrar mensaje de error
                // =========================================================
                System.out.println("❌ No se encontró la experiencia con el ID: " + expIdToUpdate);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testDeleteExperiences() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ExperiencesDAO con la conexión establecida
            ExperiencesDAO expDAO = new ExperiencesDAO(connection);

            // ID de la academia que deseas eliminar
            int expIdToDelete = 3;

            // Obtener la academia por su ID
            Experiences expToDelete = expDAO.findById(expIdToDelete);

            // Verificar si se encontró la academia
            if (expToDelete != null) {
                try {
                    // Llamar al método delete y pasarle el ID de la academia a eliminar
                    expDAO.delete(expIdToDelete);
                    System.out.println("✅ Experiencia eliminada exitosamente con ID: " + expIdToDelete);
                } catch (SQLException e) {
                    System.out.println("❌ Error al eliminar la experiencia: " + e.getMessage());
                }
            } else {
                System.out.println("❌ No se encontró la experiencia con el ID: " + expIdToDelete);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }
}
