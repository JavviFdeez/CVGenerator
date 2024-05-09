package org.JavviFdeez.controller;

import org.JavviFdeez.model.entity.Academies;


public abstract class AcademiesControllerAbstract {
    protected AcademiesController academiesController;

    /**
     * @param academies la academia que se va a guardar
     * @Author: JavviFdeez
     * Metodo para mostrar un mensaje de INSERTAR una nueva academia en la base de datos
     */
    public abstract void saveAcademies(Academies academies);

    /**
     * @param id la academia que se va a actualizar
     * @Author: JavviFdeez
     * Método que ACTUALIZAR una academia en la base de datos y muestra un mensaje de éxito o error.
     */
    public abstract void updateAcademies(int id, Academies updatedAcademies);

    /**
     * @param id la academia que se va a eliminar
     * @Author: JavviFdeez
     * Método para ELIMINAR una academia de la base de datos y muestra un mensaje de éxito o error.
     */
    public abstract void deleteAcademies(int id);

    /**
     * @param id el ID de la academia que se va a buscar
     * @Author: JavviFdeez
     * Método para BUSCAR una academia por su ID en la base de datos y muestra un mensaje de éxito o error.
     */
    public abstract void findAcademiesById(int id);

    /**
     * @Author: JavviFdeez
     * Método para BUSCAR todas las academias de la base de datos y muestra un mensaje de exito o error.
     */
    public abstract void findAllAcademies();
}