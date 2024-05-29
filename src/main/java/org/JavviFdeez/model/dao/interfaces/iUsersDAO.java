package org.JavviFdeez.model.dao.interfaces;

import org.JavviFdeez.model.entity.User;

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
    User save(User user) throws SQLException;

    /**
     * @param user
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR un usuario
     */
    User update(int id, User user) throws SQLException;

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
    User findById(int id) throws SQLException;

    /**
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR todos los usuarios
     */
    List<User> findAll() throws SQLException;
}
