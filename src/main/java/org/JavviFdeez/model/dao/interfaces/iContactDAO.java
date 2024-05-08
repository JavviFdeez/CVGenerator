package org.JavviFdeez.model.dao.interfaces;

import org.JavviFdeez.model.entity.Contact;
import java.sql.SQLException;
import java.util.List;

public interface iContactDAO {
    /**
     * @Author: JavviFdeez
     * Metodo para GUARDAR un contacto
     * @param contact
     * @return
     * @throws SQLException
     */
    Contact save(Contact contact) throws SQLException;

    /**
     * @Author: JavviFdeez
     * Metodo para ACTUALIZAR un contacto
     * @param c
     * @return
     * @throws SQLException
     */
    Contact update(int id, Contact c) throws SQLException;

    /**
     * @Author: JavviFdeez
     * Metodo para ELIMINAR un contacto
     * @param id
     * @return
     * @throws SQLException
     */
    void delete(int id) throws SQLException;

    /**
     * @Author: JavviFdeez
     * Método para BUSCAR un contacto por su ID
     * @param id
     * @return
     * @throws SQLException
     */
    Contact findById(int id) throws SQLException;

    /**
     * @Author: JavviFdeez
     * Método para BUSCAR todos los contactos
     * @return
     * @throws SQLException
     */
    List<Contact> findAll() throws SQLException;

}
