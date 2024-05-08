package org.JavviFdeez.model.dao.interfaces;


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
     * @param id
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ACTUALIZAR una academia
     */
    Academies update(int id, Academies updatedAcademies) throws SQLException;

    /**
     * @param id
     * @throws SQLException
     * @Author: JavviFdeez
     * Método para ELIMINAR una academia
     */
    void delete(int id) throws SQLException;

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