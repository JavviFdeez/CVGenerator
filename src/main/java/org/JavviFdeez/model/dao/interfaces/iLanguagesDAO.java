package org.JavviFdeez.model.dao.interfaces;

import org.JavviFdeez.model.entity.Languages;

import java.sql.SQLException;
import java.util.List;

public interface iLanguagesDAO {
    /**
     * @param lang
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para GUARDAR un idioma
     */
    Languages save(Languages lang) throws SQLException;

    /**
     * @param lang
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR un idioma
     */
    Languages update(int id, Languages lang) throws SQLException;

    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ELIMINAR un idioma
     */
    void delete(int id) throws SQLException;

    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR un idioma por su ID
     */
    Languages findById(int id) throws SQLException;

    /**
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR todos los idiomas
     */
    List<Languages> findAll() throws SQLException;
}
