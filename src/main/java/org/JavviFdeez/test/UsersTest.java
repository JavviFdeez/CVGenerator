package org.JavviFdeez.test;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.UsersDAO;
import org.JavviFdeez.model.entity.Users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsersTest {

    public static void main(String[] args) {
        testSaveUser();
        // testUpdateUser();
        // testDeleteUser();
        // testFindUserById();
        // testFindAllUsers();
    }

    private static void testSaveUser() {
        // Crear una nueva instancia de usuario con los datos necesarios
        Users users = new Users("ejemplo@gmail.com" , "Ejemplo123");

        // Obtener una aplicación de base de datos
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de userDAO con la aplicación establecida
            UsersDAO usersDAO = new UsersDAO(connection);

            // Guardar el usuario en la base de datos
            try {
                // Llama al método save del UserDAO para guardar el usuario
                Users savedUser = usersDAO.save(users);
                System.out.println("✅ Usuario insertado exitosamente: " + savedUser);
            } catch (SQLException e) {
                System.out.println("❌ Error al guardar el usuario: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testUpdateUser() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de UserDAO con la aplicación establecida
            UsersDAO usersDAO = new UsersDAO(connection);

            // Obtener un usuario existente para actualizar
            int userIdToUpdate = 1;
            Users userToUpdate = usersDAO.findById(userIdToUpdate);

            // Verificar si se encontró el usuario
            if (userToUpdate != null) {
                // Actualizar los datos del usuario
                userToUpdate.setEmail("nuevo@gmail.com");
                userToUpdate.setPassword("Pepe123456");

                // Llamar al método update y pasarle el ID y el usuario actualizado
                Users updatedUser = usersDAO.update(userIdToUpdate, userToUpdate);

                // Verificar si el usuario se actualizó correctamente
                if (updatedUser != null) {
                    System.out.println("✅ Usuario actualizado exitosamente: " + updatedUser);
                } else {
                    System.out.println("❌ Error al actualizar el usuario.");
                }
            } else {
                System.out.println("❌ No se encontró el usuario con el ID: " + userIdToUpdate);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testDeleteUser() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de UserDAO con la aplicación establecida
            UsersDAO usersDAO = new UsersDAO(connection);

            // Obtener un usuario existente para eliminar
            int userIdToDelete = 1;

            // Obtener el usuario por su ID
            Users userToDelete = usersDAO.findById(userIdToDelete);

            // Verificar si se encontró el usuario
            if (userToDelete != null) {
                try {
                    // Llama al método delete del UserDAO para borrar el usuario
                    usersDAO.delete(userIdToDelete);
                    System.out.println("✅ Usuario eliminado exitosamente." + userToDelete);
                } catch (SQLException e) {
                    System.out.println("❌ Error al eliminar el usuario: " + e.getMessage());
                }
            } else {
                System.out.println("❌ No se encontró el usuario con el ID: " + userIdToDelete);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindUserById() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de UserDAO con la aplicación establecida
            UsersDAO usersDAO = new UsersDAO(connection);

            // Obtener un usuario por su ID
            int userId = 2;

            // Obtener el usuario por su ID
            Users user = usersDAO.findById(userId);

            // Verificar si se encontró el usuario
            if (user != null) {
                System.out.println("✅ Usuario encontrado exitosamente: " + user);
            } else {
                System.out.println("❌ No se encontró el usuario con el ID: " + userId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }

    private static void testFindAllUsers() {
        try (Connection connection = ConnectionMariaDB.getConnection()) {
            // Crear una instancia de UserDAO con la aplicación establecida
            UsersDAO usersDAO = new UsersDAO(connection);

            // Obtener todos los usuarios
            List<Users> users = usersDAO.findAll();

            // Verificar si se encontró el usuario
            if (users != null) {
                System.out.println("✅ Cursos encontrados exitosamente: " + users);
            } else {
                System.out.println("❌ No se encontró el usuario con el ID: ");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al establecer la aplicación con la base de datos: " + e.getMessage());
        }
    }
}
