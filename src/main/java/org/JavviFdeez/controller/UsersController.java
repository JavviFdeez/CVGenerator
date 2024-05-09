package org.JavviFdeez.controller;

import org.JavviFdeez.model.connection.ConnectionMariaDB;
import org.JavviFdeez.model.dao.UsersDAO;
import org.JavviFdeez.model.entity.Academies;
import org.JavviFdeez.model.entity.Users;

import java.sql.SQLException;
import java.util.List;

public class UsersController {
    // ============
    // Atributes
    // ============
    private UsersDAO usersDAO;

    // ==============
    // Constructor
    // ==============
    public UsersController() {
        this.usersDAO = new UsersDAO(ConnectionMariaDB.getConnection());
    }

    /**
     * @param user el usuario a ser guardado
     * @return el usuario guardado, incluyendo su ID generado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para GUARDAR un usuario en la base de datos.
     */
    public void saveUser(Users user) throws SQLException {
        try {
            // =========================================
            // Guardar el usuario en la base de datos
            // =========================================
            usersDAO.save(user);

            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Usuario guardado exitosamente.");

        } catch (SQLException e) {
            // =============================================
            // En caso de error, mostrar mensaje de error.
            // =============================================
            System.err.println("❌ Error al guardar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el usuario a ser actualizado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ACTUALIZAR un usuario en la base de datos y mostrar un mensaje de exito o error.
     */
    public void updateUser(int id, Users updatedUser) throws SQLException {
        try {
            // =========================================
            // Actualizar el usuario en la base de datos
            // =========================================
            usersDAO.update(id, updatedUser);

            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Usuario actualizado exitosamente.");

        } catch (SQLException e) {
            // En caso de error SQL, registrar el error y mostrar un mensaje al usuario
            System.err.println("❌ Error al actualizar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el usuario a ser eliminado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para ELIMINAR un usuario en la base de datos y mostrar un mensaje de exito o error.
     */
    public void deleteUser(int id) throws SQLException {
        try {
            // =========================================
            // Eliminar el usuario en la base de datos
            // =========================================
            usersDAO.delete(id);

            // ======================================================
            // Si el guardado es exitoso, mostrar mensaje de exito.
            // ======================================================
            System.out.println("✅ Usuario eliminado exitosamente.");
        } catch (SQLException e) {
            // En caso de error SQL, registrar el error y mostrar un mensaje al usuario
            System.err.println("❌ Error al eliminar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id el usuario a ser consultado
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para CONSULTAR un usuario en la base de datos y mostrar un mensaje de exito o error.
     */
    public void findUserbyID(int id) throws SQLException {
        try {
            // =========================================
            // Consultar el usuario en la base de datos
            // =========================================
            Users foundUser = usersDAO.findById(id);

            if (foundUser != null) {
                // ======================================================
                // Si el guardado es exitoso, mostrar mensaje de exito.
                // ======================================================
                System.out.println("✅ Usuario encontrado exitosamente.");
            } else {
                // ======================================================
                // Si el guardado es exitoso, mostrar mensaje de exito.
                // ======================================================
                System.out.println("⚠️ No se encontró ninguna academia con el ID " + id + ".");
            }

        } catch (SQLException e) {
            // En caso de error SQL, registrar el error y mostrar un mensaje al usuario
            System.err.println("❌ Error al encontrar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     * @Author: JavviFdeez
     * Método para CONSULTAR todos los usuarios en la base de datos y mostrar un mensaje de exito o error.
     */
    public void findAllUsers() throws SQLException {
        try {
            // ===================================================
            // Consultar todos los usuarios en la base de datos
            // ===================================================
            List<Users> usersList = usersDAO.findAll();

            if (!usersList.isEmpty()) {
                // ======================================================
                // Si el guardado es exitoso, mostrar mensaje de exito.
                // ======================================================
                System.out.println("✅ Usuario guardado exitosamente");
            } else {
                // ======================================================
                // Si el guardado es exitoso, mostrar mensaje de exito.
                // ======================================================
                System.out.println("⚠️ No se encontró ninguém usuario.");
            }

        } catch (SQLException e) {
            // En caso de error SQL, registrar el error y mostrar un mensaje al usuario
            System.err.println("❌ Error al encontrar los usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }
}