package org.JavviFdeez.model.dao.interfaces;

import org.JavviFdeez.model.entity.Contact_Skills;

import java.sql.SQLException;
import java.util.List;

public interface iContact_SkillsDAO {

    /**
     * @Author: JavviFdeez
     * Método para GUARDAR una realcion entre un Contact y una Skill
     * @param Contact_Skills
     * @return
     * @throws SQLException
     */
    Contact_Skills save(Contact_Skills Contact_Skills) throws SQLException;

    /**
     * @Author: JavviFdeez
     * Método para ACTUALIZAR una relacion entre un Contact y una Skill
     * @param id
     * @return
     * @throws SQLException
     */
    Contact_Skills update(int id, Contact_Skills cs) throws SQLException;

    /**
     * @Author: JavviFdeez
     * Método para ELIMINAR una relacion entre un Contact y una Skill
     * @param id
     * @return
     * @throws SQLException
     */
    void delete(int id) throws SQLException;

    /**
     * @Author: JavviFdeez
     * Método para BUSCAR una relacion entre un Contact y una Skill por su ID
     * @param id
     * @return
     * @throws SQLException
     */
    Contact_Skills findById(int id) throws SQLException;

    /**
     * @Author: JavviFdeez
     * Método para BUSCAR todas las relacion entre un Contact y una Skill
     * @return
     * @throws SQLException
     */
    List<Contact_Skills> findAll() throws SQLException;
}
