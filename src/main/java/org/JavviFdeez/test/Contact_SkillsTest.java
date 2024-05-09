package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.Contact_SkillsDAO;
import org.JavviFdeez.model.entity.Contact_Skills;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Contact_SkillsTest {
    public static void main(String[] args) {
        // testSaveContact_Skills();
        // testUpdateContact_Skills();
        // testDeleteContact_Skills();
        // testFindContact_SkillsById();
        // testFindAllContact_Skills();
    }

    private static void testSaveContact_Skills() {
        // Crear una nueva instancia de relacion con los datos necesarios
        Contact_Skills cs = new Contact_Skills(4, 1, 5);

        // Obtener una conexión a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de Contact_SkillsDAO con la conexión establecida
            Contact_SkillsDAO csDAO = new Contact_SkillsDAO(connection);

            // Guardar el contacto en la base de datos
            try {
                // Llama al método save del ContactDAO para guardar el contacto
                Contact_Skills savedContact = csDAO.save(cs);
                System.out.println("✅ Relacion insertado exitosamente: " + savedContact);
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar la relacion: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testUpdateContact_Skills() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de Contact_SkillsDAO con la aplicación establecida
            Contact_SkillsDAO csDAO = new Contact_SkillsDAO(connection);

            // Obtener una relacion existente para actualizar
            int contact_SkillsIdToUpdate = 1;
            Contact_Skills contact_SkillsToUpdate = csDAO.findById(contact_SkillsIdToUpdate);

            // Verificar si se encontró la relacion
            if (contact_SkillsToUpdate != null) {
                // Actualizar los datos de la relacion
                contact_SkillsToUpdate.setValue(4);

                // Llamar al método update y pasarle el ID y la relacion actualizada
                Contact_Skills updatedCS = csDAO.update(contact_SkillsIdToUpdate, contact_SkillsToUpdate);

                // Verificar si la academia se actualizó correctamente
                if (updatedCS != null) {
                    System.out.println("✅ Academia actualizada exitosamente: " + updatedCS);
                } else {
                    System.out.println("❌ Error al actualizar la academia.");
                }
            } else {
                System.out.println("❌ No se encontró la academia con el ID: " + contact_SkillsToUpdate);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testDeleteContact_Skills() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de Contact_SkillsDAO con la aplicación establecida
            Contact_SkillsDAO csDAO = new Contact_SkillsDAO(connection);

            // Obtener una relacion existente para borrar
            int contact_SkillsIdToDelete = 1;

            // Obtener la relacion por su ID
            Contact_Skills contact_SkillsToDelete = csDAO.findById(contact_SkillsIdToDelete);

            // Verificar si se encontró la relacion
            if (contact_SkillsToDelete != null) {
                try {
                    // Llama al método delete del ContactDAO para borrar el contacto
                    csDAO.delete(contact_SkillsIdToDelete);
                    System.out.println("✅ Relacion eliminada exitosamente." + contact_SkillsToDelete);
                } catch (SQLException e) {
                    System.out.println("❌ Error al eliminar la relacion: " + e.getMessage());
                }
            } else {
                System.out.println("❌ No se encontró la relacion con el ID: " + contact_SkillsIdToDelete);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindContact_SkillsById() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de Contact_SkillsDAO con la aplicación establecida
            Contact_SkillsDAO csDAO = new Contact_SkillsDAO(connection);

            // Obtener una relacion existente
            int contact_SkillsId = 2;

            // Obtener la relacion por su ID
            Contact_Skills contact_Skills = csDAO.findById(contact_SkillsId);

            // Verificar si se encontró la relacion
            if (contact_Skills != null) {
                System.out.println("✅ Relacion encontrada exitosamente: " + contact_Skills);
            } else {
                System.out.println("❌ No se encontró la relacion con el ID: " + contact_SkillsId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindAllContact_Skills() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de Contact_SkillsDAO con la aplicación establecida
            Contact_SkillsDAO csDAO = new Contact_SkillsDAO(connection);

            // Obtener todas las relaciones
            List<Contact_Skills> contact_Skills = csDAO.findAll();

            // Verificar si se encontró la relacion
            if (contact_Skills != null) {
                System.out.println("✅ Relaciones encontradas exitosamente: " + contact_Skills);
            } else {
                System.out.println("❌ No se encontró ninguna relacion.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }
}
