package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.AcademiesDAO;
import org.JavviFdeez.model.entity.Academies;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AcademiesTest {

    public static void main(String[] args) {
         testSaveAcademy();
        // testUpdateAcademy();
        // testDeleteAcademies();
        // testFindAcademyById();
        // testFindAllAcademies();
    }

    private static void testSaveAcademy() {
        // Crear una nueva instancia de Academia con los datos necesarios
        Academies academies = new Academies( 0 ,"Ejemplo", "ejemplo", "ejemplo", "2022");

        // Obtener una conexión a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ContactDAO con la conexión establecida
            AcademiesDAO academiesDAO = new AcademiesDAO(connection);

            // Guardar la academia en la base de datos
            try {
                // Llama al método save del AcademiesDAO para guardar el contacto
                Academies savedContact = academiesDAO.save(academies);
                System.out.println("✅ Academia insertada exitosamente: " + savedContact);
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar la academia: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testUpdateAcademy() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de AcademiesDAO con la conexión establecida
            AcademiesDAO academiesDAO = new AcademiesDAO(connection);

            // Obtener una academia existente para actualizar
            int academiaIdToUpdate = 4;
            Academies academiaToUpdate = academiesDAO.findById(academiaIdToUpdate);

            // Verificar si se encontró la academia
            if (academiaToUpdate != null) {
                // Actualizar los datos de la academia
                academiaToUpdate.setName("Nuevo nombre");
                academiaToUpdate.setEntity("Nueva entidad");
                academiaToUpdate.setLocation("Nueva ubicación");
                academiaToUpdate.setYear("2024");

                // Llamar al método update y pasarle el ID y la academia actualizada
                Academies updatedAcademies = academiesDAO.update(academiaIdToUpdate, academiaToUpdate);

                // Verificar si la academia se actualizó correctamente
                if (updatedAcademies != null) {
                    System.out.println("✅ Academia actualizada exitosamente: " + updatedAcademies);
                } else {
                    System.out.println("❌ Error al actualizar la academia.");
                }
            } else {
                System.out.println("❌ No se encontró la academia con el ID: " + academiaIdToUpdate);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testDeleteAcademies() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de AcademiesDAO con la conexión establecida
            AcademiesDAO academiesDAO = new AcademiesDAO(connection);

            // ID de la academia que deseas eliminar
            int academiesIdToDelete = 5;

            // Obtener la academia por su ID
            Academies academiesToDelete = academiesDAO.findById(academiesIdToDelete);

            // Verificar si se encontró la academia
            if (academiesToDelete != null) {
                try {
                    // Llamar al método delete y pasarle el ID de la academia a eliminar
                    academiesDAO.delete(academiesIdToDelete);
                    System.out.println("✅ Academia eliminada exitosamente con ID: " + academiesIdToDelete);
                } catch (SQLException e) {
                    System.out.println("❌ Error al eliminar la academia: " + e.getMessage());
                }
            } else {
                System.out.println("❌ No se encontró la academia con el ID: " + academiesIdToDelete);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindAcademyById() {
        // ID de la academia que deseas buscar
        int academiaIdToFind = 4;

        // Obtener una conexión a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de AcademiesDAO con la conexión establecida
            AcademiesDAO academiesDAO = new AcademiesDAO(connection);

            // Obtener la academia por su ID
            Academies foundAcademy = academiesDAO.findById(academiaIdToFind);
            if (foundAcademy != null) {
                System.out.println("✅ Academia encontrada: " + foundAcademy);
            } else {
                System.out.println("❌ No se encontró ninguna academia con el ID: " + academiaIdToFind);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }


    private static void testFindAllAcademies() {
        // Obtener una aplicación a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de AcademiesDAO con la aplicación establecida
            AcademiesDAO academiesDAO = new AcademiesDAO(connection);

            // Obtener todas las acadiendas
            try {
                List<Academies> academiesList = academiesDAO.findAll();
                if (!academiesList.isEmpty()) {
                    System.out.println("✅ Lista de acadiendas encontrada:");
                    for (Academies academy : academiesList) {
                        System.out.println(academy);
                    }
                } else {
                    System.out.println("❌ No se encontró ninguna academia.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Error al buscar todas las acadiendas: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }
}