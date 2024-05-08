package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.ContactDAO;
import org.JavviFdeez.model.entity.Contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ContactTest {

    public static void main(String[] args) {
        // testSaveContact();
        // testUpdateContact();
         testDeleteContact();
        // testFindContactById();
        // testFindAllContacts();
    }

    private static void testSaveContact() {
        // Crear una nueva instancia de contacto con los datos necesarios
        Contact contact = new Contact("John Doe", "Doe", null, "Developer","123456789", "ejemplo@gmail.com", null, "Cordoba", null);

        // Obtener una conexión a la base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ContactDAO con la conexión establecida
            ContactDAO contactDAO = new ContactDAO(connection);

            // Guardar el contacto en la base de datos
            try {
                // Llama al método save del ContactDAO para guardar el contacto
                Contact savedContact = contactDAO.save(contact);
                System.out.println("✅ Contacto insertado exitosamente: " + savedContact);
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar el contacto: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testUpdateContact() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ContactDAO con la aplicación establecida
            ContactDAO contactDAO = new ContactDAO(connection);

            // Obtener un contacto existente para actualizar
            int contactIdToUpdate = 4;
            Contact contactToUpdate = contactDAO.findById(contactIdToUpdate);

            // Verificar si se encontró el contacto
            if (contactToUpdate != null) {
                // Actualizar los datos del contacto
                contactToUpdate.setName("New First Name");
                contactToUpdate.setLastname("New Last Name");
                contactToUpdate.setImage("New Image");
                contactToUpdate.setOccupation("New Occupation");
                contactToUpdate.setMobile("New Mobile");
                contactToUpdate.setEmail("aa@gmail.com");
                contactToUpdate.setLinkedin("New Linkedin");
                contactToUpdate.setLocation("New Location");
                contactToUpdate.setExtra("New Extra");

                // Llamar al método update y pasarle el ID y el contacto actualizado
                Contact updatedContact = contactDAO.update(contactIdToUpdate, contactToUpdate);

                // Verificar si el contacto se actualizó correctamente
                if (updatedContact != null) {
                    System.out.println("✅ Contacto actualizada exitosamente: " + updatedContact);
                } else {
                    System.out.println("❌ Error al actualizar el contacto.");
                }
            } else {
                System.out.println("❌ No se encontró el contacto con el ID: " + contactIdToUpdate);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la conexión con la base de datos: " + e.getMessage());
        }
    }

    private static void testDeleteContact() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ContactDAO con la aplicación establecida
            ContactDAO contactDAO = new ContactDAO(connection);

            // ID del contacto a borrar
            int contactIdToDelete = 4;

            // Obtener  por su ID
            Contact deletedContact = contactDAO.findById(contactIdToDelete);

            // Verificar si se encontró el contacto
            if (deletedContact != null) {
                try {
                    // Llama al método delete del ContactDAO para borrar el contacto
                    contactDAO.delete(contactIdToDelete);
                    System.out.println("✅ Contacto borrado exitosamente: " + deletedContact);
                } catch (SQLException e) {
                    System.out.println("❌ Error al borrar el contacto: " + e.getMessage());
                }
            } else {
                System.out.println("❌ No se encontró el contacto con el ID: " + contactIdToDelete);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindContactById() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ContactDAO con la aplicación establecida
            ContactDAO contactDAO = new ContactDAO(connection);

            // Obtener un contacto existente
            int contactId = 4;
            Contact contact = contactDAO.findById(contactId);

            // Verificar si se encontró el contacto
            if (contact != null) {
                System.out.println("✅ Contacto encontrado exitosamente: " + contact);
            } else {
                System.out.println("❌ No se encontró el contacto con el ID: " + contactId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindAllContacts() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de ContactDAO con la aplicación establecida
            ContactDAO contactDAO = new ContactDAO(connection);

            // Obtener todos los contactos existentes
            List<Contact> contacts = contactDAO.findAll();

            // Verificar si se encontraron contactos
            if (!contacts.isEmpty()) {
                System.out.println("✅ Contactos encontrados exitosamente: " + contacts);
            } else {
                System.out.println("❌ No se encontraron contactos.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }
}