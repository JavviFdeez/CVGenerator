package org.JavviFdeez.model.dao.interfaces;

import org.JavviFdeez.model.entity.Skills;

import java.sql.SQLException;
import java.util.List;

public interface iSkillsDAO {

    /**
     * @param skill
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para GUARDAR una skill
     */
    Skills save(Skills skill) throws SQLException;

    /**
     * @param skill
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR una skill
     */
    Skills update(Skills skill) throws SQLException;

    /**
     * @param skill
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ELIMINAR una skill
     */
    Skills delete(Skills skill) throws SQLException;

    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR una skill por su ID
     */
    Skills findById(int id) throws SQLException;

    /**
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR todas las skills
     */
    List<Skills> findAll() throws SQLException;
}
