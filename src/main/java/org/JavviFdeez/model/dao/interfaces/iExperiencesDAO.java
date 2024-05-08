package org.JavviFdeez.model.dao.interfaces;

import org.JavviFdeez.model.entity.Experiences;

import java.sql.SQLException;
import java.util.List;

public interface iExperiencesDAO {
    /**
     * @param exp
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para GUARDAR una experiencia
     */
    Experiences save(Experiences exp) throws SQLException;

    /**
     * @param exp
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR una experiencia
     */
    Experiences update(Experiences exp) throws SQLException;

    /**
     * @param exp
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ELIMINAR una experiencia
     */
    Experiences delete(Experiences exp) throws SQLException;

    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR una experiencia por su ID
     */
    Experiences findById(int id) throws SQLException;

    /**
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR todas las experiencias
     */
    List<Experiences> findAll() throws SQLException;
}
