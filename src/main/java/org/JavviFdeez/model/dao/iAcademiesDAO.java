package org.JavviFdeez.model.dao;


import org.JavviFdeez.model.entity.Academies;
import java.sql.SQLException;
import java.util.List;

public interface iAcademiesDAO {
    /**
     * @param academies
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para GUARDAR una academia
     */
    Academies save(Academies academies) throws SQLException;

    /**
     * @param academies
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR una academia
     */
    Academies update(Academies academies) throws SQLException;

    /**
     * @param academies
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ELIMINAR una academia
     */
    Academies delete(Academies academies) throws SQLException;

    /**
     * @param id
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR una academia por su ID
     */
    Academies findById(int id) throws SQLException;

    /**
     * @return
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para BUSCAR todas las acadmias
     */
    List<Academies> findAll() throws SQLException;
}