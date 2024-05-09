package org.JavviFdeez.model.dao.interfaces;

import org.JavviFdeez.model.entity.Users;

import java.sql.SQLException;
import java.util.List;

public interface iUsersDAO {

    /**
     * @param user
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para CREAR un usuario
     */
    Users save(Users user) throws SQLException;

    /**
     * @param user
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR un usuario
     */
    Users update(int id, Users user) throws SQLException;

    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ELIMINAR un usuario
     */
    void delete(int id) throws SQLException;

    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR un usuario por su ID
     */
    Users findById(int id) throws SQLException;

    /**
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR todos los usuarios
     */
    List<Users> findAll() throws SQLException;
}
