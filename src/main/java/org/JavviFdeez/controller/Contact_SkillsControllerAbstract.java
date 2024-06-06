package org.JavviFdeez.controller;

import org.JavviFdeez.model.entity.Contact_Skills;

public abstract class Contact_SkillsControllerAbstract {
    protected Contact_SkillsController contact_skillsController;

    /**
     * Método abstracto para guardar las habilidades de contacto.
     *
     * @param cs el ID del contacto al que se le agregarán las habilidades
     * @param cs las habilidades que se agregarán al contacto
     */
    public abstract void saveContact_Skill(Contact_Skills cs);

    /**
     * Método abstracto para actualizar las habilidades de contacto.
     *
     * @param cs el contacto cuyas habilidades se actualizarán
     * @param cs las nuevas habilidades del contacto
     */
    public abstract void updateContact_Skill(Contact_Skills cs);

    /**
     * Método abstracto para eliminar las habilidades de contacto.
     *
     * @param id el ID del contacto del que se eliminarán las habilidades
     */
    public abstract void deleteContact_Skill(int id);

    /**
     * Método abstracto para buscar las habilidades de contacto de un contacto específico.
     *
     * @param id el ID del contacto del que se buscarán las habilidades
     * @return una lista de habilidades del contacto
     */
    public abstract void getIDContact_Skill(int id);

    /**
     * Método abstracto para buscar todas las habilidades de contacto.
     *
     * @return un mapa que asocia los IDs de contacto con sus respectivas habilidades
     */
    public abstract void getAllContact_Skill();
}